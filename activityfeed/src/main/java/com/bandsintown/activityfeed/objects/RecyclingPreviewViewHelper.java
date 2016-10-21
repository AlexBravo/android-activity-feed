package com.bandsintown.activityfeed.objects;

import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.GroupFeedItemMiniListItem;
import com.bandsintown.activityfeed.audio.AudioStateItem;
import com.bandsintown.activityfeed.audio.AudioStateManager;
import com.bandsintown.activityfeed.interfaces.AudioControlsGroup;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndex;
import com.bandsintown.activityfeed.interfaces.RecyclingAudioPreviewHelper;
import com.bandsintown.activityfeed.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjaylward on 10/19/16
 */

public class RecyclingPreviewViewHelper extends RecyclingAudioPreviewHelper {

    private FeedGroupInterface mGroup;
    private List<? extends FeedItemInterface> mFeedItems;
    private MediaControllerCompat.TransportControls mTransportControls;
    private OnItemClickAtIndex<AudioPreviewInfo> mAudioPreviewInfoOnItemClickListener;
    private List<AudioPreviewInfo> mAudioPreviewInfoList = new ArrayList<>();
    private AudioControlsGroup mView;
    private int mAdapterPosition;

    public RecyclingPreviewViewHelper(List<AudioPreviewInfo> audioPreviewInfoList, FeedGroupInterface group, MediaControllerCompat.TransportControls transportControls,
                                      AudioControlsGroup view, int adapterPosition, OnItemClickAtIndex<AudioPreviewInfo> onAudioItemClicked) {
        mAudioPreviewInfoList = audioPreviewInfoList;
        mGroup = group;
        mFeedItems = group.getActivities();
        mAudioPreviewInfoOnItemClickListener = onAudioItemClicked;
        mTransportControls = transportControls;
        mView = view;
        mAdapterPosition = adapterPosition;
    }

    @Override
    public void onItemClick(int type, int index, Bundle bundle) {
        Logger.log("Click", index, "item?", type == ITEM_CLICK, "image?", type == IMAGE_CLICK);

        switch(type) {
            case ITEM_CLICK:
                if(mAudioPreviewInfoOnItemClickListener != null)
                    mAudioPreviewInfoOnItemClickListener.onItemClick(mAudioPreviewInfoList.get(index), index);
                break;
            case IMAGE_CLICK:
                if(bundle != null && bundle.getInt(GroupFeedItemMiniListItem.MEDIA_CONTROL_STATE, -1) > -1) {

                    int playbackState = bundle.getInt(GroupFeedItemMiniListItem.MEDIA_CONTROL_STATE, -1);

                    AudioStateManager.getInstance().setCurrent(new AudioStateItem.Builder()
                            .feedId(mFeedItems.get(index).getId())
                            .groupId(mGroup.getGroupId())
                            .knownIndex(mAdapterPosition)
                            .mediaPlayerState(-1) //state will get set through the transport controls
                            .build(), false);

                    switch(playbackState) {
                        case PlaybackStateCompat.STATE_PLAYING:
                            mTransportControls.pause();
                            break;
                        case PlaybackStateCompat.STATE_BUFFERING:
                        case PlaybackStateCompat.STATE_CONNECTING:
                            break;
                        default:
                            Bundle mediaInfoBundle = new Bundle();
                            mediaInfoBundle.putString(FeedValues.SOURCE, FeedValues.SPOTIFY);
                            AudioPreviewInfo audioPreviewInfo = mAudioPreviewInfoList.get(index);
                            if(audioPreviewInfo != null) {
                                mediaInfoBundle.putString(FeedValues.TYPE, FeedValues.SPOTIFY_URI);
                                mTransportControls.playFromSearch(audioPreviewInfo.toMediaUri(), bundle);
                            } else if(mFeedItems.get(index).getObject().getSpotifyUri() != null) {
                                mediaInfoBundle.putString(FeedValues.TYPE, FeedValues.SPOTIFY_URI);
                                mTransportControls.playFromSearch(mFeedItems.get(index).getObject().getSpotifyUri(), mediaInfoBundle);
                            }
                            else {
                                mediaInfoBundle.putString(FeedValues.TYPE, FeedValues.ARTIST_NAME);
                                mTransportControls.playFromSearch(mFeedItems.get(index).getObject().getArtistStub().getName(), mediaInfoBundle);
                            }
                    }
                }
                break;
        }
    }

    @Override
    public void syncPlaybackState() {
        AudioStateItem item = AudioStateManager.getInstance().getCurrent();

        if(item != null) {
            if(item.getActivityFeedItemId() > 0) {
                for(int i = 0; i < mGroup.getActivities().size(); i++) {
                    if(mGroup.getActivities().get(i).getId() == item.getActivityFeedItemId())
                        mView.setAudioPlayerStateAtIndex(i, item.getState());
                    else
                        mView.setAudioPlayerStateAtIndex(i, PlaybackStateCompat.STATE_STOPPED);
                }
            }
        }
        else {
            for(int i = 0; i < mGroup.getActivities().size(); i++) {
                mView.setAudioPlayerStateAtIndex(i, PlaybackStateCompat.STATE_STOPPED);
            }
        }
    }

}
