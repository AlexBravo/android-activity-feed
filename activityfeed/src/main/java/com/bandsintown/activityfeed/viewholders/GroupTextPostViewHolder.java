package com.bandsintown.activityfeed.viewholders;

import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.GroupTextPostView;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.audio.AudioStateItem;
import com.bandsintown.activityfeed.audio.AudioStateManager;
import com.bandsintown.activityfeed.audio.OnAudioStateChangeListener;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndex;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndexAtSubIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.interfaces.OnLinkClickListener;
import com.bandsintown.activityfeed.interfaces.Recycleable;
import com.bandsintown.activityfeed.objects.AudioPreviewInfo;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.objects.RecyclingPreviewViewHelper;

import java.util.Collections;

import static com.bandsintown.activityfeed.objects.RecyclingPreviewViewHelper.IMAGE_CLICK;
import static com.bandsintown.activityfeed.objects.RecyclingPreviewViewHelper.ITEM_CLICK;

public class GroupTextPostViewHolder extends AbsActivityFeedGroupViewHolder implements Recycleable, OnAudioStateChangeListener {

	private GroupTextPostView mGroupTextPostView;
	private MediaControllerCompat.TransportControls mTransportControls;
	private RecyclingPreviewViewHelper mAudioPreviewHelper;

	public GroupTextPostViewHolder(AppCompatActivity context, FeedViewOptions options, View itemView) {
		super(context, options, itemView);
		mGroupTextPostView = (GroupTextPostView) itemView;
		mTransportControls = context.getSupportMediaController().getTransportControls();
	}

	@Override
	public void buildItem(FeedGroupInterface group, boolean lastItem, OnLikeClickedListener<FeedGroupInterface> onLikeClickListener,
						  OnItemClickAtIndexAtSubIndex<FeedGroupInterface> itemOrViewMoreListener, final IntentRouter router) {
		super.buildItem(group, lastItem, onLikeClickListener, itemOrViewMoreListener, router);

		FeedItemInterface firstItem = group.getActivities().get(0);

		mGroupTextPostView.setMessage(firstItem.getObject().getPost().getMessage());
		AudioPreviewInfo audioInfo = mOptions.getLinkProcessor()
				.process(firstItem.getObject().getPost().getMessage());
		if(audioInfo != null)
			setUpAudioPreview(audioInfo, group, router);
		else
			hideAudioPreview();

		if(firstItem.getObject().getPost().getMediaId() > 0)
			mGroupTextPostView.setImage(mContext, String.format(FeedValues.BIT_MEDIA_IMAGE_URL,
					firstItem.getObject().getPost().getMediaId()));
		else
			mGroupTextPostView.setImageGone();

		if(group.getGroupActor().getArtistId() > 0) {
			mGroupTextPostView.setMessageLinksClickable(true, new OnLinkClickListener() {

				@Override
				public boolean onClick(TextView textView, String url) {
					return router.onLinkClicked(url);
				}

			});
		}
		else
			mGroupTextPostView.setMessageLinksClickable(false, null);
	}

	private void hideAudioPreview() {
		mGroupTextPostView.getMusicPreviewCardView().setVisibility(View.GONE);
	}

	private void setUpAudioPreview(AudioPreviewInfo audioInfo, FeedGroupInterface group, final IntentRouter router) {
		mGroupTextPostView.getMusicPreviewCardView().setVisibility(View.VISIBLE);
		OnItemClickAtIndex<AudioPreviewInfo> previewBodyClickListener = new OnItemClickAtIndex<AudioPreviewInfo>() {

			@Override
			public void onItemClick(AudioPreviewInfo item, int index) {
				//this means the user clicked on the body of the preview instead of the
				if(item.getUrlInfoWasGeneratedFrom() != null)
					router.onLinkClicked(item.getUrlInfoWasGeneratedFrom());
			}
		};

		mAudioPreviewHelper = new RecyclingPreviewViewHelper(Collections.singletonList(audioInfo),
				group, mTransportControls, mGroupTextPostView, getAdapterPosition(), previewBodyClickListener);

		mGroupTextPostView.getMusicPreviewCardView()
				.setOnClickOfTypeAtListener(mAudioPreviewHelper, ITEM_CLICK, IMAGE_CLICK);

		String title = audioInfo.getSource().toUpperCase();
		String subtitle = audioInfo.getUrlInfoWasGeneratedFrom();

		mGroupTextPostView.getMusicPreviewCardView().setText(title, subtitle);
		mGroupTextPostView.getMusicPreviewCardView()
				.setImage(null, null, R.drawable.spotify_icon_with_black_background);
	}

	@Override
	public void recycle() {
		AudioStateManager.getInstance().removeListener(this);
	}

	@Override
	public void onAudioStateChanged(AudioStateItem previousItem, AudioStateItem currentItem) {
		if(mAudioPreviewHelper != null)
			mAudioPreviewHelper.onAudioStateChanged(previousItem, currentItem);
	}
}
