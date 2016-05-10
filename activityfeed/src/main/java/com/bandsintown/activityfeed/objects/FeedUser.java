package com.bandsintown.activityfeed.objects;

/**
 * Created by rjaylward on 5/2/16 for Bandsintown
 */
public interface FeedUser {

    int getId();

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    String getFacebookId();

    int getMediaId();

    String getLocation();

    int getFriendStatus();

    void setFriendStatus(int isFriend);

    long getExpiration();

    String getLatestActivityItemStreamId();

//	public void setLatestActivityItemStreamId(String id);

    String getImageUrl();

    String getFullName();

    void setContactImageUri(String contactImageUri);

    void setContactImageUri(long id);

    String getEmail();

    void setEmail(String email);

    String getPhoneNumber();

    void setPhoneNumber(String phoneNumber);

    boolean getHasMoreActivities();

    void setHasMoreActivities(boolean hasMoreActivities);

    String getOldestActivityStreamId();

    void setOldestActivityStreamId(String oldestActivityStreamId);
}
