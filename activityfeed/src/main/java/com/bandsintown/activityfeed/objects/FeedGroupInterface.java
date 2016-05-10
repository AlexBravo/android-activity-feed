package com.bandsintown.activityfeed.objects;

import java.util.ArrayList;

public interface FeedGroupInterface extends ActivityFeedEntry {

    String getGroupId();

    String getVerb();

    ArrayList<? extends FeedItemInterface> getActivities();

    void setGroupId(String groupId);

    void setVerb(String verb);

//    void addActivityItem(FeedItemInterface item);

    String getLatestDatetime();

    FeedItemActorInterface getGroupActor();

    boolean isGroupLikedByUser();

    int getGroupLikeCount();

    boolean isSingleItem();

}