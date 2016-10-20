package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.GroupTextPostView;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndexAtSubIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.interfaces.OnLinkClickListener;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;

public class GroupTextPostViewHolder extends AbsActivityFeedGroupViewHolder {

	private GroupTextPostView mGroupTextPostView;

	public GroupTextPostViewHolder(AppCompatActivity context, FeedViewOptions options, View itemView) {
		super(context, options, itemView);

		mGroupTextPostView = (GroupTextPostView) itemView;
	}

	@Override
	public void buildItem(FeedGroupInterface group, boolean lastItem, OnLikeClickedListener<FeedGroupInterface> onLikeClickListener,
						  OnItemClickAtIndexAtSubIndex<FeedGroupInterface> itemOrViewMoreListener, final IntentRouter router) {
		super.buildItem(group, lastItem, onLikeClickListener, itemOrViewMoreListener, router);

		FeedItemInterface firstItem = group.getActivities().get(0);

		mGroupTextPostView.setMessage(firstItem.getObject().getPost().getMessage());

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
}
