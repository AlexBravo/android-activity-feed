package com.bandsintown.activityfeed.viewholders;

import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.GroupTextPostView;
import com.bandsintown.activityfeed.R;
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

public class GroupTextPostViewHolder extends AbsActivityFeedGroupViewHolder implements Recycleable {

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
		AudioPreviewInfo audioInfo = mOptions.getLinkProcessor().process(firstItem.getObject().getPost().getMessage());
		if(audioInfo != null)
			setUpAudioPreview(audioInfo, group, router);

		if(firstItem.getObject().getPost().getMediaId() > 0)
			mGroupTextPostView.setImage(mContext, String.format(FeedValues.BIT_MEDIA_IMAGE_URL, firstItem.getObject().getPost().getMediaId()));
		else
			mGroupTextPostView.setImageGone();

		if(group.getGroupActor().getArtist() != null) {
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

	private void setUpAudioPreview(AudioPreviewInfo audioInfo, FeedGroupInterface group, final IntentRouter router) {
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

		String title = audioInfo.getSource().toUpperCase();
		String subtitle = audioInfo.getUrlInfoWasGeneratedFrom();

		mGroupTextPostView.setUpAudioControls(R.drawable.placeholder_artist_small_square, title, subtitle);
	}

	@Override
	public void recycle() {
		if(mAudioPreviewHelper != null)
			mAudioPreviewHelper.recycle();
	}

}
