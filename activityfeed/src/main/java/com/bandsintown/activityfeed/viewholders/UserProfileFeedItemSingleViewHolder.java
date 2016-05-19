package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.support.v8.renderscript.RenderScript;
import android.view.View;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedItemSingleUserProfile;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.interfaces.OnFeedMenuItemAdapterClickListener;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.objects.FeedUser;
import com.bandsintown.activityfeed.util.AnalyticsHelper;
import com.bandsintown.activityfeed.util.FeedAnalyticsTags;
import com.bandsintown.activityfeed.util.Print;

public class UserProfileFeedItemSingleViewHolder extends AbsActivityFeedSingleViewHolder {

	private FeedItemSingleUserProfile mFeedItem;

	private RenderScript mRenderScript;

	public UserProfileFeedItemSingleViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
		super(activity, options, itemView);

		mFeedItem = (FeedItemSingleUserProfile) itemView;

		try {
			mRenderScript = RenderScript.create(mActivity.getApplicationContext());
		}
		catch(Exception e) {
			Print.log("Renderscript Exception");
			Print.exception(e);
		}
	}

	@Override
	public void buildItem(final FeedItemInterface feedItem, boolean lastItem, OnLikeClickedListener<FeedItemInterface> onLikeClickListener,
						  OnFeedMenuItemAdapterClickListener feedMenuItemAdapterClickListener, final IntentRouter router) {
		super.buildItem(feedItem, lastItem, onLikeClickListener, feedMenuItemAdapterClickListener, router);

		final FeedUser objectUser = feedItem.getObject().getUser();

		mFeedItem.setLocation(objectUser.getLocation());
		mFeedItem.setName(objectUser.getFullName());

		float userPicSize = mActivity.getResources().getDimension(R.dimen.user_profile_inset_image_size);

		String imageUri;
		if(objectUser.getMediaId() > 0)
			imageUri = String.format(FeedValues.BIT_MEDIA_IMAGE_URL, objectUser.getMediaId());
		else if(objectUser.getFacebookId() != null)
			imageUri = String.format(FeedValues.FACEBOOK_IMAGE_URL, objectUser.getFacebookId());
		else
			imageUri = null;

		mFeedItem.setImages(mRenderScript, imageUri, userPicSize);

		mFeedItem.setImagesClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelper.trackEvent(FeedAnalyticsTags.ACTIVITY_FEED_ITEM_CLICK, FeedAnalyticsTags.OBJECT);
				router.onObjectClicked(feedItem);
			}

		});
	}

}
