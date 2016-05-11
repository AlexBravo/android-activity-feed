package com.bandsintown.activityfeed.objects;

import android.content.Context;
import android.content.Intent;

public interface FeedItemObjectInterface {

	FeedEventStub getEventStub();

	FeedArtistStub getArtistStub();

	//TODO put this back when we support object venues, right now we do not.
//  FeedVenueStub getVenueStub();

	FeedItemInterface getLikedItem();

	FeedPost getPost();

	FeedUser getUser();

	int getTourTrailerMediaId();

	String getSpotifyUri();

	String getObjectImageUrl();

	boolean isObjectImageUrlAUserPost();

	String getObjectTitle(Context context);

	String getObjectDescription(Context context);

	Intent buildOnClickIntent(Context context);

	boolean hasImageOverlayTitleAndDesc();

	int getActivityId();

	int getEventId();

	int getArtistId();

	int getVenueId();

	int getUserId();

	int getLikedItemId();

	String getLocation();

	double getLatitude();

	double getLongitude();

}