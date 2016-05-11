package com.bandsintown.activityfeed.objects;

import android.content.Context;

import java.util.ArrayList;

public interface FeedEventStub {

	int getId();

	String getTitle();

	String getStartsAt();

	String getAnnouncedAt();

	int getImageId();

	int getVenueId();

	int getArtistId();

	String getBasedOn();

	FeedArtistStub getArtistStub();

	FeedVenueStub getVenueStub();

	int getRsvpStatus();

	void setRsvpStatus(int rsvpStatus);

	void clearRsvpStatus();

	int getRsvpCount();

	ArrayList<? extends FeedUser> getFriendAttendees();

	void clearAttendees();

	String getFormattedTitle(Context context);

	String getCalendarEventUri();

}