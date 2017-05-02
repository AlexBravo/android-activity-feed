package com.bandsintown.activityfeed;

/**
 * Created by rjaylward on 5/2/16 for Bandsintown
 */
public class FeedValues {

    //Time periods
    public static final long ONE_MIN_MILLIS = 1000 * 60;
    public static final long FIVE_MIN_MILLIS = ONE_MIN_MILLIS * 5;
    public static final long ONE_HOUR_MILLIS = 1000 * 60 * 60;
    public static final long ONE_DAY_MILLIS = ONE_HOUR_MILLIS * 24;
    public static final long ONE_WEEK_MILLIS = ONE_DAY_MILLIS * 7;
    public static final long FOUR_WEEKS_MILLIS = ONE_WEEK_MILLIS * 4;
    public static final long ONE_YEAR_MILLIS = ONE_DAY_MILLIS * 365;

    //Date format strings
    public static final String DATE_FORMAT_CALENDAR_DAY = "MM/dd/yy";
    public static final String DATE_FORMAT_DATE_FILTER = "yyyy-MM-dd";
    public static final String DATE_FORMAT_EVENT_LISTING = "MMM dd - h:mm a";
    public static final String DATE_FORMAT_BANDSINTOWN_API = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_BANDSINTOWN_API_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ssZZ";
    public static final String DATE_FORMAT_MONTH_ABV = "MMM";
    public static final String DATE_FORMAT_DAY = "dd";
    public static final String DATE_FORMAT_YEAR = "yyyy";
    public static final String DATE_FORMAT_DAY_OF_WEEK_WITH_YEAR = "EEEE, MMMM d, yyyy"; //TODO figure out the date suffixes (may 5*th*)
    public static final String DATE_FORMAT_MONTH_ABBREVIATION_WITH_YEAR = "MMMM d yyyy";
    public static final String DATE_FORMAT_DAY_OF_WEEK = "EEEE, MMM d";
    public static final String DATE_FORMAT_SHORT_DAY_OF_WEEK = "EE, MMM d";
    public static final String DATE_FORMAT_MONTH_ABV_WITH_DAY = "MMM dd";
    public static final String DATE_FORMAT_TIMER = "m:ss";


    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final int REQUEST_CODE_VIEW_GROUP_FEED = 30;

    //Image URLs
    public static final String BIT_MEDIA_IMAGE_URL = "http://photos.bandsintown.com/large/%s.jpeg";
    public static final String THUMB_URL = "http://photos.bandsintown.com/thumb/%s.jpeg";
    public static final String FACEBOOK_IMAGE_URL = "https://graph.facebook.com/%s/picture?type=large";

    public static final String VIDEO_BASE_URL = "https://s3.amazonaws.com/bit-artist-videos/%s.mp4";
    public static final String VIDEO_PREVIEW_BASE_URL = "http://s3.amazonaws.com/bit-artist-videos/%s.jpeg";


    //Verbs
    public static final String VERB_USER_TRACKING = "user_tracking";
    public static final String VERB_ARTIST_TRACKING = "artist_tracking";
    public static final String VERB_RSVP = "rsvp";
    public static final String VERB_LIKE = "like";
    public static final String VERB_LISTEN = "listen";
    public static final String VERB_REQUEST = "request";
    public static final String VERB_RATE = "rate";
    public static final String VERB_USER_POST = "user_post";
    public static final String VERB_EVENT_ANNOUNCEMENT = "event_announcement";
    public static final String VERB_MESSAGE_RSVPS = "message_rsvps";
    public static final String VERB_PROMOTE = "promote";
    public static final String VERB_ARTIST_POST = "artist_post";
    public static final String VERB_WATCH_TRAILER = "watch_trailer";
    public static final String VERB_POST_TRAILER = "post_trailer";
	public static final String VERB_INVITE = "invite";

    public static final String VERB_ACTIVITY_FEED_LOADING = "activity_feed_loading";

    //Verb int codes
    public static final int VERB_CODE_USER_TRACKING = 100;
    public static final int VERB_CODE_ARTIST_TRACKING = 101;
    public static final int VERB_CODE_EVENT_ANNOUNCEMENT = 103;
    public static final int VERB_CODE_RSVP = 102;
    public static final int VERB_CODE_LIKE = 104;
    public static final int VERB_CODE_LISTEN = 112;
    public static final int VERB_CODE_REQUEST = 113;
    public static final int VERB_CODE_RATE = 116;
    public static final int VERB_CODE_USER_POST = 118;
    public static final int VERB_CODE_MESSAGE_RSVPS = 119;
    public static final int VERB_CODE_PROMOTE = 120;
    public static final int VERB_CODE_ARTIST_POST = 121;
    public static final int VERB_CODE_WATCH_TRAILER = 122;
    public static final int VERB_CODE_POST_TRAILER = 123;
    public static final int VERB_CODE_INVITE = 124;

    public static final int VERB_CODE_GROUP_USER_TRACKING = 200;
    public static final int VERB_CODE_GROUP_ARTIST_TRACKING = 201;
    public static final int VERB_CODE_GROUP_EVENT_ANNOUNCEMENT = 203;
    public static final int VERB_CODE_GROUP_RSVP = 202;
    public static final int VERB_CODE_GROUP_LIKE = 204;
    public static final int VERB_CODE_GROUP_LISTEN = 212;
    public static final int VERB_CODE_GROUP_REQUEST = 213;
    public static final int VERB_CODE_GROUP_RATE = 216;
    public static final int VERB_CODE_GROUP_USER_POST = 218;
    public static final int VERB_CODE_GROUP_MESSAGE_RSVPS = 219;
    public static final int VERB_CODE_GROUP_PROMOTE = 220;
    public static final int VERB_CODE_GROUP_ARTIST_POST = 221;
    public static final int VERB_CODE_GROUP_WATCH_TRAILER = 222;
    public static final int VERB_CODE_GROUP_POST_TRAILER = 223;
    public static final int VERB_CODE_GROUP_ARTIST_POST_ALL_IMAGES = 224;
    public static final int VERB_CODE_GROUP_USER_POST_ALL_IMAGES = 225;
    public static final int VERB_CODE_GROUP_INVITE = 226;

    public static final int VERB_CODE_UNRECOGNIZED = -1;
    public static final int VERB_CODE_ACTIVITY_FEED_LOADING = -99;

    public static final int SNACKBAR_SHORT_DURATION = 1500;

    //Activity feed description keys
    public static final String TRACKED = "tracked";
    public static final String LIKES_YOUR_ACTIVITY = "likes_your_activity";
    public static final String LISTENED_TO = "listened_to";
    public static final String REQUESTED_PLAY_CITY = "requested_play_city";
    public static final String RATED_EVENT = "rated_event";
    public static final String RSVP_GOING = "rsvp_going";
    public static final String RSVP_MAYBE = "rsvp_maybe";
    public static final String COMMENTED = "commented";
    public static final String POSTED = "posted";
    public static final String PLAYING_TOMORROW = "playing_tomorrow";
    public static final String ON_SALE_TOMORROW = "on_sale_tomorrow";
    public static final String PLAYING_TODAY = "playing_today";
    public static final String ON_SALE_TODAY = "on_sale_today";
    public static final String JUST_ANNOUNCED = "just_announced";
    public static final String NEXT_MONTH = "next_month";
    public static final String THIS_WEEKEND = "this_weekend";
    public static final String NEXT_WEEK = "next_week";
    public static final String SHARED = "shared";
    public static final String WATCHED_TRAILER = "watched_trailer";
    public static final String POSTED_TRAILER = "posted_trailer";
    public static final String MESSAGE_RSVPS = "message_rsvp";
	public static final String INVITED_YOU = "invited_you";


    public static final String GOOGLE_MAPS_STATIC_URL_TEMPLATE = "http://maps.google.com/maps/api/staticmap?center=%s,%s&zoom=9&size=%sx%s&sensor=false";

    public static final String ACTIVITY_FEED_GROUP = "activity_feed_group";

    public static final String TYPE = "type";
    public static final String SPOTIFY = "spotify";
    public static final String SPOTIFY_URI = "spotify_uri";
    public static final String ARTIST_NAME = "artist_name";
    public static final String SOURCE = "source";
}

