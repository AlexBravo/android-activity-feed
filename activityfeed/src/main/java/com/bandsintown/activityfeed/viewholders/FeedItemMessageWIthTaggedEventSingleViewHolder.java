package com.bandsintown.activityfeed.viewholders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bandsintown.activityfeed.FeedItemSingleMessageWithTaggedEvent;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.interfaces.OnFeedMenuItemAdapterClickListener;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.util.AnalyticsHelper;
import com.bandsintown.activityfeed.util.AnalyticsTags;

public class FeedItemMessageWIthTaggedEventSingleViewHolder extends AbsActivityFeedSingleViewHolder {

	private FeedItemSingleMessageWithTaggedEvent mItem;

	public FeedItemMessageWIthTaggedEventSingleViewHolder(AppCompatActivity activity, View itemView) {
		super(activity, itemView);
		mItem = (FeedItemSingleMessageWithTaggedEvent) mView;
	}

	@Override
	public void buildItem(final FeedItemInterface feedItem, boolean lastItem, final OnLikeClickedListener<FeedItemInterface> onLikeClickListener,
						  OnFeedMenuItemAdapterClickListener feedMenuItemAdapterClickListener, IntentRouter router) {
		super.buildItem(feedItem, lastItem, onLikeClickListener, feedMenuItemAdapterClickListener, router);

		String imageUrl = feedItem.getObject().getObjectImageUrl();
		boolean isUserPostedImage = feedItem.getObject().isObjectImageUrlAUserPost();

		if(imageUrl != null)
			mItem.setImage(mActivity, imageUrl, isUserPostedImage);

		if(feedItem.getObject().getPost() != null && feedItem.getObject().getPost().getMessage() != null)
			mItem.setUserMessage(feedItem.getObject().getPost().getMessage());
		else
			mItem.hideUserMessageView();

		if(feedItem.getObject().hasImageOverlayTitleAndDesc()) {
			mItem.showTextSection();
			mItem.setObjectTitle(feedItem.getObject().getObjectTitle(mActivity));
			mItem.setObjectDescription(feedItem.getObject().getObjectDescription(mActivity));

			if(imageUrl == null)
				mItem.setDefaultImage(R.drawable.placeholder_big_image);
		}
		else if(imageUrl == null)
			mItem.hideEventSection();
		else {
			//This is to prevent a recycling problem where this occasionally shows up even though it isn't supposed to.
			mItem.hideTextSection();
		}

		mItem.setOnImageClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelper.trackEvent(AnalyticsTags.ACTIVITY_FEED_ITEM_CLICK, AnalyticsTags.OBJECT);
				Intent intent = feedItem.getObject().buildOnClickIntent(mActivity);

//				Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(mActivity, R.anim.slide_from_right, R.anim.shrink_into_background).toBundle();
				if(intent != null)
					mActivity.startActivity(intent);
			}

		});

		//TODO second click listener for event if the click intent above is for the image preview
	}
}
