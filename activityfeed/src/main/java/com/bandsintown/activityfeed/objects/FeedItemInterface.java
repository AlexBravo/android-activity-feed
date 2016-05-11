package com.bandsintown.activityfeed.objects;

import android.support.v7.app.AppCompatActivity;

public interface FeedItemInterface extends ActivityFeedEntry {

	int getId();

	void setId(int id);

	String getVerb();

	String getCategory();

	String getDatetime();

	int getLikeCount();

	void incrementLikeCountByAmount(int amount);

	boolean isLikedByUser();

	void setIsLikedByUser(boolean isLikedByUser);

	FeedItemActorInterface getActor();

	FeedItemObjectInterface getObject();

	boolean isSeen();

	String getStreamId();

	String getDescriptionKey();

	String getActivityTypeFormatted(AppCompatActivity activity);

	String getLikedActivityTypeFormatted(AppCompatActivity activity);

}