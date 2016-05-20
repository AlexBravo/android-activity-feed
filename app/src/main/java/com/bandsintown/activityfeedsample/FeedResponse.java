package com.bandsintown.activityfeedsample;

import com.bandsintown.activityfeedsample.objects.ActivityFeedGroup;
import com.bandsintown.activityfeedsample.objects.ActivityFeedItem;
import com.bandsintown.activityfeedsample.objects.ArtistStub;
import com.bandsintown.activityfeedsample.objects.EventStub;
import com.bandsintown.activityfeedsample.objects.User;
import com.bandsintown.activityfeedsample.objects.VenueStub;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class FeedResponse {

    @SerializedName(FieldNames.FEED)
    protected ArrayList<ActivityFeedGroup> mGroups;

    @SerializedName(FieldNames.EVENTS)
    protected ArrayList<EventStub> mEvents;

    @SerializedName(FieldNames.ARTISTS)
    protected ArrayList<ArtistStub> mArtists;

    @SerializedName(FieldNames.VENUES)
    protected ArrayList<VenueStub> mVenues;

    @SerializedName(FieldNames.USERS)
    protected ArrayList<User> mUsers;

    @SerializedName(FieldNames.LIKED_ACTIVITIES)
    protected ArrayList<ActivityFeedItem> mLikedActivities;

    public ArrayList<ActivityFeedGroup> getGroups() {
        return mGroups;
    }

    protected String getListName() {
        return "me";
    }

}
