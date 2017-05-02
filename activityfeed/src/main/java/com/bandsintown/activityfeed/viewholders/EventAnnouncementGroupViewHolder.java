package com.bandsintown.activityfeed.viewholders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bandsintown.activityfeed.view.EventAnnouncementGroupView;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndexAtSubIndex;
import com.bandsintown.activityfeed.interfaces.OnItemClickOfTypeAtIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;

public class EventAnnouncementGroupViewHolder extends AbsActivityFeedGroupViewHolder implements OnItemClickOfTypeAtIndex {

	private EventAnnouncementGroupView mEventAnnouncementGroupView;
	private FeedGroupInterface mGroup;
	private OnItemClickAtIndexAtSubIndex<FeedGroupInterface> mOnItemClickAtIndex;

	public EventAnnouncementGroupViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
		super(activity, options, itemView);

		mEventAnnouncementGroupView = (EventAnnouncementGroupView) itemView;
	}

	@Override
	public void buildItem(FeedGroupInterface group, boolean lastItem, OnLikeClickedListener<FeedGroupInterface> onLikeClickListener,
						  OnItemClickAtIndexAtSubIndex<FeedGroupInterface> itemOrViewMoreListener, IntentRouter router) {
		super.buildItem(group, lastItem, onLikeClickListener, itemOrViewMoreListener, router);

		mGroup = group;
		mOnItemClickAtIndex = itemOrViewMoreListener;

		mEventAnnouncementGroupView.buildItem(mContext, group, this);
		mEventAnnouncementGroupView.setDescription(mContext.getString(R.string.count_new_shows_in_your_area, group.getActivities().size()));
	}

	@Override
	public void onItemClick(int type, int index, Bundle bundle) {
		mOnItemClickAtIndex.onItemClick(mGroup, getAdapterPosition(), index);
	}

}
