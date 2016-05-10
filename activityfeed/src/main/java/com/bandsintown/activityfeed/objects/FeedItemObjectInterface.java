package com.bandsintown.activityfeed.objects;

import android.content.Context;
import android.content.Intent;

public interface FeedItemObjectInterface {

	FeedEventStub getEventStub();

	FeedArtistStub getArtistStub();

	//TODO put this back when we support object venues, right now we do not.
//  FeedVenueStub getVenueStub();

	FeedItemInterface getLikedItem();

	void setEventStub(FeedEventStub feedEventStub);

	void setArtistStub(FeedArtistStub feedArtistStub);

	void setVenueStub(FeedVenueStub venueStub);

	void setLikedItem(FeedItemInterface item);

	FeedPost getPost();

	void setPost(FeedPost post);

	FeedUser getUser();

	void setUser(FeedUser feedUser);

	void setTourTrailerMediaId(int tourTrailerMediaId);

	int getTourTrailerMediaId();

	void setSpotifyUri(String spotifyUri);

	String getSpotifyUri();

	String getObjectImageUrl();

	boolean isObjectImageUrlAUserPost();

	String getObjectTitle(Context context);

	String getObjectDescription(Context context);

	Intent buildOnClickIntent(Context context);

	boolean hasImageOverlayTitleAndDesc();

	int getActivityId();

	void setActivityId(int activityId);

	int getEventId();

	void setEventId(int eventId);

	int getArtistId();

	void setArtistId(int artistId);

	int getVenueId();

	void setVenueId(int venueId);

	int getUserId();

	void setUserId(int userId);

	int getLikedItemId();

	void setLikedItemId(int likedItemId);

	String getLocation();

	void setLocation(String location);

	double getLatitude();

	void setLatitude(double latitude);

	double getLongitude();

	void setLongitude(double longitude);

}