package com.bandsintown.activityfeed.objects;

import android.support.v7.app.AppCompatActivity;

public interface FeedItemInterface extends ActivityFeedEntry {

	int getId();

	void setId(int id);

	String getVerb();

	void setVerb(String verb);

	String getCategory();

	String getDatetime();

	int getLikeCount();

	void setLikeCount(int count);

	void incrementLikeCountByAmount(int amount);

	boolean isLikedByUser();

	void setIsLikedByUser(boolean isLikedByUser);

	FeedItemActorInterface getActor();

	void setActor(FeedItemActorInterface actor);

	FeedItemObjectInterface getObject();

	void setObject(FeedItemObjectInterface object);

	boolean isSeen();

	String getStreamId();

	String getDescriptionKey();

	void setDescriptionKey(String key);

	String getActivityTypeFormatted(AppCompatActivity activity);

	String getLikedActivityTypeFormatted(AppCompatActivity activity);

//	static String formatActivityType(AppCompatActivity activity, FeedItemInterface item) {
//		switch(item.getVerb()) {
//			case FeedValues.VERB_RSVP :
//				return activity.getString(R.string.activity_type_rsvp);
//			case FeedValues.VERB_USER_POST :
//				switch(item.getDescriptionKey()) {
//					case FeedValues.COMMENTED :
//						return activity.getString(R.string.activity_type_comment);
//					case FeedValues.POSTED :
//						return activity.getString(R.string.activity_type_post);
//					default :
//						return null;
//				}
//			case FeedValues.VERB_ARTIST_POST :
//				return activity.getString(R.string.activity_type_post);
//			case FeedValues.VERB_LISTEN :
//				return activity.getString(R.string.activity_type_listen);
//			case FeedValues.VERB_ARTIST_TRACKING :
//			case FeedValues.VERB_USER_TRACKING :
//				return activity.getString(R.string.activity_type_track);
//			case FeedValues.VERB_REQUEST :
//				return activity.getString(R.string.activity_type_request);
//			case FeedValues.VERB_RATE :
//				return activity.getString(R.string.activity_type_rate);
//			case FeedValues.VERB_EVENT_ANNOUNCEMENT :
//				return activity.getString(R.string.event_announcement);
//			case FeedValues.VERB_WATCH_TRAILER :
//			case FeedValues.VERB_POST_TRAILER :
//				return activity.getString(R.string.activity_type_tour_trailer);
//			default :
//				return null;
//		}
//	}

}