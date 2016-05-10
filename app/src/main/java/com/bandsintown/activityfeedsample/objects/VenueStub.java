package com.bandsintown.activityfeedsample.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.bandsintown.activityfeed.objects.FeedVenueStub;
import com.bandsintown.activityfeedsample.FieldNames;
import com.google.gson.annotations.SerializedName;

public class VenueStub implements Parcelable, FeedVenueStub {

	@SerializedName(FieldNames.ID)
	private int mId;

	@SerializedName(FieldNames.NAME)
	private String mName;

	@SerializedName(FieldNames.LOCATION)
	private String mLocation;

	@SerializedName(FieldNames.LATITUDE)
	private double mLatitude;

	@SerializedName(FieldNames.LONGITUDE)
	private double mLongitude;

	public VenueStub() { }

	protected VenueStub(Parcel in) {
		mId = in.readInt();
		mName = in.readString();
		mLocation = in.readString();
		mLatitude = in.readDouble();
		mLongitude = in.readDouble();
	}

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public String getLocation() {
		return mLocation;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

//	@Override
//	public Uri getUri() {
//		return Tables.VenueStubs.CONTENT_URI;
//	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mId);
		dest.writeString(mName);
		dest.writeString(mLocation);
		dest.writeDouble(mLatitude);
		dest.writeDouble(mLongitude);
	}

	public static final Creator<VenueStub> CREATOR = new Creator<VenueStub>() {

		@Override
		public VenueStub createFromParcel(Parcel in) {
			return new VenueStub(in);
		}

		@Override
		public VenueStub[] newArray(int size) {
			return new VenueStub[size];
		}
	};

}
