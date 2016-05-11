package com.bandsintown.activityfeedsample.objects;

import android.net.Uri;

import com.bandsintown.activityfeedsample.FieldNames;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class Tables {

    public static final String DATABASE_AUTHORITY = "feed_test";

    public static final String RAW = "raw";
    public static final Uri RAW_QUERY = Uri.parse("content://" + DATABASE_AUTHORITY +
            "" + "/" + RAW);
    public static final String ORDER_KEY = "order_key";

    public static final class ActivityFeedItems {

        public static final String TABLE_NAME = "activity_feed_items";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + DATABASE_AUTHORITY +
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
        public static final Uri CONTENT_URI = Uri.parse("content://" + DATABASE_AUTHORITY +
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
        public static final Uri CONTENT_URI = Uri.parse("content://" + DATABASE_AUTHORITY +
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

    public static final class Posts {

        public static final String TABLE_NAME = "user_posts";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + DATABASE_AUTHORITY +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = "_id";
        public static final String ACTIVITY_ID = FieldNames.ACTIVITY_ID;
        public static final String MESSAGE = FieldNames.MESSAGE;
        public static final String MEDIA_ID = FieldNames.MEDIA_ID;
        public static final String RATING = FieldNames.RATING;

    }


    public static class ActivityFeedGroups {

        public static final String TABLE_NAME = "activity_feed_groups";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + DATABASE_AUTHORITY +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = "_id";
        public static final String GROUP_ID = FieldNames.GROUP_ID;
        public static final String VERB = FieldNames.VERB;

    }

    public static final class ActivityMap {

        public static final String TABLE_NAME = "activity_map";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + DATABASE_AUTHORITY +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String GROUP_ID = "group_id";
        public static final String LIST_NAME = FieldNames.LIST_NAME;

    }

    public static final class ActivityFeedGroupItems {

        public static final String TABLE_NAME = "activity_feed_group_items";

        //uri and MIME type
        public static final Uri CONTENT_URI = Uri.parse("content://" + DATABASE_AUTHORITY +
                "" + "/" + TABLE_NAME);
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.BIT." + TABLE_NAME;

        public static final String ID = "_id";
        public static final String GROUP_ID = "group_id";
        public static final String ACTIVITY_FEED_ITEM_ID = "activity_feed_item_id";

    }

}
