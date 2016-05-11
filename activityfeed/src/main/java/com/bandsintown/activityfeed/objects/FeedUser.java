package com.bandsintown.activityfeed.objects;

/**
 * Created by rjaylward on 5/2/16 for Bandsintown
 */
public interface FeedUser {

    int getId();

    String getFirstName();

    String getLastName();

    String getFacebookId();

    int getMediaId();

    String getLocation();

    int getFriendStatus();

    long getExpiration();

    String getLatestActivityItemStreamId();

//	public void setLatestActivityItemStreamId(String id);

    String getImageUrl();

    String getFullName();

    String getEmail();

    String getPhoneNumber();

    boolean getHasMoreActivities();

    void setHasMoreActivities(boolean hasMoreActivities);

    String getOldestActivityStreamId();

    void setOldestActivityStreamId(String oldestActivityStreamId);
}
