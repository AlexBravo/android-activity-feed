package com.bandsintown.activityfeedsample.objects;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.bandsintown.activityfeed.objects.FeedEventStub;
import com.bandsintown.activityfeed.objects.FeedUser;
import com.bandsintown.activityfeedsample.Constants;
import com.bandsintown.activityfeedsample.FieldNames;
import com.bandsintown.activityfeedsample.R;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;

public class EventStub implements Parcelable, FeedEventStub {

	@SerializedName(FieldNames.ID)
	private int mId;

	@SerializedName(FieldNames.TITLE)
	private String mTitle;

	@SerializedName(FieldNames.STARTS_AT)
	private String mStartsAt;

	@SerializedName(FieldNames.MEDIA_ID)
	private int mImageId;

	@SerializedName(FieldNames.VENUE_ID)
	private int mVenueId;

	@SerializedName(FieldNames.ARTIST_ID)
	private int mArtistId;

	@SerializedName(FieldNames.RSVP_COUNT)
	private int mRsvpCount;

	@SerializedName(FieldNames.ANNOUNCED_AT)
	private String mAnnouncedAt;

	@SerializedName(FieldNames.BASED_ON)
	private String mBasedOn;

	@SerializedName("event_calendar_uri")
	private String mCalendarEventUri;

	//Just convenience, not stored in JSON or DB
	private ArtistStub mArtistStub;
	private VenueStub mVenueStub;
	private int mRsvpStatus = Constants.ATTENDEE_STATUS_DOESNT_EXIST;
	private ArrayList<User> mFriendAttendees = new ArrayList<>();

	public EventStub() { }

	protected EventStub(Parcel in) {
		mId = in.readInt();
		mTitle = in.readString();
		mStartsAt = in.readString();
		mAnnouncedAt = in.readString();
		mImageId = in.readInt();
		mVenueId = in.readInt();
		mArtistId = in.readInt();
		mBasedOn = in.readString();
		mRsvpCount = in.readInt();
		mArtistStub = in.readParcelable(ArtistStub.class.getClassLoader());
		mVenueStub = in.readParcelable(VenueStub.class.getClassLoader());
		mRsvpStatus = in.readInt();
		in.readTypedList(mFriendAttendees, User.CREATOR);
	}

	public int getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getStartsAt() {
		return mStartsAt;
	}

	public String getAnnouncedAt() {
		return mAnnouncedAt;
	}

	public int getImageId() {
		return mImageId;
	}

	public int getVenueId() {
		return mVenueId;
	}

	public void setArtistId(int artistId) {
		mArtistId = artistId;
	}

	public int getArtistId() {
		return mArtistId;
	}

	public String getBasedOn() {
		return mBasedOn;
	}

//	@Override
//	public Uri getUri() {
//		return Tables.EventStubs.CONTENT_URI;
//	}

	public ArtistStub getArtistStub() {
		return mArtistStub;
	}

	public void setArtistStub(ArtistStub artistStub) {
		mArtistStub = artistStub;
	}

	public VenueStub getVenueStub() {
		return mVenueStub;
	}

	public void setVenueStub(VenueStub venueStub) {
		mVenueStub = venueStub;
	}

	public int getRsvpStatus() {
		return mRsvpStatus;
	}

	public void setRsvpStatus(int rsvpStatus) {
		mRsvpStatus = rsvpStatus;
	}

	public void clearRsvpStatus() {
		mRsvpStatus = Constants.ATTENDEE_STATUS_DOESNT_EXIST;
	}

	public int getRsvpCount() {
		return mRsvpCount;
	}

	public ArrayList<? extends FeedUser> getFriendAttendees() {
		return mFriendAttendees;
	}

	public void addAttendee(User attendee) {
		mFriendAttendees.add(attendee);
	}

	public void clearAttendees() {
		mFriendAttendees.clear();
	}

	public String getFormattedTitle(Context context) {
		return context.getString(R.string.event_title_format, mArtistStub.getName(), mTitle != null ? mTitle : mVenueStub.getName());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(mId)
				.append(mTitle)
				.append(mArtistId)
				.append(mRsvpCount)
				.append(mRsvpStatus)
				.build();
	}

	@Override
	public boolean equals(Object o) {
		try {
			EventStub eventStub = (EventStub) o;
			return eventStub.hashCode() == hashCode();
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
		dest.writeString(mTitle);
		dest.writeString(mStartsAt);
		dest.writeString(mAnnouncedAt);
		dest.writeInt(mImageId);
		dest.writeInt(mVenueId);
		dest.writeInt(mArtistId);
		dest.writeString(mBasedOn);
		dest.writeInt(mRsvpCount);
		dest.writeParcelable(mArtistStub, flags);
		dest.writeParcelable(mVenueStub, flags);
		dest.writeInt(mRsvpStatus);
		dest.writeTypedList(mFriendAttendees);
	}

	public static final Creator<EventStub> CREATOR = new Creator<EventStub>() {

		@Override
		public EventStub createFromParcel(Parcel in) {
			return new EventStub(in);
		}

		@Override
		public EventStub[] newArray(int size) {
			return new EventStub[size];
		}

	};

	public String getCalendarEventUri() {
		return mCalendarEventUri;
	}

}