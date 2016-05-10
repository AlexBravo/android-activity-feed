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

	void setArtistId(int artistId);

	int getArtistId();

	String getBasedOn();

	FeedArtistStub getArtistStub();

	void setArtistStub(FeedArtistStub feedArtistStub);

	FeedVenueStub getVenueStub();

	void setVenueStub(FeedVenueStub venueStub);

	int getRsvpStatus();

	void setRsvpStatus(int rsvpStatus);

	void clearRsvpStatus();

	int getRsvpCount();

	ArrayList<? extends FeedUser> getFriendAttendees();

	void addAttendee(FeedUser attendee);

	void clearAttendees();

	String getFormattedTitle(Context context);

	String getCalendarEventUri();

}