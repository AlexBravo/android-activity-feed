package com.bandsintown.activityfeedsample.objects;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.bandsintown.activityfeed.objects.FeedArtistStub;
import com.bandsintown.activityfeed.objects.FeedItemActorInterface;
import com.bandsintown.activityfeed.objects.FeedUser;
import com.bandsintown.activityfeedsample.Constants;
import com.bandsintown.activityfeedsample.FieldNames;
import com.google.gson.annotations.SerializedName;

public class ActivityFeedItemActor implements Parcelable, FeedItemActorInterface {

	//Built by our DB, not from JSON
	private ArtistStub mArtist;
	private User mUser;

	//Custom Deserializer for these
	@SerializedName(FieldNames.ARTIST_ID)
	private int mArtistId;

	@SerializedName(FieldNames.USER_ID)
	private int mUserId;

	@SerializedName(FieldNames.ACTIVITY_ID)
	private int mActivityItemId;

	public ActivityFeedItemActor() {}

	protected ActivityFeedItemActor(Parcel in) {
		mArtist = in.readParcelable(ArtistStub.class.getClassLoader());
		mUser = in.readParcelable(User.class.getClassLoader());
	}

	public ArtistStub getArtist() {
		return mArtist;
	}

	public User getUser() {
		return mUser;
	}

	@Override
	public void setArtist(FeedArtistStub feedArtistStub) {
		if(feedArtistStub instanceof ArtistStub)
			mArtist = (ArtistStub) feedArtistStub;
	}

	@Override
	public void setUser(FeedUser feedUser) {
		if(feedUser instanceof User)
			mUser = (User) feedUser;
	}

	public void setArtist(ArtistStub artistStub) {
		mArtist = artistStub;
	}

	public void setUser(User user) {
		mUser = user;
	}

	public String getActorName() {
		if(mArtist != null)
			return mArtist.getName();
		else if(mUser != null)
			return mUser.getFullName(); //TODO was just first name, which do we use?
		else
			throw new NullPointerException("no actor");
	}

	public String getActorImageUrl(boolean thumb) {
		if(mArtist != null)
			return String.format(thumb ? Constants.THUMB_URL : Constants.BIT_MEDIA_IMAGE_URL, mArtist.getImageId());
		else if(mUser != null) {
			if(mUser.getFacebookId() != null)
				return String.format(Constants.FACEBOOK_IMAGE_URL, mUser.getFacebookId());
			else if(mUser.getMediaId() > 0)
				return String.format(thumb ? Constants.THUMB_URL : Constants.BIT_MEDIA_IMAGE_URL, mUser.getMediaId());
			else
				return null;
		}
		else
			throw new NullPointerException("no actor");
	}

	public Intent buildOnClickIntent(Context context) {
		Intent intent = new Intent();
//		if(mArtist != null) {
//			intent = new Intent(context, ArtistActivity.class);
//			intent.putExtra(FieldNames.ARTIST_ID, mArtist.getId());
//		}
//		else if(mUser != null) {
//			if(mUser.getId() == Preferences.getInstance().getUserId())
//				intent = new Intent(context, ManageActivity.class);
//			else {
//				intent = new Intent(context, UserActivity.class);
//				intent.putExtra(Constants.USER_ID, mUser.getId());
//			}
//		}
//		else
//			throw new NullPointerException("no intent could be made");

		return intent;
	}

//	@Override
//	public Uri getUri() {
//		return Tables.ActivityFeedActors.CONTENT_URI;
//	}

	public int getActivityItemId() {
		return mActivityItemId;
	}

	public void setActivityId(int activityItemId) {
		mActivityItemId = activityItemId;
	}

	public int getArtistId() {
		return mArtistId;
	}

	public void setArtistId(int artistId) {
		mArtistId = artistId;
	}

	public int getUserId() {
		return mUserId;
	}

	public void setUserId(int userId) {
		mUserId = userId;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mArtist, flags);
		dest.writeParcelable(mUser, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ActivityFeedItemActor> CREATOR = new Creator<ActivityFeedItemActor>() {

		@Override
		public ActivityFeedItemActor createFromParcel(Parcel in) {
			return new ActivityFeedItemActor(in);
		}

		@Override
		public ActivityFeedItemActor[] newArray(int size) {
			return new ActivityFeedItemActor[size];
		}
	};
}
