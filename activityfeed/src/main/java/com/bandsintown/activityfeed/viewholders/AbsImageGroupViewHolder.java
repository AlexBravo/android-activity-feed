package com.bandsintown.activityfeed.viewholders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bandsintown.activityfeed.FeedItemImageGroupView;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndexAtSubIndex;
import com.bandsintown.activityfeed.interfaces.OnItemClickOfTypeAtIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.util.FeedUtil;

import java.util.ArrayList;

public abstract class AbsImageGroupViewHolder extends AbsActivityFeedGroupViewHolder implements OnItemClickOfTypeAtIndex {

	protected FeedItemImageGroupView mFeedItemImageGroupView;
	private OnItemClickAtIndexAtSubIndex<FeedGroupInterface> mOnItemClickAtIndex;

	protected FeedGroupInterface mGroup;

	public AbsImageGroupViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
		super(activity, options, itemView);
		mFeedItemImageGroupView = (FeedItemImageGroupView) mView;
	}

	@Override
	public void buildItem(FeedGroupInterface group, boolean lastItem, OnLikeClickedListener<FeedGroupInterface> onLikeClickListener,
						  OnItemClickAtIndexAtSubIndex<FeedGroupInterface> itemOrViewMoreListener, IntentRouter router) {
		super.buildItem(group, lastItem, onLikeClickListener, itemOrViewMoreListener, router);

		mOnItemClickAtIndex = itemOrViewMoreListener;
		mGroup = group;

		ArrayList<String> urls = getImageUrls();

		mFeedItemImageGroupView.loadImages(mContext, urls, this);

		boolean hasMessage = false;
		for(FeedItemInterface item : mGroup.getActivities()) {
			if(item.getObject() != null && item.getObject().getPost() != null) {
				String message = item.getObject().getPost().getMessage();

				if(FeedUtil.stringHasContent(message)) {
					hasMessage = true;
					mFeedItemImageGroupView.setMessage(message);
					break;
				}
			}
		}

		if(!hasMessage)
			mFeedItemImageGroupView.setMessageGone();
	}

	protected abstract ArrayList<String> getImageUrls();

	@Override
	public void onItemClick(int type, int index, Bundle bundle) {
		if(mOnItemClickAtIndex != null)
			mOnItemClickAtIndex.onItemClick(mGroup, getAdapterPosition(), index);
	}

}
