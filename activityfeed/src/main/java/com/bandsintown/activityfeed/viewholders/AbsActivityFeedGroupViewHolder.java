package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;

import com.bandsintown.activityfeed.view.AbsFeedItemGroupView;
import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndexAtSubIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.util.FeedUtil;
import com.bandsintown.activityfeed.util.Logger;

public class AbsActivityFeedGroupViewHolder extends RecyclerView.ViewHolder {

	protected AppCompatActivity mContext;
	protected AbsFeedItemGroupView mView;
	protected FeedViewOptions mOptions;

	public AbsActivityFeedGroupViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
		super(itemView);

		mContext = activity;
		mView = (AbsFeedItemGroupView) itemView;

		mOptions = options;
	}

	public void buildItem(final FeedGroupInterface group, boolean lastItem, final OnLikeClickedListener<FeedGroupInterface> onLikeClickListener,
						  final OnItemClickAtIndexAtSubIndex<FeedGroupInterface> itemOrViewMoreListener, final IntentRouter router) {

		String timestamp = DateUtils.getRelativeTimeSpanString(FeedUtil.convertDatetimeToMillis(group.getLatestDatetime())).toString();

		mView.getHeader().setName(String.valueOf(group.getGroupActor().getActorName()));
		mView.getHeader().setPosterImage(ImageProvider.getInstance(mContext), group.getGroupActor().getActorImageUrl(true));
		mView.getHeader().setDescription(buildDescription(group));
		mView.getHeader().setTimestamp(timestamp);

		updateLikeStatus(group);

		mView.getFooter().setOptions(mOptions);

		mView.getHeader().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				router.onHeaderClicked(group);
			}

		});

		mView.getFooter().setLikeClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mView.getFooter().onLikeClick(group.isGroupLikedByUser());

				if(onLikeClickListener != null) {
					if(group.isGroupLikedByUser()) // unlike the post
						onLikeClickListener.onLike(group, false);
					else
						onLikeClickListener.onLike(group, true);
				}
			}

		});

		mView.getFooter().setViewMoreOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(itemOrViewMoreListener != null)
					itemOrViewMoreListener.onItemClick(group, getAdapterPosition(), 0);
			}

		});
	}

	public void updateLikeStatus(FeedGroupInterface group) {
		if(group != null && mView != null && mView.getFooter() != null)
			mView.getFooter().setInitialState(group);
	}

	private String buildDescription(FeedGroupInterface group) {
		try {
			if(group.getVerb() != null) {
				switch(group.getVerb()) {
					case FeedValues.VERB_USER_TRACKING :
					case FeedValues.VERB_ARTIST_TRACKING :
						return mContext.getString(R.string.description_tracked);
					case FeedValues.VERB_RSVP :
						return mContext.getString(R.string.rsvped_to);
					case FeedValues.VERB_LIKE :
						return mContext.getString(R.string.liked);
					case FeedValues.VERB_LISTEN :
						return mContext.getString(R.string.description_listened_to);
					case FeedValues.VERB_REQUEST : {
						int numberOfArtists = group.getActivities().size();
						String artistsCount = mContext.getResources().getQuantityString(R.plurals.artists_count, numberOfArtists, numberOfArtists);

						//TODO change below to the location from the object once it is available
						String location = group.getGroupActor().getUser().getLocation();

						return mContext.getString(R.string.description_requested_play_city, artistsCount, location);
					}
					case FeedValues.VERB_RATE :
						return mContext.getString(R.string.description_rated_multiple_events, group.getActivities().size());
					case FeedValues.VERB_USER_POST :
					case FeedValues.VERB_ARTIST_POST :
					case FeedValues.VERB_MESSAGE_RSVPS : //TODO does this have a diff desc?
						return mContext.getString(R.string.description_posted);
					case FeedValues.VERB_EVENT_ANNOUNCEMENT :
						return "announced"; //TODO CHANGE THIS to something more accurate, not all event_announcement verbs are announcing new shows, could be on sale or rsvp reminders
					case FeedValues.VERB_PROMOTE :
						return "promoted"; //TODO CHANGE THIS, SAME AS ABOVE
					case FeedValues.VERB_WATCH_TRAILER :
						return mContext.getString(R.string.description_watched_a_tour_trailer);
					case FeedValues.VERB_POST_TRAILER :
						return mContext.getString(R.string.description_posted_a_tour_trailer); //THIS WONT EVER HAPPEN SINCE TOUR TRAILER POSTS ARE FORCED FLAT BUT WE AT LEAST HAVE SOMETHING
					default:
						return null;
				}
			}
			else {
				Logger.exception(new Exception("Feed item " + group.getGroupId() + " has a null description key"));
				return null;
			}
		}
		catch(Exception e) {
			Logger.exception(e);
			return null;
		}
	}
}
