package com.bandsintown.activityfeedsample.objects;

import android.net.Uri;

import com.bandsintown.activityfeedsample.Constants;
import com.bandsintown.activityfeedsample.FieldNames;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class Tables {

    public static final String RAW = "raw";
    public static final Uri RAW_QUERY = Uri.parse("content://" + "feedTest" +
            "" + "/" + RAW);
    public static final String ORDER_KEY = "order_key";

    public static final class Artists {

        public static final String TABLE_NAME = "artists";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ARTIST_ID = FieldNames.ID;
        public static final String NAME = FieldNames.NAME;
        public static final String ON_TOUR = FieldNames.ON_TOUR; //TODO are we gonna use this?
        public static final String VERIFIED = FieldNames.VERIFIED;
        public static final String TRACKER_COUNT = FieldNames.TRACKER_COUNT;
        public static final String SCORE = FieldNames.SCORE;
        public static final String IMAGE_MEDIA_ID = FieldNames.MEDIA_ID;
        public static final String TOUR_TRAILER_MEDIA_ID = FieldNames.TOUR_TRAILER_MEDIA_ID;
        public static final String TRACKERS_LIST_EXPIRATION = "trackers_list_expiration";

        //Says when to refresh events
        public static final String EVENTS_EXP_DATE = FieldNames.EXPIRATION_DATE;

    }

    public static final class Events {

        public static final String TABLE_NAME = "events";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String EVENT_ID = FieldNames.ID;
        public static final String TITLE = FieldNames.TITLE;
        public static final String DESCRIPTION = FieldNames.DESCRIPTION;
        public static final String STARTS_AT = FieldNames.STARTS_AT;
        public static final String ENDS_AT = FieldNames.ENDS_AT;
        public static final String VENUE_ID = FieldNames.VENUE_ID;
        public static final String IMAGE_ID = FieldNames.MEDIA_ID;
        public static final String HEADLINE_ARTIST_ID = FieldNames.HEADLINE_ARTIST_ID;
        public static final String TOUR_TRAILER_ID = FieldNames.TOUR_TRAILER_MEDIA_ID;
        public static final String RSVP_COUNT = FieldNames.RSVP_COUNT;
        public static final String ON_SALE_AT = FieldNames.ON_SALE_AT;
        public static final String BUY_TICKETS_URL = FieldNames.BUY_TICKETS_URL;
        public static final String EVENT_EXPIRATION = "event_expiration";
        public static final String ATTENDEE_LIST_EXPIRATION = "attendees_list_expiration";
        public static final String EVENT_ACTIVITY_FEED_HAS_MORE = "event_activity_feed_has_more";
        public static final String EVENT_ACTIVITY_FEED_LATEST_STREAM_ID = "event_activity_feed_latest_stream_id";
        public static final String EVENT_ACTIVITY_FEED_OLDEST_STREAM_ID = "event_activity_feed_oldest_stream_id";
        public static final String EVENT_ACTIVITY_FEED_EXPIRATION = "event_activity_feed_expiration";

    }

    public static final class Venues {

        public static final String TABLE_NAME = "venues";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String VENUE_ID = FieldNames.ID;
        public static final String NAME = FieldNames.NAME;
        public static final String STREET_ADDRESS = FieldNames.STREET_ADDRESS;
        public static final String CITY = FieldNames.CITY;
        public static final String REGION = FieldNames.REGION;
        public static final String COUNTRY = FieldNames.COUNTRY;
        public static final String LATITUDE = FieldNames.LATITUDE;
        public static final String LONGITUDE = FieldNames.LONGITUDE;
        public static final String POSTAL_CODE = FieldNames.POSTAL_CODE;
        public static final String PHONE_NUMBER = FieldNames.PHONE_NUMBER;
        public static final String WEBSITE = FieldNames.WEBSITE;

    }

    public static final class Tickets {

        public static final String TABLE_NAME = "tickets";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = "_id";
        public static final String BIT_TICKET_ID = FieldNames.ID;
        public static final String URL = FieldNames.LINK;
        public static final String OFFICIAL = FieldNames.OFFICIAL;
        public static final String TICKET_TYPE = FieldNames.TICKET_TYPE;
        public static final String TICKET_SELLER_NAME = FieldNames.TICKET_SELLER_NAME;
        public static final String MIN_PRICE = FieldNames.MIN_PRICE;
        public static final String MAX_PRICE = FieldNames.MAX_PRICE;
        public static final String CURRENCY = FieldNames.CURRENCY;
        public static final String EXTERNAL_EVENT_ID = FieldNames.EXTERNAL_EVENT_ID;

        public static final String EVENT_ID = FieldNames.EVENT_ID;

    }

    public static final class Lineups {

        public static final String TABLE_NAME = "lineups";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String EVENT_ID = FieldNames.EVENT_ID;
        public static final String ARTIST_ID = FieldNames.ARTIST_ID;

    }

    public static final class SimilarArtists {

        public static final String TABLE_NAME = "similar_artists";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        //One to many ARTIST_ID -> SIMILAR_ARTIST_ID

        public static final String ARTIST_ID = FieldNames.ARTIST_ID;
        public static final String SIMILAR_ARTIST_ID = FieldNames.SIMILAR_ARTIST_ID;
        public static final String SCORE = FieldNames.SCORE;

    }

    public static final class States {

        public static final String TABLE_NAME = "states";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = FieldNames.ID;
        public static final String CODE = "code";
        public static final String NAME = "name";
        public static final String COUNTRY_CODE = "country_code";

    }

    public static final class Countries {

        public static final String TABLE_NAME = "countries";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String CODE = "code";
        public static final String NAME = "name";

    }

    public static final class Trackers {

        public static final String TABLE_NAME = "trackers";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String TRACKER_ID = FieldNames.TRACKER_ID;
        public static final String ARTIST_ID = FieldNames.ARTIST_ID;
        public static final String STATUS = FieldNames.STATUS;

    }

    public static final class Attendees {

        public static final String TABLE_NAME = "attendees";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ATTENDEE_ID = FieldNames.ATTENDEE_ID;
        public static final String EVENT_ID = FieldNames.EVENT_ID;
        public static final String STATUS = FieldNames.STATUS;

    }

    public static final class Users {

        public static final String TABLE_NAME = "users";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String USER_ID = FieldNames.ID;
        public static final String FIRST_NAME = FieldNames.FIRST_NAME;
        public static final String LAST_NAME = FieldNames.LAST_NAME;
        public static final String FACEBOOK_ID = FieldNames.FACEBOOK_UID;
        public static final String IMAGE_ID = FieldNames.MEDIA_ID;
        public static final String LOCATION = FieldNames.LOCATION;

        public static final String ACTIVITY_EXPIRATION = FieldNames.EXPIRATION_DATE;
        public static final String HAS_MORE_ACTIVITIES = Constants.HAS_MORE_ACTIVITIES;
        public static final String LATEST_ACTIVITY_ITEM_STREAM_ID = "latest_activity_item_stream_id";
        public static final String OLDEST_ACTIVITY_ITEM_STREAM_ID = "oldest_activity_item_stream_id";

    }

    public static final class Friends {

        public static final String TABLE_NAME = "friends";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String FRIEND_USER_ID = "friend_user_id";
        public static final String STATUS = "status";

    }

    public static final class ArtistStubs {

        public static final String TABLE_NAME = "artist_stubs";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ARTIST_ID = FieldNames.ID;
        public static final String NAME = FieldNames.NAME;
        public static final String IMAGE_ID = FieldNames.MEDIA_ID;
        public static final String SCORE = FieldNames.SCORE;
        public static final String ON_TOUR = FieldNames.ON_TOUR;
        public static final String SOURCE = FieldNames.SOURCE;
        public static final String TRACKER_COUNT = FieldNames.TRACKER_COUNT;

    }

    public static final class EventStubs {

        public static final String TABLE_NAME = "event_stubs";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String EVENT_ID = FieldNames.ID;
        public static final String TITLE = FieldNames.TITLE;
        public static final String STARTS_AT = FieldNames.STARTS_AT;
        public static final String IMAGE_ID = FieldNames.MEDIA_ID;
        public static final String VENUE_ID = FieldNames.VENUE_ID;
        public static final String ARTIST_ID = FieldNames.ARTIST_ID;
        public static final String RSVP_COUNT = FieldNames.RSVP_COUNT;

        public static final String ANNOUNCED_AT = FieldNames.ANNOUNCED_AT;
        public static final String BASED_ON = FieldNames.BASED_ON;

        public static final String CALENDAR_EVENT_URI = "event_calendar_uri";

    }

    public static final class VenueStubs {

        public static final String TABLE_NAME = "venue_stubs";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String VENUE_ID = FieldNames.ID;
        public static final String NAME = FieldNames.NAME;
        public static final String LOCATION = FieldNames.LOCATION;
        public static final String LATITUDE = FieldNames.LATITUDE;
        public static final String LONGITUDE = FieldNames.LONGITUDE;

    }

    public static final class ActivityFeedItems {

        public static final String TABLE_NAME = "activity_feed_items";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = FieldNames.ID;
        public static final String VERB = FieldNames.VERB;
        public static final String CATEGORY = FieldNames.CATEGORY;
        public static final String DESCRIPTION_KEY = FieldNames.DESCRIPTION_KEY;
        public static final String TIMESTAMP = FieldNames.TIMESTAMP;
        public static final String LIKE_COUNT = FieldNames.LIKE_COUNT;
        public static final String IS_LIKED_BY_USER = FieldNames.IS_LIKED;
        public static final String STREAM_ID = FieldNames.STREAM_ID;
        public static final String IS_SEEN = FieldNames.SEEN;

    }

    public static final class ActivityFeedActors {

        public static final String TABLE_NAME = "activity_feed_actors";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = FieldNames.ID; //TODO change to a hash maybe if using foreign keys
        public static final String ACTIVITY_ID = FieldNames.ACTIVITY_ID;
        public static final String ACTOR_ARTIST_ID = FieldNames.ACTOR_ARTIST_ID;
        public static final String ACTOR_USER_ID = FieldNames.ACTOR_USER_ID;

    }

    public static final class ActivityFeedObjects {

        public static final String TABLE_NAME = "activity_feed_objects";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = FieldNames.ID; //TODO change to a hash maybe if using foreign keys
        public static final String ACTIVITY_ID = FieldNames.ACTIVITY_ID;
        public static final String OBJECT_ARTIST_ID = "object_artist_id";
        public static final String OBJECT_EVENT_ID = "object_event_id";
        public static final String OBJECT_VENUE_ID = "object_venue_id";
        public static final String OBJECT_ACTIVITY_ID = "object_activity_id";
        public static final String OBJECT_USER_ID = "object_user_id";
        public static final String OBJECT_TOUR_TRAILER_MEDIA_ID = FieldNames.TOUR_TRAILER_MEDIA_ID;
        public static final String OBJECT_SPOTIFY_URI = FieldNames.SPOTIFY_URI;
        public static final String OBJECT_PLACE_LOCATION = "object_place_location";
        public static final String OBJECT_PLACE_LATITUDE = "object_place_latitude";
        public static final String OBJECT_PLACE_LONGITUDE = "object_place_longitude";

    }

    public static final class EventMap {

        public static final String TABLE_NAME = "event_map";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String EVENT_ID = FieldNames.EVENT_ID;
        public static final String LIST_NAME = FieldNames.LIST_NAME;

    }

    //Just keeping this so we can remove it in the upgrade code
//	public static final class ActivityMap {
//
//		public static final String TABLE_NAME = "activity_map";
//
//		//uri and MIME type
//		public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest
// + "/" + TABLE_NAME);
//		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;
//
//		public static final String ACTIVITY_ITEM_ID = FieldNames.ACTIVITY_ID;
//		public static final String LIST_NAME = FieldNames.LIST_NAME;
//
//	}

    public static final class RecommendedArtists {

        public static final String TABLE_NAME = "recommended_artists";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ARTIST_ID = FieldNames.ARTIST_ID;
        public static final String BASED_ON = FieldNames.BASED_ON;
        public static final String ID = FieldNames.ID;

    }

    public static final class Posts {

        public static final String TABLE_NAME = "user_posts";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = "_id";
        public static final String ACTIVITY_ID = FieldNames.ACTIVITY_ID;
        public static final String MESSAGE = FieldNames.MESSAGE;
        public static final String MEDIA_ID = FieldNames.MEDIA_ID;
        public static final String RATING = FieldNames.RATING;

    }

    //TODO encrypt the fuck out of this stuff
    public static final class PaymentMethods {

        public static final String TABLE_NAME = "payment_methods";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = "_id";
        public static final String CARD_NUMBER = "card_number";
        public static final String LAST_4_DIGITS = "last_4_digits";
        //		public static final String CVV_CODE = "cvv_code"; // shouldn't go in the database
        public static final String CARDHOLDER_NAME = "cardholder_name";
        public static final String EXPIRATION = "expiration";
        public static final String BILLING_ADDRESS_POSTAL_CODE = "billing_address_postal_code";
        public static final String IS_ENCRYPTED = "is_encrypted"; //should always be true in db
        public static final String CARD_IS_ENCRYPTED = "card_is_encrypted";
        public static final String DECRYPT_TRIES_COUNT = "decrypt_tries_count";
        public static final String CREATION_TIME = "creation_time";
        public static final String CARD_TYPE = "card_type";
    }

    public static class Purchases {

        public static final String TABLE_NAME = "purchases";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = FieldNames.ID;
        public static final String PURCHASED_AT = FieldNames.PURCHASED_AT;
        public static final String ORDER_NUMBER = FieldNames.ORDER_NUMBER;
        public static final String ORDER_TOKEN = FieldNames.ORDER_TOKEN;
        public static final String QUANTITY = FieldNames.QUANTITY;
        public static final String TOTAL_PRICE = FieldNames.TOTAL_PRICE;
        public static final String ORDER_EMAIL = FieldNames.ORDER_EMAIL;
        public static final String PAYMENT_METHOD = FieldNames.PAYMENT_METHOD;
        public static final String EVENT_ID = FieldNames.EVENT_ID;
        public static final String TICKET_ID = FieldNames.TICKET_ID;

    }

    public static class ActivityFeedGroups {

        public static final String TABLE_NAME = "activity_feed_groups";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = "_id";
        public static final String GROUP_ID = FieldNames.GROUP_ID;
        public static final String VERB = FieldNames.VERB;

    }

    public static final class ActivityMap {

        public static final String TABLE_NAME = "activity_map";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String GROUP_ID = "group_id";
        public static final String LIST_NAME = FieldNames.LIST_NAME;

    }

    public static final class ActivityFeedGroupItems {

        public static final String TABLE_NAME = "activity_feed_group_items";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + "feedTest" +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = "_id";
        public static final String GROUP_ID = "group_id";
        public static final String ACTIVITY_FEED_ITEM_ID = "activity_feed_item_id";

    }

}
