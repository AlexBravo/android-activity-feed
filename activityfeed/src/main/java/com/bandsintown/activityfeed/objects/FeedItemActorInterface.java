package com.bandsintown.activityfeed.objects;

import android.content.Context;
import android.content.Intent;

public interface FeedItemActorInterface {

	FeedArtistStub getArtist();

	FeedUser getUser();

	String getActorName();

	String getActorImageUrl(boolean thumb);

	Intent buildOnClickIntent(Context context);

	int getActivityItemId();

	int getArtistId();

	int getUserId();

}
