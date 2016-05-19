package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;

import com.bandsintown.activityfeed.AbsFeedItemSingleView;
import com.bandsintown.activityfeed.FeedItemSingleFooterView;
import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.interfaces.OnFeedMenuItemAdapterClickListener;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.util.AnalyticsHelper;
import com.bandsintown.activityfeed.util.AnalyticsTags;
import com.bandsintown.activityfeed.util.FeedUtil;
import com.bandsintown.activityfeed.util.Print;

public abstract class AbsActivityFeedSingleViewHolder extends RecyclerView.ViewHolder {

	protected AppCompatActivity mActivity;
	protected AbsFeedItemSingleView mView;
	protected FeedViewOptions mOptions;

	public AbsActivityFeedSingleViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
		super(itemView);

		mActivity = activity;
		mView = (AbsFeedItemSingleView) itemView;

		mOptions = options;
	}

	public void buildItem(final FeedItemInterface feedItem, boolean lastItem, final OnLikeClickedListener<FeedItemInterface> onLikeClickListener,
						  final OnFeedMenuItemAdapterClickListener feedMenuItemClickListener, final IntentRouter router) {

		String timestamp = DateUtils.getRelativeTimeSpanString(FeedUtil.convertDatetimeToMillis(feedItem.getDatetime())).toString();

		mView.getHeader().setName(String.valueOf(feedItem.getActor().getActorName()));
		mView.getHeader().setPosterImage(ImageProvider.getInstance(mActivity), feedItem.getActor().getActorImageUrl(true));
		mView.getHeader().setDescription(buildDescription(feedItem));
		mView.getHeader().setTimestamp(timestamp);

		updateLikeStatus(feedItem, router);

		mView.getHeader().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AnalyticsHelper.trackEvent(AnalyticsTags.ACTIVITY_FEED_ITEM_CLICK, AnalyticsTags.ACTOR);
				router.onHeaderClicked(mActivity, feedItem);
			}

		});

		mView.getFooter().setOptions(mOptions);

		mView.getFooter().setLikeClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mView.getFooter().onLikeClick(feedItem.isLikedByUser(), feedItem.getLikeCount());
				AnalyticsHelper.trackEvent(AnalyticsTags.ACTIVITY_FEED_ITEM_CLICK, AnalyticsTags.LIKE);

				if(feedItem.isLikedByUser()) // unlike the post
					onLikeClickListener.onLike(feedItem, false);
				else
					onLikeClickListener.onLike(feedItem, true);
			}

		});

		mView.getFooter().setOnLikesTotalClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(feedItem.getLikeCount() > 0 && mActivity != null) {
					router.onLikesTotalClick(mActivity, feedItem);
				}
			}

		});

		mView.getFooter().setFeedMenuItemClickListener(new FeedItemSingleFooterView.OnFeedFooterMenuClickListener() {

			@Override
			public void onReportClick(int feedId) {
				if(feedMenuItemClickListener != null) {
					AnalyticsHelper.trackEvent(AnalyticsTags.ACTIVITY_FEED_ITEM_CLICK, AnalyticsTags.MORE_OPTIONS_ICON);
					feedMenuItemClickListener.onReportClick(feedId, getAdapterPosition());
				}
			}

			@Override
			public void onDeleteClick(int feedId) {
				if(feedMenuItemClickListener != null) {
					AnalyticsHelper.trackEvent(AnalyticsTags.ACTIVITY_FEED_ITEM_CLICK, AnalyticsTags.MORE_OPTIONS_ICON);
					feedMenuItemClickListener.onDeleteClick(feedId, getAdapterPosition());
				}
			}

		});

		mView.getFooter().setFeedMenuButtonClickListener(new FeedItemSingleFooterView.OnFeedFooterMenuButtonClickListener() {

			@Override
			public void onMenuButtonClick() {
				AnalyticsHelper.trackEvent(AnalyticsTags.ACTIVITY_FEED_ITEM_CLICK, AnalyticsTags.MORE_OPTIONS_ICON);
			}

		});

	}

	public void updateLikeStatus(FeedItemInterface feedItem, IntentRouter router) {
		if(feedItem != null && mView != null && mView.getFooter() != null)
			mView.getFooter().setInitialState(feedItem, router);
	}

	private String buildDescription(FeedItemInterface item) {
		try {
			if(item.getDescriptionKey() != null) {
				switch(item.getDescriptionKey()) {
					case FeedValues.TRACKED:
						return mActivity.getString(R.string.description_tracked);
					case FeedValues.LIKES_YOUR_ACTIVITY:
						return mActivity.getString(R.string.description_likes_your_activity);
					case FeedValues.LISTENED_TO:
						return mActivity.getString(R.string.description_listened_to);
					case FeedValues.REQUESTED_PLAY_CITY:
						return mActivity.getString(R.string.description_requested_play_city, item.getObject().getArtistStub().getName(),
								item.getActor().getUser().getLocation());
					case FeedValues.RATED_EVENT:
						return mActivity.getString(R.string.description_rated_event, item.getObject().getEventStub().getFormattedTitle(mActivity),
								FeedUtil.getStars(mActivity, item));
					case FeedValues.RSVP_GOING:
						return mActivity.getString(FeedUtil.checkIfDatetimeHasPassed(item.getObject().getEventStub().getStartsAt()) ? R.string.description_rsvp__past_tense
								: R.string.description_rsvp_going);
					case FeedValues.RSVP_MAYBE:
						return mActivity.getString(FeedUtil.checkIfDatetimeHasPassed(item.getObject().getEventStub().getStartsAt()) ? R.string.description_rsvp__past_tense
								: R.string.description_rsvp_maybe);
					case FeedValues.COMMENTED:
						return mActivity.getString(R.string.description_commented);
					case FeedValues.POSTED:
						return mActivity.getString(R.string.description_posted);
					case FeedValues.PLAYING_TOMORROW:
						return mActivity.getString(R.string.description_playing_tomorrow);
					case FeedValues.ON_SALE_TOMORROW:
						return mActivity.getString(R.string.description_on_sale_tomorrow);
					case FeedValues.PLAYING_TODAY:
						return mActivity.getString(R.string.description_playing_today);
					case FeedValues.ON_SALE_TODAY:
						return mActivity.getString(R.string.description_on_sale_today);
					case FeedValues.JUST_ANNOUNCED:
						return mActivity.getString(R.string.description_just_announced);
					case FeedValues.NEXT_MONTH:
						return mActivity.getString(R.string.description_next_month);
					case FeedValues.THIS_WEEKEND:
						return mActivity.getString(R.string.description_this_weekend);
					case FeedValues.NEXT_WEEK:
						return mActivity.getString(R.string.description_next_week);
					case FeedValues.WATCHED_TRAILER:
						return mActivity.getString(R.string.description_watched_a_tour_trailer);
					case FeedValues.POSTED_TRAILER:
						return mActivity.getString(R.string.description_posted_a_tour_trailer);
					default:
						return null;
				}
			} else {
				Print.exception(new Exception("Feed item " + item.getId() + " has a null description key"));
				return null;
			}
		}
		catch(Exception e) {
			Print.exception(e);
			return null;
		}
	}

	//TODO not being used
//	protected void setLikeCount(int likeCount) {
//		if(likeCount > 0) {
//			mView.getFooter().showLikeCount();
//			mView.getFooter().setLikeCount(likeCount);
//		}
//		else
//			mView.getFooter().hideLikeCount();
//	}

}
