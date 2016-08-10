package com.bandsintown.activityfeed;

/**
 * Created by rjaylward on 5/2/16 for Bandsintown
 */
public class FeedValues {

    public static final String INSTANT_RUN_FIELD_INDICATOR = "$";

    //Time periods
    public static final long ONE_MIN_MILLIS = 1000 * 60;
    public static final long FIVE_MIN_MILLIS = ONE_MIN_MILLIS * 5;
    public static final long ONE_HOUR_MILLIS = 1000 * 60 * 60;
    public static final long ONE_DAY_MILLIS = ONE_HOUR_MILLIS * 24;
    public static final long ONE_WEEK_MILLIS = ONE_DAY_MILLIS * 7;
    public static final long FOUR_WEEKS_MILLIS = ONE_WEEK_MILLIS * 4;
    public static final long ONE_YEAR_MILLIS = ONE_DAY_MILLIS * 365;

    //Expiration times
    public static final long ACTIVITY_FEED_EXPIRATION_TIME = FeedValues.ONE_HOUR_MILLIS;

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

    //TESTING
    public static final String IS_TEST = "is_test";
    public static final String TEST_CREDENTIALS = "test_credentials";

    public static final int REQUEST_CODE_VIEW_GROUP_FEED = 30;
    public static final String USER_ID = "user_id";

    //Friend status
    public static final int FRIEND_FRIENDED = 1;
    public static final int FRIEND_ROW_DOESNT_EXIST = 2;
    public static final int FRIEND_UNFRIENDED = 3;
    public static final int FRIEND_BLOCKED = 4;

    //Artist tracking status
    public static final int ARTIST_TRACKED = 1;
    public static final int ARTIST_ROW_DOESNT_EXIST = 2;
    public static final int ARTIST_UNTRACKED = 3;
    public static final int ARTIST_BLOCKED = 4;

    //Event rsvp status
    public static final int ATTENDEE_STATUS_ATTENDING = 1;
    public static final int ATTENDEE_STATUS_MAYBE = 2;
    public static final int ATTENDEE_STATUS_DOESNT_EXIST = 3;
    public static final int ATTENDEE_STATUS_DECLINED = 4;

    public static final String TEMPLATE_NAME = "android";

    //Image URLs
    public static final String BIT_MEDIA_IMAGE_URL = "http://photos.bandsintown.com/large/%s.jpeg";
    public static final String THUMB_URL = "http://photos.bandsintown.com/thumb/%s.jpeg";
    public static final String FACEBOOK_IMAGE_URL = "https://graph.facebook.com/%s/picture?type=large";

    public static final String VIDEO_BASE_URL = "https://s3.amazonaws.com/bit-artist-videos/%s.mp4";
    public static final String VIDEO_PREVIEW_BASE_URL = "http://s3.amazonaws.com/bit-artist-videos/%s.jpeg";

    //Reflection
    public static final String TABLE_NAME = "TABLE_NAME";
    public static final String CONTENT = "CONTENT";

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

    public static final int VERB_CODE_UNRECOGNIZED = -1;
    public static final int VERB_CODE_ACTIVITY_FEED_LOADING = -99;

    public static final String IN_ACTIVITY = "in_activity";

    public static final String CATEGORY_ARTIST = "artist";
    public static final String CATEGORY_FRIENDS = "friends";
    public static final String CATEGORY_NONE = "none";
    public static final String CATEGORY_USER = "user";
    public static final String CATEGORY_ME = "me";

    //Activity list names
    public static final String MY_ACTIVITY_FEED = "my_activity_feed";
    public static final String USERS_ACTIVITIES = "users_activities";
    public static final String NOTIFICATIONS = "notifications";
    public static final String EVENT_ACTIVITIES = "event_activities";

    public static final int ACTIVITY_FEED_LIMIT = 50;

    //RSVP status strings
    public static final String RSVP_STATUS_ATTENDING = "attending";
    public static final String RSVP_STATUS_MAYBE = "maybe";
    public static final String RSVP_STATUS_DECLINED = "declined";

    //Activity feed API tasks
    public static final int LOAD_MORE = 0;
    public static final int CLEAR_REFRESH = 1;
    public static final int UPDATE = 2;

    public static final String ACTIVITY_FEED_TASK = "activity_feed_task";

    public static final int SNACKBAR_SHORT_DURATION = 1500;

    //Api Response flags
    public static final String CLEAR_LIST = "clear_list";

    //Invite Friends Button Fade In Time
    public static final int BUTTON_FADE_IN_DURATION = 300;

    // FAKE FIELD NAMES
    public static final String HAS_MORE_ACTIVITIES = "has_more_activities";

    public static final String BUNDLE = "bundle";

//	public static final int DISTANCE_SCROLLED_BEFORE_HIDE_FAB = 15;

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

    public static final int MEDIA_CONTROLS_STATE_PLAYING = 40;
    public static final int MEDIA_CONTROLS_STATE_STOPED = 41;
    public static final int MEDIA_CONTROLS_STATE_PAUSED = 42;
    public static final int MEDIA_CONTROLS_STATE_LOADING = 43;

    public static final String GOOGLE_MAPS_STATIC_URL_TEMPLATE = "http://maps.google.com/maps/api/staticmap?center=%s,%s&zoom=9&size=%sx%s&sensor=false";

    public static final String SCROLL_TO_COMMENTS = "scroll_to_comments";
    public static final String ACTIVITY_FEED_GROUP = "activity_feed_group";

    public static final String TYPE = "type";
    public static final String SPOTIFY = "spotify";
    public static final String SPOTIFY_URI = "spotify_uri";
    public static final String ARTIST_NAME = "artist_name";
    public static final String SOURCE = "source";
}

