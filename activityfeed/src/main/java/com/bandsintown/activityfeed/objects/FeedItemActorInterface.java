package com.bandsintown.activityfeed.objects;

import android.content.Context;
import android.content.Intent;

public interface FeedItemActorInterface {

	FeedArtistStub getArtist();

	FeedUser getUser();

	void setArtist(FeedArtistStub feedArtistStub);

	void setUser(FeedUser feedUser);

	String getActorName();

	String getActorImageUrl(boolean thumb);

	Intent buildOnClickIntent(Context context);

	int getActivityItemId();

	void setActivityId(int activityItemId);

	int getArtistId();

	void setArtistId(int artistId);

	int getUserId();

	void setUserId(int userId);

}
