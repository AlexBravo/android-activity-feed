package com.bandsintown.activityfeed.viewholders;

import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.GroupFeedItemMiniList;
import com.bandsintown.activityfeed.GroupFeedItemMiniListItem;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.audio.AudioStateItem;
import com.bandsintown.activityfeed.audio.AudioStateManager;
import com.bandsintown.activityfeed.audio.OnAudioStateChangeListener;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndexAtSubIndex;
import com.bandsintown.activityfeed.interfaces.OnItemClickOfTypeAtIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.util.Logger;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by rjaylward on 4/6/16 for Bandsintown
 */
public class GroupListensFeedItemViewHolder extends AbsActivityFeedGroupViewHolder implements OnItemClickOfTypeAtIndex, OnAudioStateChangeListener {

    private GroupFeedItemMiniList mView;
    private ArrayList<? extends FeedItemInterface> mFeedItems;
    private FeedGroupInterface mGroup;
    private OnItemClickAtIndexAtSubIndex<FeedGroupInterface> mOnItemClickAtIndex;
    private MediaControllerCompat.TransportControls mTransportControls;

    private static final int ITEM_CLICK = 0;
    private static final int IMAGE_CLICK = 1;

    public GroupListensFeedItemViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
        super(activity, options, itemView);

        mView = (GroupFeedItemMiniList) itemView;
        mTransportControls = activity.getSupportMediaController().getTransportControls();
    }

    @Override
    public void buildItem(final FeedGroupInterface feedGroup, boolean lastItem, OnLikeClickedListener<FeedGroupInterface> onLikeClickListener,
                          OnItemClickAtIndexAtSubIndex<FeedGroupInterface> itemOrViewMoreListener, IntentRouter router) {
        super.buildItem(feedGroup, lastItem, onLikeClickListener, itemOrViewMoreListener, router);
        mOnItemClickAtIndex = itemOrViewMoreListener;
        mGroup = feedGroup;
        mFeedItems = feedGroup.getActivities();
        mView.setViewModel(new GroupFeedItemMiniList.ViewModel() {

            @Override
            public String getTitle(int index) {
                try {
                    Logger.log("Index", index, mFeedItems.get(index).getObject().getArtistStub().getName());
                    return mFeedItems.get(index).getObject().getArtistStub().getName();
                }
                catch(NullPointerException e) {
                    Logger.exception(e);
                    return "";
                }
            }

            @Override
            public String getSubtitle(int index) {
                try {
                    return mView.getContext().getString(R.string.tracker_count, NumberFormat.getInstance().format(
                            mFeedItems.get(index).getObject().getArtistStub().getTrackerCount())
                    );
                }
                catch(NullPointerException e) {
                    Logger.exception(e);
                    return "";
                }
            }

            @Override
            public Pair<String, Integer> getImageUrlErrorResIdPair(int index) {
                String url = null;
                try {
                    url = mFeedItems.get(index).getObject().getObjectImageUrl();
                }
                catch(NullPointerException e) {
                    Logger.exception(e);
                }
                return Pair.create(url, R.drawable.placeholder_artist_small_square);
            }

            @Override
            public boolean playButtonVisible(int index) {
                return true;
            }

        });

        mView.loadItems(mFeedItems.size(), this, ITEM_CLICK, IMAGE_CLICK);
    }

    @Override
    public void onItemClick(int type, int index, Bundle bundle) {
        Logger.log("Click", index, "item?", type == ITEM_CLICK, "image?", type == IMAGE_CLICK);

        switch(type) {
            case ITEM_CLICK:
                if(mOnItemClickAtIndex != null)
                    mOnItemClickAtIndex.onItemClick(mGroup, getAdapterPosition(), index);
                break;
            case IMAGE_CLICK:
                    if(bundle != null && bundle.getInt(GroupFeedItemMiniListItem.MEDIA_CONTROL_STATE, -1) > -1) {

                        int playbackState = bundle.getInt(GroupFeedItemMiniListItem.MEDIA_CONTROL_STATE, -1);

                        AudioStateManager.getInstance().setCurrent(new AudioStateItem.Builder()
                                .feedId(mFeedItems.get(index).getId())
                                .groupId(mGroup.getGroupId())
                                .knownIndex(getAdapterPosition())
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
                                if(mFeedItems.get(index).getObject().getSpotifyUri() != null) {
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

    public void recycle() {
        AudioStateManager.getInstance().removeListener(this);
    }

    @Override
    public void onAudioStateChanged(AudioStateItem previousItem, AudioStateItem currentItem) {
        syncPlaybackState();
    }
}
