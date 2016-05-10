package com.bandsintown.activityfeedsample.objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.bandsintown.activityfeed.objects.FeedItemActorInterface;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.FeedItemObjectInterface;
import com.bandsintown.activityfeed.util.FeedPrefs;
import com.bandsintown.activityfeedsample.Constants;
import com.bandsintown.activityfeedsample.FieldNames;
import com.bandsintown.activityfeedsample.R;
import com.google.gson.annotations.SerializedName;

public class ActivityFeedItem implements FeedItemInterface, Parcelable {

	@SerializedName(FieldNames.ID)
	private int mId;

	@SerializedName(FieldNames.STREAM_ID)
	private String mStreamId;

	@SerializedName(FieldNames.VERB)
	private String mVerb;

	@SerializedName(FieldNames.CATEGORY)
	private String mCategory;

	@SerializedName(FieldNames.DESCRIPTION_KEY)
	private String mDescriptionKey;

	@SerializedName(FieldNames.TIMESTAMP)
	private String mTimestamp;

	@SerializedName(FieldNames.LIKE_COUNT)
	private int mLikeCount;

	@SerializedName(FieldNames.IS_LIKED)
	private boolean mIsLikedByUser;

	@SerializedName(FieldNames.SEEN)
	private boolean mSeen;

	@SerializedName(FieldNames.ACTOR)
	private ActivityFeedItemActor mActor;

	@SerializedName(FieldNames.OBJECT)
	private ActivityFeedItemObject mObject;

	public ActivityFeedItem() {}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	@Override
	public Type getType() {
		return Type.ITEM;
	}

	@Override
	public Object getIdentifier() {
		return mId;
	}

	public String getVerb() {
		return mVerb;
	}

	@Override
	public int getSize() {
		return 1;
	}

	public void setVerb(String verb) {
		mVerb = verb;
	}

	public String getCategory() {
		if(mActor.getArtist() != null)
			return Constants.CATEGORY_ARTIST;
		else if(mActor.getUser() != null) {
			if(mActor.getUser().getId() == FeedPrefs.getInstance().getUserId())
				return Constants.CATEGORY_ME;
			else if(mActor.getUser().getFriendStatus() == Constants.FRIEND_FRIENDED) {
				return Constants.CATEGORY_FRIENDS;
			}
			else
				return Constants.CATEGORY_USER;
		}
		else
			return Constants.CATEGORY_NONE;
	}

	public String getDatetime() {
		return mTimestamp;
	}

	public int getLikeCount() {
		return mLikeCount;
	}

	public void setLikeCount(int count) {
		mLikeCount = count;
	}

	public void incrementLikeCountByAmount(int amount) {
		mLikeCount += amount;
	}

	public boolean isLikedByUser() {
		return mIsLikedByUser;
	}

	public void setIsLikedByUser(boolean isLikedByUser) {
		mIsLikedByUser = isLikedByUser;
	}

	public ActivityFeedItemActor getActor() {
		return mActor;
	}

	@Override
	public void setActor(FeedItemActorInterface actor) {
		if(actor instanceof ActivityFeedItemActor)
			mActor = (ActivityFeedItemActor) actor;
	}

	public void setActor(ActivityFeedItemActor actor) {
		mActor = actor;
	}

	public ActivityFeedItemObject getObject() {
		return mObject;
	}

	@Override
	public void setObject(FeedItemObjectInterface object) {
		if(object instanceof ActivityFeedItemObject)
			mObject = (ActivityFeedItemObject) object;
	}

	public void setObject(ActivityFeedItemObject object) {
		mObject = object;
	}

	public boolean isSeen() {
		return mSeen;
	}

//	@Override
//	public Uri getUri() {
//		return Tables.ActivityFeedItems.CONTENT_URI;
//	}

	public String getStreamId() {
		return mStreamId;
	}

	public String getDescriptionKey() {
		return mDescriptionKey;
	}

	public void setDescriptionKey(String key) {
		mDescriptionKey = key;
	}

	public String getActivityTypeFormatted(AppCompatActivity activity) {
		return formatActivityType(activity, this);
	}

	public String getLikedActivityTypeFormatted(AppCompatActivity activity) {
		return formatActivityType(activity, mObject.getLikedItem());
	}

	private static String formatActivityType(AppCompatActivity activity, ActivityFeedItem item) {
		switch(item.getVerb()) {
			case Constants.VERB_RSVP :
				return activity.getString(R.string.activity_type_rsvp);
			case Constants.VERB_USER_POST :
				switch(item.getDescriptionKey()) {
					case Constants.COMMENTED :
						return activity.getString(R.string.activity_type_comment);
					case Constants.POSTED :
						return activity.getString(R.string.activity_type_post);
					default :
						return null;
				}
			case Constants.VERB_ARTIST_POST :
				return activity.getString(R.string.activity_type_post);
			case Constants.VERB_LISTEN :
				return activity.getString(R.string.activity_type_listen);
			case Constants.VERB_ARTIST_TRACKING :
			case Constants.VERB_USER_TRACKING :
				return activity.getString(R.string.activity_type_track);
			case Constants.VERB_REQUEST :
				return activity.getString(R.string.activity_type_request);
			case Constants.VERB_RATE :
				return activity.getString(R.string.activity_type_rate);
			case Constants.VERB_EVENT_ANNOUNCEMENT :
				return activity.getString(R.string.event_announcement);
			case Constants.VERB_WATCH_TRAILER :
			case Constants.VERB_POST_TRAILER :
				return activity.getString(R.string.activity_type_tour_trailer);
			default :
				return null;
		}
	}

	protected ActivityFeedItem(Parcel in) {
		mId = in.readInt();
		mStreamId = in.readString();
		mVerb = in.readString();
		mCategory = in.readString();
		mDescriptionKey = in.readString();
		mTimestamp = in.readString();
		mLikeCount = in.readInt();
		mIsLikedByUser = in.readByte() != 0;
		mSeen = in.readByte() != 0;
		mActor = in.readParcelable(ActivityFeedItemActor.class.getClassLoader());
		mObject = in.readParcelable(ActivityFeedItemObject.class.getClassLoader());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mId);
		dest.writeString(mStreamId);
		dest.writeString(mVerb);
		dest.writeString(mCategory);
		dest.writeString(mDescriptionKey);
		dest.writeString(mTimestamp);
		dest.writeInt(mLikeCount);
		dest.writeByte((byte) (mIsLikedByUser ? 1 : 0));
		dest.writeByte((byte) (mSeen ? 1 : 0));
		dest.writeParcelable(mActor, flags);
		dest.writeParcelable(mObject, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ActivityFeedItem> CREATOR = new Creator<ActivityFeedItem>() {

		@Override
		public ActivityFeedItem createFromParcel(Parcel in) {
			return new ActivityFeedItem(in);
		}

		@Override
		public ActivityFeedItem[] newArray(int size) {
			return new ActivityFeedItem[size];
		}
	};

}