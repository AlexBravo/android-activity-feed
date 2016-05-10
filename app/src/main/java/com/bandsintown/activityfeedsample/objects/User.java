package com.bandsintown.activityfeedsample.objects;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import com.bandsintown.activityfeed.objects.FeedUser;
import com.bandsintown.activityfeedsample.Constants;
import com.bandsintown.activityfeedsample.FieldNames;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable, FeedUser {

	@SerializedName(FieldNames.ID)
	private int mId;

	@SerializedName(FieldNames.FIRST_NAME)
	private String mFirstName;

	@SerializedName(FieldNames.LAST_NAME)
	private String mLastName;

	@SerializedName(FieldNames.FACEBOOK_UID)
	private String mFacebookId;

	@SerializedName(FieldNames.MEDIA_ID)
	private int mMediaId;

	@SerializedName(FieldNames.LOCATION)
	private String mLocation;

	@SerializedName(FieldNames.EXPIRATION_DATE)
	private long mExpiration;

	@SerializedName(Tables.Users.LATEST_ACTIVITY_ITEM_STREAM_ID)
	private String mLatestActivityItemStreamId;

	@SerializedName(Tables.Users.OLDEST_ACTIVITY_ITEM_STREAM_ID)
	private String mOldestActivityStreamId;

	@SerializedName(Tables.Users.HAS_MORE_ACTIVITIES)
	private boolean mHasMoreActivities;

	//Not stored on users table, just helper
	private int mFriendStatus;
	private String mContactImageUri;
	private String mEmail;
	private String mPhoneNumber;

	public User() {	}

	protected User(Parcel in) {
		mId = in.readInt();
		mFirstName = in.readString();
		mLastName = in.readString();
		mFacebookId = in.readString();
		mMediaId = in.readInt();
		mLocation = in.readString();
		mFriendStatus = in.readInt();
		mContactImageUri = in.readString();
		mEmail = in.readString();
		mPhoneNumber = in.readString();
		mHasMoreActivities = in.readByte() != 0;
	}

	public int getId() {
		return mId;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public void setFirstName(String firstName) {
		mFirstName = firstName;
	}

	public String getLastName() {
		return mLastName;
	}

	public String getFacebookId() {
		return mFacebookId;
	}

	public int getMediaId() {
		return mMediaId;
	}

	public String getLocation() {
		return mLocation;
	}

	public int getFriendStatus() {
		return mFriendStatus;
	}

	public void setFriendStatus(int isFriend) {
		mFriendStatus = isFriend;
	}

	public long getExpiration() {
		return mExpiration;
	}

	public String getLatestActivityItemStreamId() {
		return mLatestActivityItemStreamId;
	}

//	public void setLatestActivityItemStreamId(String id) {
//		mLatestActivityItemStreamId = id;
//	}

//	@Override
//	public Uri getUri() {
//		return Tables.Users.CONTENT_URI;
//	}

	public String getImageUrl() {
		if(mFacebookId != null)
			return String.format(Constants.FACEBOOK_IMAGE_URL, mFacebookId);
		else if(mMediaId > 0)
			return String.format(Constants.THUMB_URL, mMediaId);
		else if(mContactImageUri != null)
			return mContactImageUri;
		else
			return null;
	}

	public String getFullName() {
		StringBuilder builder = new StringBuilder();

		if(mFirstName != null) {
			builder.append(mFirstName);
			builder.append(" ");
		}

		if(mLastName != null)
			builder.append(mLastName);

		return builder.toString().trim();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mId);
		dest.writeString(mFirstName);
		dest.writeString(mLastName);
		dest.writeString(mFacebookId);
		dest.writeInt(mMediaId);
		dest.writeString(mLocation);
		dest.writeInt(mFriendStatus);
		dest.writeString(mContactImageUri);
		dest.writeString(mEmail);
		dest.writeString(mPhoneNumber);
		dest.writeByte((byte) (mHasMoreActivities ? 1 : 0));
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	public void setContactImageUri(String contactImageUri) {
		mContactImageUri = contactImageUri;
	}

	public void setContactImageUri(long id) {
		Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
		mContactImageUri = String.valueOf(uri);
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		mEmail = email;
	}

	public String getPhoneNumber() {
		return mPhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		mPhoneNumber = phoneNumber;
	}

	public boolean getHasMoreActivities() {
		return mHasMoreActivities;
	}

	public void setHasMoreActivities(boolean hasMoreActivities) {
		mHasMoreActivities = hasMoreActivities;
	}

	public String getOldestActivityStreamId() {
		return mOldestActivityStreamId;
	}

	public void setOldestActivityStreamId(String oldestActivityStreamId) {
		mOldestActivityStreamId = oldestActivityStreamId;
	}
}
