package com.bandsintown.activityfeedsample.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.bandsintown.activityfeed.objects.FeedArtistStub;
import com.bandsintown.activityfeedsample.FieldNames;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ArtistStub implements Parcelable, FeedArtistStub {

	@SerializedName(FieldNames.ID)
	private int mId;

	@SerializedName(FieldNames.NAME)
	private String mName;

	@SerializedName(FieldNames.MEDIA_ID)
	private int mImageId;

	@SerializedName(FieldNames.SCORE)
	private int mScore = -1;

	@SerializedName(FieldNames.ON_TOUR)
	private boolean mOnTour;

	@SerializedName(FieldNames.SOURCE)
	private String mSource;

	@SerializedName(FieldNames.TRACKER_COUNT)
	private int mTrackerCount;

	//Not stored in db or given in JSON
	private int mTrackedStatus;

	public ArtistStub() { }

	protected ArtistStub(Parcel in) {
		mId = in.readInt();
		mName = in.readString();
		mImageId = in.readInt();
		mScore = in.readInt();
		mSource = in.readString();
		mTrackerCount = in.readInt();
		mTrackedStatus = in.readInt();
	}

	public void setName(String name) {
		mName = name;
	}

	public void setScore(int score) {
		mScore = score;
	}

	//used for the more (...) button
	public void setId(int id) {
		mId = id;
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public int getImageId() {
		return mImageId;
	}

	public boolean isOnTour() {
		return mOnTour;
	}

	public int getScore() {
		return mScore;
	}

	public String getSource() {
		return mSource;
	}

	public int getTrackerCount() {
		return mTrackerCount;
	}

	public int getTrackedStatus() {
		return mTrackedStatus;
	}

	public void setTrackedStatus(int trackedStatus) {
		mTrackedStatus = trackedStatus;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(mId)
				.append(mName)
				.append(mTrackerCount)
				.build();
	}

	@Override
	public boolean equals(Object o) {
		try {
			ArtistStub artistStub = (ArtistStub) o;
			return artistStub.hashCode() == hashCode();
		} catch(ClassCastException e) {
			return false;
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mId);
		dest.writeString(mName);
		dest.writeInt(mImageId);
		dest.writeInt(mScore);
		dest.writeString(mSource);
		dest.writeInt(mTrackerCount);
		dest.writeInt(mTrackedStatus);
	}

	public static final Creator<ArtistStub> CREATOR = new Creator<ArtistStub>() {

		@Override
		public ArtistStub createFromParcel(Parcel in) {
			return new ArtistStub(in);
		}

		@Override
		public ArtistStub[] newArray(int size) {
			return new ArtistStub[size];
		}

	};

}
