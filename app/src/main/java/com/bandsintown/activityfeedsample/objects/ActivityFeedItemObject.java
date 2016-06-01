
package com.bandsintown.activityfeedsample.objects;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.bandsintown.activityfeed.objects.FeedItemObjectInterface;
import com.bandsintown.activityfeed.util.FeedUtil;
import com.bandsintown.activityfeed.util.Logger;
import com.bandsintown.activityfeedsample.Constants;
import com.bandsintown.activityfeedsample.FieldNames;
import com.bandsintown.activityfeedsample.R;
import com.google.gson.annotations.SerializedName;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ActivityFeedItemObject implements Parcelable, FeedItemObjectInterface {

	//Custom deserializer
	@SerializedName("event_id")
	private int mEventId;

	@SerializedName("artist_id")
	private int mArtistId;

	@SerializedName("venue_id")
	private int mVenueId;

	@SerializedName("user_id")
	private int mUserId;

	@SerializedName(Tables.ActivityFeedObjects.OBJECT_ACTIVITY_ID)
	private int mLikedItemId;

	@SerializedName(FieldNames.ACTIVITY_ID)
	private int mActivityId;

	@SerializedName(Tables.ActivityFeedObjects.OBJECT_PLACE_LOCATION)
	private String mLocation;

	@SerializedName(Tables.ActivityFeedObjects.OBJECT_PLACE_LATITUDE)
	private double mLatitude;

	@SerializedName(Tables.ActivityFeedObjects.OBJECT_PLACE_LONGITUDE)
	private double mLongitude;

	//From DB
	private EventStub mEventStub;
	private ArtistStub mArtistStub;
	private VenueStub mVenueStub;
	private User mUser;
	private ActivityFeedItem mLikedItem;

	@SerializedName(FieldNames.TOUR_TRAILER_MEDIA_ID)
	private int mTourTrailerMediaId;

	@SerializedName(FieldNames.SPOTIFY_URI)
	private String mSpotifyUri;

	@SerializedName(FieldNames.POST)
	private Post mPost;

	public ActivityFeedItemObject() {}

	public EventStub getEventStub() {
		return mEventStub;
	}

	public ArtistStub getArtistStub() {
		return mArtistStub;
	}

	//TODO put this back when we support object venues, right now we do not.
//	public VenueStub getVenueStub() {
//		return mVenueStub;
//	}

	public ActivityFeedItem getLikedItem() {
		return mLikedItem;
	}

	public void setEventStub(EventStub eventStub) {
		mEventStub = eventStub;
	}

	public void setArtistStub(ArtistStub artistStub) {
		mArtistStub = artistStub;
	}

	public void setVenueStub(VenueStub venueStub) {
		mVenueStub = venueStub;
	}

	public void setLikedItem(ActivityFeedItem item) {
		mLikedItem = item;
	}

	public Post getPost() {
		return mPost;
	}

	public void setPost(Post post) {
		mPost = post;
	}

	public User getUser() {
		return mUser;
	}

	public void setUser(User user) {
		mUser = user;
	}

	public void setTourTrailerMediaId(int tourTrailerMediaId) {
		mTourTrailerMediaId = tourTrailerMediaId;
	}

	public int getTourTrailerMediaId() {
		return mTourTrailerMediaId;
	}

	public void setSpotifyUri(String spotifyUri) {
		mSpotifyUri = spotifyUri;
	}

	public String getSpotifyUri() {
		return mSpotifyUri;
	}

	public String getObjectImageUrl() {
		if(mLikedItem != null)
			return mLikedItem.getObject().getObjectImageUrl();
		else {
			int mediaId = -1;
			if(mPost != null && mPost.getMediaId() > 0)
				mediaId = getPost().getMediaId();
			else {
				if(mEventStub != null)
					mediaId = mEventStub.getImageId();
				else if(mArtistStub != null)
					mediaId = mArtistStub.getImageId();
			}

			if(mediaId > 0)
				return String.format(Constants.BIT_MEDIA_IMAGE_URL, mediaId);
			else if(mUser != null) {
				if(mUser.getMediaId() > 0)
					return String.format(Constants.THUMB_URL, mUser.getMediaId());
				else if(mUser.getFacebookId() != null)
					return String.format(Constants.FACEBOOK_IMAGE_URL, mUser.getFacebookId());
				else
					return null;
			}
			else
				return null;
		}
	}

	public boolean isObjectImageUrlAUserPost() {
		//size of user posted photo is unknown, so it should be displayed according to its size
		return mPost != null && mPost.getMediaId() > 0;
	}

	public String getObjectTitle(Context context) {
		if(mLikedItem != null)
			return mLikedItem.getObject().getObjectTitle(context);
		else if(mEventStub != null) {
			String title = mEventStub.getTitle();
			if(title == null)
				title = context.getString(R.string.event_title_format, mEventStub.getArtistStub().getName(), mEventStub.getVenueStub().getName());

			return title;
		}
		else if(mArtistStub != null)
			return mArtistStub.getName();
		else {
			Logger.exception(new NullPointerException("no title found"), false);
			return null;
		}
	}

	public String getObjectDescription(Context context) {
		if(mLikedItem != null)
			return mLikedItem.getObject().getObjectDescription(context);
		if(mEventStub != null)
			return FeedUtil.formatDatetime(mEventStub.getStartsAt(), new SimpleDateFormat(Constants.DATE_FORMAT_DAY_OF_WEEK_WITH_YEAR, Locale.getDefault()));
		else if(mArtistStub != null)
			return context.getString(R.string.tracker_count, NumberFormat.getInstance().format(mArtistStub.getTrackerCount()));
		else {
			Logger.exception(new NullPointerException("no description found"), false);
			return null;
		}
	}

	public Intent buildOnClickIntent(Context context) {
		if(mLikedItem != null) {
			return mLikedItem.getObject().buildOnClickIntent(context); //FIXME object is null
//			Intent intent = new Intent(activity, SingleFeedItemActivity.class);
		}
		else {
			Intent intent = new Intent();
//			if(mUserPost != null && mUser.getMediaId() > 0) {
//				//TODO link to looking at the photo
//			}
//			/*else*/ if(mEventStub != null) {
//				intent = new Intent(context, EventActivity.class);
//				intent.putExtra(FieldNames.EVENT_ID, mEventStub.getId());
//			}
//			else if(mArtistStub != null) {
//				intent = new Intent(context, ArtistActivity.class);
//				intent.putExtra(FieldNames.ARTIST_ID, mArtistStub.getId());
//			}
//			else if(mUser != null) {
//				intent = new Intent(context, UserActivity.class);
//				intent.putExtra(FieldNames.USER_ID, mUser.getId());
//			}
//			else
//				intent = null;
//				throw new NullPointerException("no intent could be made");

			return intent;
		}
	}

	/**
	 * Tells if there should be an object title and desc overlaid over the image
	 *
	 * @return if we should show the title and desc
	 */
	public boolean hasImageOverlayTitleAndDesc() {
		return mEventStub != null || mArtistStub != null;
	}

	public int getActivityId() {
		return mActivityId;
	}

	public void setActivityId(int activityId) {
		mActivityId = activityId;
	}

	public int getEventId() {
		return mEventId;
	}

	public void setEventId(int eventId) {
		mEventId = eventId;
	}

	public int getArtistId() {
		return mArtistId;
	}

	public void setArtistId(int artistId) {
		mArtistId = artistId;
	}

	public int getVenueId() {
		return mVenueId;
	}

	public void setVenueId(int venueId) {
		mVenueId = venueId;
	}

	public int getUserId() {
		return mUserId;
	}

	public void setUserId(int userId) {
		mUserId = userId;
	}

	public int getLikedItemId() {
		return mLikedItemId;
	}

	public void setLikedItemId(int likedItemId) {
		mLikedItemId = likedItemId;
	}

	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String location) {
		mLocation = location;
	}

	public double getLatitude() {
		return mLatitude;
	}

	public void setLatitude(double latitude) {
		mLatitude = latitude;
	}

	public double getLongitude() {
		return mLongitude;
	}

	public void setLongitude(double longitude) {
		mLongitude = longitude;
	}

	protected ActivityFeedItemObject(Parcel in) {
		mEventId = in.readInt();
		mArtistId = in.readInt();
		mVenueId = in.readInt();
		mUserId = in.readInt();
		mLikedItemId = in.readInt();
		mActivityId = in.readInt();
		mLocation = in.readString();
		mLatitude = in.readDouble();
		mLongitude = in.readDouble();
		mEventStub = in.readParcelable(EventStub.class.getClassLoader());
		mArtistStub = in.readParcelable(ArtistStub.class.getClassLoader());
		mVenueStub = in.readParcelable(VenueStub.class.getClassLoader());
		mUser = in.readParcelable(User.class.getClassLoader());
		mLikedItem = in.readParcelable(ActivityFeedItem.class.getClassLoader());
		mTourTrailerMediaId = in.readInt();
		mSpotifyUri = in.readString();
		mPost = in.readParcelable(Post.class.getClassLoader());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mEventId);
		dest.writeInt(mArtistId);
		dest.writeInt(mVenueId);
		dest.writeInt(mUserId);
		dest.writeInt(mLikedItemId);
		dest.writeInt(mActivityId);
		dest.writeString(mLocation);
		dest.writeDouble(mLatitude);
		dest.writeDouble(mLongitude);
		dest.writeParcelable(mEventStub, flags);
		dest.writeParcelable(mArtistStub, flags);
		dest.writeParcelable(mVenueStub, flags);
		dest.writeParcelable(mUser, flags);
		dest.writeParcelable(mLikedItem, flags);
		dest.writeInt(mTourTrailerMediaId);
		dest.writeString(mSpotifyUri);
		dest.writeParcelable(mPost, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ActivityFeedItemObject> CREATOR = new Creator<ActivityFeedItemObject>() {

		@Override
		public ActivityFeedItemObject createFromParcel(Parcel in) {
			return new ActivityFeedItemObject(in);
		}

		@Override
		public ActivityFeedItemObject[] newArray(int size) {
			return new ActivityFeedItemObject[size];
		}
	};

}