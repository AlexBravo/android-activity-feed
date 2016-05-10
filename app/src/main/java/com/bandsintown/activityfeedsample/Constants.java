package com.bandsintown.activityfeedsample;

import android.net.Uri;

public class Constants {

    public static final boolean IS_DEBUG_MODE = true;
    public static final boolean ENABLE_WIDGET_DEBUG_VIEW = false;
    public static final boolean ENABLE_STETHO = true;
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
    public static final long TRACKED_ARTISTS_EXPIRATION_TIME = Constants.ONE_DAY_MILLIS;
    public static final long RSVPS_EXPIRATION_TIME = Constants.ONE_DAY_MILLIS;
    public static final long ACTIVITY_FEED_EXPIRATION_TIME = Constants.ONE_HOUR_MILLIS;
    public static final long PURCHASES_EXPIRATION_TIME = Constants.ONE_DAY_MILLIS;
    public static final long EVENT_DETAILS_EXPIRATION_TIME = Constants.ONE_HOUR_MILLIS * 6;

    //Distances
    public static final double KILOMETERS_PER_MILE = 1.609344;

    //Gcm project number/sender id (same thing)
    public static final String GCM_SENDER_ID = "144953192710";

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

    public static final String[] FACEBOOK_LOGIN_PERMISSIONS = new String[]{"user_likes", "user_location", "email", "user_actions.music", "user_friends"};
    public static final String FACEBOOK_FRIENDS_PERMISSION = "user_friends";

    //GOOGLE VOICE SEARCH
    public static final String GOOGLE_VOICE_SEARCH_INTENT_ACTION = "com.google.android.gms.actions.SEARCH_ACTION";

    //
    public static final int REQUEST_CODE_LOCATION_PERMISSIONS = 33;
    public static final int REQUEST_CODE_ACCOUNTS_PERMISSIONS = 34;
    public static final int REQUEST_CODE_WRITE_CAL_PERMISSIONS = 35;
    public static final int REQUEST_CODE_READ_CONTACTS_PERMISSIONS = 36;
    public static final int REQUEST_CODE_READ_CAL_PERMISSIONS = 37;

    public static final int REQUEST_CODE_VIEW_GROUP_FEED = 30;

    //Extras
    public static final String FROM_LOGIN_FLOW = "from_login_flow";
    public static final String CAME_FROM = "came_from";
    public static final String SHARED_ELEMENT_TRANSITION = "shared_element_transition";
    public static final String USER_ID = "user_id";
    public static final String CLOUD_ITEM = "cloud_item";
    public static final String CUSTOM_TRANSITION = "custom_transition";
    public static final String FROM_SPLASH = "from_splash";

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

    //BIT Share Urls
    public static final String BITLY_FALLBACK_URL = "http://bnds.in/townEF";
    public static final String BIT_SHARE_EVENT_URL = "http://www.bandsintown.com/artist_events/%s?came_from=206";
    public static final String BIT_SHARE_ARTIST_URL = "http://www.bandsintown.com/artists/%s/?came_from=206"; //TODO confirm these urls

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

    public static final String CATEGORY_ARTIST = "artist";
    public static final String CATEGORY_FRIENDS = "friends";
    public static final String CATEGORY_NONE = "none";
    public static final String CATEGORY_USER = "user";
    public static final String CATEGORY_ME = "me";

    //Notifiable Uris
    public static final Uri MY_RSVPS_NOTIFY_URI = Uri.parse("notify://my_rsvps");
    public static final Uri CLOUD_NOTIFY_URI = Uri.parse("notify://cloud");
    public static final Uri ALL_LOCAL_NOTIFY_URI = Uri.parse("notify://all_local");
    public static final Uri FRIENDS_EVENTS_NOTIFY_URI = Uri.parse("notify://friends_events");
    public static final Uri POPULAR_EVENTS_NOTIFY_URI = Uri.parse("notify://popular_events");
    public static final Uri JUST_ANNOUNCED_EVENTS_NOTIFY_URI = Uri.parse("notify://just_announced_events");
    public static final Uri RECOMMENDED_EVENTS_NOTIFY_URI = Uri.parse("notify://recommended_events");
    public static final Uri DEEPLINK_NOTIFY_URI = Uri.parse("notify://deeplink");

    public static final Uri PAST_PURCHASES_NOTIFY_URI = Uri.parse("notify://past_purchases");
    public static final Uri LIKES_CHANGED_NOTIFY_URI = Uri.parse("notify://likes_changed");

    //Request Code
    public static final int REQUEST_CHANGE_LOCATION = 1;
    public static final int REQUEST_ADJUST_DISTANCE = 2;
    public static final int REQUEST_SET_DATE_RANGE = 3;

    public static final int REQUEST_SET_FACEBOOK = 4;
    public static final int REQUEST_SET_TWITTER = 5;
    public static final int REQUEST_SET_GOOGLE_PLAY = 6;
    public static final int REQUEST_SET_LASTFM = 7;
    public static final int REQUEST_SET_PANDORA = 8;
    public static final int REQUEST_SET_SPOTIFY = 9;
    public static final int REQUEST_SET_RDIO = 10;
    public static final int REQUEST_SET_DEEZER = 11;
    public static final int PURCHASES_HEADER = 12;
    public static final int REQUEST_SET_EDIT_PROFILE = 13;
    public static final int REQUEST_SET_SOUNDCLOUD = 14;

    public static final String IN_ACTIVITY = "in_activity";

    public static final double BLAA_TOP_MARGIN_SCREEN_PERCENTAGE = 0.15;

    public static final String MANAGER_APP_PACKAGE_NAME = "com.bandsintown.manager";
    public static final String MANAGER_APP_LAUNCHER = "com.bandsintown.manager.SplashActivity";
    public static final String ANDROID_MARKET_DATA_URI = "market://details?id=";

    //Event list names
    public static final String CLOUD = "cloud";
    public static final String ALL_LOCAL = "all_local";
    public static final String FRIENDS = "friends";
    public static final	String NEW = "new";
    public static final String RECOMMENDED = "recommended";

    //Activity list names
    public static final String MY_ACTIVITY_FEED = "my_activity_feed";
    public static final String USERS_ACTIVITIES = "users_activities";
    public static final String NOTIFICATIONS = "notifications";
    public static final String EVENT_ACTIVITIES = "event_activities";

    //LastFm Request
    public static final int LASTFM_EXTRA_LARGE_IMAGE = 3;
    public static final Object LASTFM_GET_ARTIST_LIMIT = 100;
    public static final String POPULAR = "popular";

    //Twitter Request
    public static final Uri TWITTER_URI = Uri.parse("bitintent://twitter");
    public static final String TWITTER_CONSUMER_KEY = "tbU9RlgIjQr9LtdjCiocnA";
    public static final String TWITTER_CONSUMER_SECRET = "481FSdl4P3ZDHNZvnjeuHVcKo16OAK8eIGQTMbDyg";
    public static final String TWITTER_GET_USER_URL = "https://api.twitter.com/1.1/account/settings.json";
    public static final String TWITTER_SHOW_USER_INFO_URL_FORMAT = "https://api.twitter.com/1.1/users/show.json?screen_name=%s";

    //API request list limits
    public static final int ALL_CONCERTS_LIMIT = 50;
    public static final int RECOMMENDED_EVENTS_LIMIT = 50;
    public static final int FRIENDS_EVENTS_LIMIT = 50;
    public static final int POPULAR_EVENTS_LIMIT = 50;
    public static final int NEW_EVENTS_LIMIT = 50;
    public static final int CLOUD_LIMIT = 1000;
    public static final int USER_ACTIVITY_LIMIT = 50;
    public static final int EVENT_ACTIVITY_LIMIT = 25;
    public static final	int RECOMMENDED_ARTISTS_LIMIT = 50;
    public static final int TRACKED_ARTISTS_LIMIT = 5000;
    public static final int ATTENDEE_LIMIT = 50;
    public static final int ARTIST_TRACKER_LIMIT = 50;
    public static final int PAST_PURCHASES_LIMIT = 25;
    public static final int ACTIVITY_FEED_LIMIT = 50;

    //RSVP status strings
    public static final String RSVP_STATUS_ATTENDING = "attending";
    public static final String RSVP_STATUS_MAYBE = "maybe";
    public static final String RSVP_STATUS_DECLINED = "declined";

    //Music Sync Install Flow
    public static final String SYNCED_SOURCES = "synced_sources";
    public static final int FACEBOOK_MUSIC = 19;
    public static final int LASTFM = 20;
    public static final int SPOTIFY = 21;
    public static final int GOOGLE_MUSIC = 22;
    public static final int SOUNDCLOUD = 23;
    public static final int PANDORA = 24;

    //Spotify
    public static final String SPOTIFY_CLIENT_ID = "8e49c0ca16ac497eb5be4463f77ac1c8";
    public static final String SPOTIFY_REDIRECT_URI = "bitspot://auth";
    public static final String[] SPOTIFY_SCOPES = new String[] {"user-read-private", "playlist-read-private", "user-library-read", "user-follow-read"};
    public static final String SPOTIFY_ACCESS_TOKEN = "spotify_access_token";

    //Menu Index
    public static final int CLOUD_PAGE_INDEX = 0;
    public static final int CONCERTS_PAGE_INDEX = 1;
    public static final int ACTIVITY_FEED_PAGE_INDEX = 2;
    public static final int MANAGE_PAGE_INDEX = 3;
    public static final int SETTINGS_PAGE_INDEX = 4;

    //Navigation Drawer
    public static final float PERCENTAGE_OF_DISTANCE_TRAVELED_BEFORE_FADE_IN = .5f;

    //Events Activity
    public static final String CLOUD_ITEM_HEIGHT = "cloud_item_height";

    //Activity feed API tasks
    public static final int LOAD_MORE = 0;
    public static final int CLEAR_REFRESH = 1;
    public static final int UPDATE = 2;

    public static final String ACTIVITY_FEED_TASK = "activity_feed_task";

    //Ticket types
    public static final String TICKET_TYPE_TICKETS = "Tickets";

    public static final String[] FACEBOOK_WRITE_PERMISSIONS = {"publish_actions", "rsvp_event"};

    //Sharing Types
    public static final String SHARE_EVENT_TO_TWITTER = "share_event";
    public static final String SHARE_EVENT_TO_FACEBOOK = "share_event_facebook";
    public static final String SHARE_ARTIST_TO_TWITTER = "share_artist_to_twitter";
    public static final String SHARE_ARTIST_TO_FACEBOOK = "share_artist_to_facebook";
    public static final String SHARE_PLAY_MY_CITY = "share_play_my_city";

    public static final int SNACKBAR_SHORT_DURATION = 1500;

    //Api Response flags
    public static final String CLEAR_LIST = "clear_list";

    //Invite Friends Button Fade In Time
    public static final int BUTTON_FADE_IN_DURATION = 300;

    //Tracking source codes
    public static final String TRACK_SOURCE_BUY_TICKETS = "buy_tickets";
    public static final String TRACK_SOURCE_ITUNES = "itunes";
    public static final String TRACK_SOURCE_LAST_FM = "last.fm";
    public static final String TRACK_SOURCE_LIKES = "likes";
    public static final String TRACK_SOURCE_LISTENS = "listens";
    public static final String TRACK_SOURCE_MANUAL = "manual";
    public static final String TRACK_SOURCE_PANDORA = "pandora";
    public static final String TRACK_SOURCE_RSVP = "rsvp";
    public static final String TRACK_SOURCE_SPOTIFY = "spotify";
    public static final String TRACK_SOURCE_ANDROID = "android";
    public static final String TRACK_SOURCE_GOOGLE_PLAY = "google_play";
    public static final String TRACK_SOURCE_RDIO = "rdio";
    public static final String TRACK_SOURCE_EMAIL_SIGNUP = "email_signup";
    public static final String TRACK_SOURCE_ACTION_LINK = "action_link";
    public static final String TRACK_SOURCE_LIKES_BULK_IMPORT = "likes-bulk-import";
    public static final String TRACK_SOURCE_DEEZER = "deezer";

    // FAKE FIELD NAMES
    public static final String HAS_MORE_ACTIVITIES = "has_more_activities";

    public static final String BUNDLE = "bundle";

    //Notification types
    public static final String NOTIFICATION_JUST_ANNOUNCED = "just_announced";
    public static final String NOTIFICATION_RSVP = "rsvp";
    public static final String NOTIFICATION_RATE_EVENT = "event_rating";
    public static final String NOTIFICATION_PROMOTION = "promotion";
    public static final String NOTIFICATION_ARTIST_ALERT = "artist_alert";
    public static final String NOTIFICATION_EVENT_ALERT = "event_alert";
    public static final String NOTIFICATION_FRIEND_RSVP = "friend_rsvp";
    public static final String NOTIFICATION_ON_SALE = "on_sale";
    public static final String NOTIFICATION_LIKE = "like";

    public static final String RSVP_STATUS = "rsvp_status";

    public static final String NOTIFICATION_ID = "notification_id";
    public static final String NOTIFICATION_COUNT = "notification_count";
    public static final int BANDSINTOWN_NOTIFICATION_ID = 2487; //This is just for now when we clear all old notifications when a new one comes in

    //Amazon AWS
    public static final String AWS_ACCESS_KEY_ID_ENCRYPTED = "7E1BA8EC247E7BB0DC7B5A9DDD50A84A6136606F664E0F793942195474AEA067";
    public static final String AWS_SECRET_KEY_ENCRYPTED = "5A392E3B896D00D1B5E4951C687C3F45CC1F26318E1B607B30F0FC5FDC3C1A34167F5082A6E4D88CB534A235E6D9EC85";
    public static final String AWS_UPLOAD_BUCKET = "bit-artist-photos";
    public static final String AWS_UPLOAD_IMAGE_PATH = "Android/%s.jpeg";
    public static final String AWS_UPLOADED_IMAGE_URL = "https://s3.amazonaws.com/" + AWS_UPLOAD_BUCKET + "/%s";

    //Helpdesk
    public static final String HELPDESK_CONSUMER_KEY = "X824YwX8XzkaNUUOUvOy";
    public static final String HELPDESK_CONSUMER_SECRET = "yEeTN7Qd4CJJLl3cTT7W9qx9HnUlP1xYldv4humt";
    public static final String HELPDESK_CONSUMER_TOKEN = "Mbs2jDmluT8PydbRQYRe";
    public static final String HELPDESK_TOKEN_SECRET = "e0d65Ut2iskB3OopAbGsQkbT1tV8QaXwBonOmDYg";

    public static final String EVENT_ACTIVITY_FEED_LIST = "event_activity_feed_list";

    public static final String USERPHOTO = "UserPhoto";
    public static final String FANPHOTO = "FanPhoto";

    public static final int MINIMUM_ARTISTS_TO_SKIP_TRACK_SCREEN_ON_INSTALL_FLOW = 10;

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

    public static final String EDIT_PROFILE_FILTER_ACTION = "edit_profile_filter_action";
    public static final String BIT_AUTHORIZATION_VALUE_FORMAT = "Token token=%s, auth_method=%s, auth_login=%s";

    public static final String FEEDBACK_EMAIL = "support_android@bandsintown.com";

    public static final int CONNECT_FACEBOOK = 758;
    public static final int CONNECT_TWITTER = 759;

    public static final String CREDENTIALS = "credentials";
    public static final String NOTIFICATION_PAYLOAD = "notification_payload";

    public static final String DEEPLINK_EXCHANGE = "--deeplink--exchange--";
    public static final String CREDENTIALS_REQUIRED = "credentials_required";

    //Triggers
    public static final String TRIGGER_TRACK = "track";
    public static final String TRIGGER_RSVP_GOING = "rsvp_going";
    public static final String TRIGGER_RSVP_MAYBE = "rsvp_maybe";
    public static final String TRIGGER_BUY_TICKETS = "buy_tickets";
    public static final String TRIGGER_BUY_CUSTOM_TICKETS = "buy_custom";

    public static final String CUSTOM_APP_URI = "customAppUri";

    public static final String PUSH_NOTIFICATION = "push_notification";

    public static final String FLAG_EVENT_TYPE = "flag_event_type";
    public static final String FLAG_FEED_ITEM_TYPE = "flag_feed_item_type";

    //Ticket Type Strings
    public static final String TICKET_LABEL_SOLD_OUT = "Sold Out";
    public static final String TICKET_LABEL_FREE = "Free";
    public static final String TICKET_LABEL_PRESALE = "Presale";
    public static final String TICKET_LABEL_VIP = "VIP";
    public static final String TICKET_LABEL_TICKETS = "Tickets";

    public static final String FROM_ME_RESPONSE = "from_me_response";

    public static final String SOUNDCLOUD_CLIENT_ID = "9c684a506ff0167abf1e95f22f4a37e2";

    /* Other keys:
     * Sandbox no captcha: ENychSLuFlZJ6qcwpeP2W8Rc6SwoLmOp
     * Sandbox captcha: GkB8Z037ZfqbLCNtZViAgrEegbsrZ6Ne
     * Production: l17YJ2QJTttcAPLdKSgAkoqhGazpqOv9
     *
     * backwards, not a whole lot of benefit but at least keeps it from being plain text,
     * if you change it make sure you put it in backwards, or change getTMKey()
     */
    private static final String YEK_IPA_RETSAMTEKCIT = "9vOqpzaGhqokAgSKdLPActtTJQ2JY71l";
    private static final String YEK_IPA_XOBDNAS_MT = "eN6ZrsbgeErgAiVZtNCLbqfZ730Z8BkG";

    private static final String YEK_IPA = "=yekipa";

    public static final String TICKET = "ticket";

    public static final String TICKETMASTER_EVENT = "ticketmaster_event";
    public static final String TICKETMASTER_REQUEST_RESPONSE = "ticketmaster_request_response";

    public static final int SESSION_EXPIRED_CART_EXPIRED = 758;
    public static final int SESSION_EXPIRED_INACTIVITY = 987;
    public static final long TICKETMASTER_INACTIVITY_TIMEOUT = 10000L;

    //View Pagers
    public static final String PAGE_INDEX = "page_index";

    //View Automation Commands for Voice Control
    public static final String PENDING_ACTION_OPEN_TICKETS_PAGE = "pending_action_open_tickets_page";
    public static final String PENDING_ACTION_OPEN_FIRST_LOCAL_EVENT = "pending_action_open_local_event";
    public static final String PENDING_ACTION_OPEN_EVENT = "pending_action_open_event";

    //Widget
    public static final String WIDGET_UPDATE_TYPE = "widget_update_type";
    public static final String WIDGET_IDS = "widget_ids";
    public static final String WIDGET_RELOAD = "widget_reload";
    public static final String WIDGET_CONTENT = "widget_content";
    public static final String WIDGET_RSVPS = "widget_rsvps";
    public static final String WIDGET_RECOMMENDED = "widget_recommended";
    public static final String WIDGET_ALL = "widget_all";
    public static final String WIDGET_FRIENDS = "widget_friends";
    public static final String WIDGET_POPULAR = "widget_popular";
    public static final String WIDGET_NEW = "widget_new";

    //Widget Intent Constants
    public static final String PENDING_INTENT_ACTIVITY_INT = "pending_intent_class";
    public static final String PENDING_INTENT_EXTRA_INT = "pending_intent_extra_int";
    public static final int EVENT_ACTIVITY_INT = 10;
    public static final int MANAGE_ACTIVITY_INT = 11;
    public static final int CONCERTS_ACTIVITY_INT = 12;

    //Default Calendar
    public static final int DEFAULT_CALENDAR_ID= 1;

    public static final String FROM_NOTIFICATION = "from_notification";

    public static final String LATEST_APP_VERSION_UPDATED = "latest_app_version_updated";

    public static final int DEFAULT_TIMEOUT_MILLIS = 5000;

    //Music Source App Package Names
    public static final String LASTFM_PACKAGE_NAME = "fm.last.android";
    public static final String SOUNDCLOUD_PACKAGE_NAME = "com.soundcloud.android";
    public static final String SPOTIFY_PACKAGE_NAME = "com.spotify.music";
    public static final String PANDORA_PACKAGE_NAME = "com.pandora.android";

    //TM bundles
    public static final String TICKETMASTER_PACKAGE_NAME = "com.ticketmaster.mobile.android.na";
    public static final String ERROR_ON_PAYMENT = "error_on_payment";
    public static final String PAYMENT_METHOD = "payment_method";
    public static final String CART = "cart";
    public static final String SHOW_ERROR_ON_PAYMENT_DIALOG = "show_error_on_payment_dialog";
    public static final int RESULT_CODE_REVIEWING_PAYMENT = 84;

    //Card Encryption
    public static final int CARD_DECRYPT_TRIES_LIMIT = 3;

    public static String getTMKey() {
        return new StringBuilder(Constants.YEK_IPA_RETSAMTEKCIT).reverse().insert(0, new StringBuilder(YEK_IPA).reverse()).toString();
    }

    public static final String TICKETMASTER_REDEMPTION_URL_FORMAT = "https://myorder.ticketmaster.com/redeem?token=%s";
    public static final String TICKETMASTER_REDEMPTION_APP_URI_FORMAT = "ticketmaster:///redeem/partners?token=%s";

    public static final String NO_NOTIFICATION_TONE = "no_notif_tone";

    public static final int MEDIA_CONTROLS_STATE_PLAYING = 40;
    public static final int MEDIA_CONTROLS_STATE_STOPED = 41;
    public static final int MEDIA_CONTROLS_STATE_PAUSED = 42;
    public static final int MEDIA_CONTROLS_STATE_LOADING = 43;

    public static final String GOOGLE_MAPS_STATIC_URL_TEMPLATE = "http://maps.google.com/maps/api/staticmap?center=%s,%s&zoom=9&size=%sx%s&sensor=false";

    public static final String SCROLL_TO_COMMENTS = "scroll_to_comments";
}
