package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedItemSinglePost;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.interfaces.OnFeedMenuItemAdapterClickListener;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;

public class PostFeedItemSingleViewHolder extends AbsActivityFeedSingleViewHolder {

	private FeedItemSinglePost mItem;

	public PostFeedItemSingleViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
		super(activity, options, itemView);
		mItem = (FeedItemSinglePost) mView;
	}

	@Override
	public void buildItem(FeedItemInterface feedItem, boolean lastItem, OnLikeClickedListener<FeedItemInterface> onLikeClickListener,
						  OnFeedMenuItemAdapterClickListener feedMenuItemAdapterClickListener, IntentRouter router) {
		super.buildItem(feedItem, lastItem, onLikeClickListener, feedMenuItemAdapterClickListener, router);

		mItem.setMessage(feedItem.getObject().getPost().getMessage());

		//TODO do we add an on click report to analytics here since a click doesn't do anything?
		if(feedItem.getObject().getPost().getMediaId() > 0)
			mItem.setImage(String.format(FeedValues.BIT_MEDIA_IMAGE_URL, feedItem.getObject().getPost().getMediaId()));
		else
			mItem.setImageGone();
	}

}