package com.bandsintown.activityfeedsample.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.FeedItemActorInterface;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.util.FeedUtil;
import com.bandsintown.activityfeedsample.Constants;
import com.bandsintown.activityfeedsample.FieldNames;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ActivityFeedGroup implements FeedGroupInterface, Parcelable {

    @SerializedName(FieldNames.GROUP_ID)
    private String mGroupId;

    @SerializedName(FieldNames.VERB)
    private String mVerb;

    @SerializedName(FieldNames.ACTIVITIES)
    private ArrayList<ActivityFeedItem> mActivities;

//    @Override
//    public Uri getUri() {
//        return Tables.ActivityFeedGroups.CONTENT_URI;
//    }

    public ActivityFeedGroup() {}

    public String getGroupId() {
        return mGroupId;
    }

    @Override
    public Type getType() {
        return Type.GROUP;
    }

    @Override
    public Object getIdentifier() {
        return mGroupId;
    }

    public String getVerb() {
        return mVerb;
    }

    @Override
    public int getSize() {
        if(mActivities != null)
            return mActivities.size();
        else
            return 0;
    }

    public ArrayList<ActivityFeedItem> getActivities() {
        return mActivities;
    }

    public void setGroupId(String groupId) {
        mGroupId = groupId;
    }

    public void setVerb(String verb) {
        mVerb = verb;
    }

    public void addActivityItem(ActivityFeedItem item) {
        if(mActivities == null)
            mActivities = new ArrayList<>();

        mActivities.add(item);
    }

    public String getLatestDatetime() {
        String latestDatetime = null;
        for(ActivityFeedItem item : mActivities) {
            if(latestDatetime == null)
                latestDatetime = item.getDatetime();
            else if(FeedUtil.compareDatetimes(latestDatetime, item.getDatetime()) == 2)
                latestDatetime = item.getDatetime();
        }

        return latestDatetime;
    }

    public FeedItemActorInterface getGroupActor() {
        return mActivities.get(0).getActor();
    }

    public boolean isGroupLikedByUser() {
        for(FeedItemInterface item : mActivities) {
            if(!item.isLikedByUser())
                return false;
        }

        return true;
    }

    public int getGroupLikeCount() {
        return 0; //TODO how are we going to handle group likes? at all?
    }

    public boolean isSingleItem() {
        //It's considered single if it has one item, or is a post trailer object, which we flatten
        return mActivities.size() == 1 || mVerb.equals(Constants.VERB_POST_TRAILER);
    }

    protected ActivityFeedGroup(Parcel in) {
        mGroupId = in.readString();
        mVerb = in.readString();
        mActivities = in.createTypedArrayList(ActivityFeedItem.CREATOR);
    }

//    public String getActorsCount(Context context) {
////		//Shouldn't happen, but to prevent the same actor getting in twice
////        ArrayList<String> actors = new ArrayList<>();
////
////		for(ActivityFeedItem item : mActivities) {
////			String actorName = item.getActor().getActorName();
////			if(!actors.contains(actorName))
////				actors.add(actorName);
////		}
////
////        //TODO remove and as well as comma when data not there
////		StringBuilder builder = new StringBuilder();
////		for(int i = 0; i < actors.size(); i++) {
////			builder.append(actors.get(i));
////
////			if(i == mActivities.size() - 1) {
////				//Do nothing, too lazy to write this if/else in a way to avoid this empty block
////			}
////			else if(i == mActivities.size() - 2) {
////				builder.append(" ");
////				builder.append(context.getString(R.string.and));
////				builder.append(" ");
////			}
////			else
////				builder.append(", ");
////		}
////
////		return builder.toString();
//	}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mGroupId);
        dest.writeString(mVerb);
        dest.writeTypedList(mActivities);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActivityFeedGroup> CREATOR = new Creator<ActivityFeedGroup>() {

        @Override
        public ActivityFeedGroup createFromParcel(Parcel in) {
            return new ActivityFeedGroup(in);
        }

        @Override
        public ActivityFeedGroup[] newArray(int size) {
            return new ActivityFeedGroup[size];
        }
    };

}