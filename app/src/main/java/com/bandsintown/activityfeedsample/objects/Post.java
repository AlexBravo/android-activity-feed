package com.bandsintown.activityfeedsample.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.bandsintown.activityfeed.objects.FeedPost;
import com.bandsintown.activityfeedsample.FieldNames;
import com.google.gson.annotations.SerializedName;

public class Post implements Parcelable, FeedPost {

	@SerializedName(FieldNames.ACTIVITY_ID)
	private int mActivityId;

	@SerializedName(FieldNames.MESSAGE)
	private String mMessage;

	@SerializedName(FieldNames.MEDIA_ID)
	private int mMediaId;

	@SerializedName(FieldNames.RATING)
	private double mRating;

	public Post() { }

	protected Post(Parcel in) {
		mActivityId = in.readInt();
		mMessage = in.readString();
		mMediaId = in.readInt();
		mRating = in.readDouble();
	}
//
//	@Override
//	public Uri getUri() {
//		return Tables.Posts.CONTENT_URI;
//	}

	public int getActivityId() {
		return mActivityId;
	}

	public void setActivityId(int activityId) {
		mActivityId = activityId;
	}

	public String getMessage() {
		return mMessage;
	}

	public int getMediaId() {
		return mMediaId;
	}

	public double getRating() {
		return mRating;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mActivityId);
		dest.writeString(mMessage);
		dest.writeInt(mMediaId);
		dest.writeDouble(mRating);
	}

	public static final Creator<Post> CREATOR = new Creator<Post>() {

		@Override
		public Post createFromParcel(Parcel in) {
			return new Post(in);
		}

		@Override
		public Post[] newArray(int size) {
			return new Post[size];
		}
	};

}
