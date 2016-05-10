package com.bandsintown.activityfeed.objects;

public interface FeedArtistStub {

	void setName(String name);

	void setScore(int score);

	//used for the more (...) button
	void setId(int id);

	int getId();

	String getName();

	int getImageId();

	boolean isOnTour();

	int getScore();

	String getSource();

	int getTrackerCount();

	int getTrackedStatus();

	void setTrackedStatus(int trackedStatus);

}
