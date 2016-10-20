package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bandsintown.activityfeed.FeedItemSingleMessageWithTaggedEvent;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.interfaces.OnFeedMenuItemAdapterClickListener;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.interfaces.OnLinkClickListener;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;

public class FeedItemMessageWIthTaggedEventSingleViewHolder extends AbsActivityFeedSingleViewHolder {

	private FeedItemSingleMessageWithTaggedEvent mItem;

	public FeedItemMessageWIthTaggedEventSingleViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
		super(activity, options, itemView);
		mItem = (FeedItemSingleMessageWithTaggedEvent) mView;
	}

	@Override
	public void buildItem(final FeedItemInterface feedItem, boolean lastItem, final OnLikeClickedListener<FeedItemInterface> onLikeClickListener,
						  OnFeedMenuItemAdapterClickListener feedMenuItemAdapterClickListener, final IntentRouter router) {
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
			mItem.hideImageSection();
		else {
			//This is to prevent a recycling problem where this occasionally shows up even though it isn't supposed to.
			mItem.hideTextSection();
		}

		mItem.setOnImageClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				router.onObjectClicked(feedItem);
			}

		});

		if(feedItem.getActor().getArtist() != null)
			mItem.setMessageLinksClickable(false, null);
		else
			mItem.setMessageLinksClickable(true, new OnLinkClickListener() {

				@Override
				public boolean onClick(TextView textView, String url) {
					return router.onLinkClicked(url);
				}

			});

		//TODO second click listener for event if the click intent above is for the image preview
	}
}
