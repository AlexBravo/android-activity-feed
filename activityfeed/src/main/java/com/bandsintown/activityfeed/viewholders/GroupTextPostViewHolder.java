package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.GroupTextPostView;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndexAtSubIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
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
						  OnItemClickAtIndexAtSubIndex<FeedGroupInterface> itemOrViewMoreListener, IntentRouter router) {
		super.buildItem(group, lastItem, onLikeClickListener, itemOrViewMoreListener, router);

//		mGroupTextPostView.getFooter().setViewMoreText(mContext.getString(R.string.see_more_posts_from_actor, group.getGroupActor().getActorName()));

		FeedItemInterface firstItem = group.getActivities().get(0);

		mGroupTextPostView.setMessage(firstItem.getObject().getPost().getMessage());

		if(firstItem.getObject().getPost().getMediaId() > 0)
			mGroupTextPostView.setImage(mContext, String.format(FeedValues.BIT_MEDIA_IMAGE_URL, firstItem.getObject().getPost().getMediaId()));
		else
			mGroupTextPostView.setImageGone();
	}
}
