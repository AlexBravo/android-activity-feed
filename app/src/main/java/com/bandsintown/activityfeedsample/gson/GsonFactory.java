package com.bandsintown.activityfeedsample.gson;

import com.bandsintown.activityfeedsample.objects.ActivityFeedItem;
import com.bandsintown.activityfeedsample.objects.ActivityFeedItemActor;
import com.bandsintown.activityfeedsample.objects.ActivityFeedItemObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class GsonFactory {

    public static Gson createBandsintownGsonObject() {
        return createBandsintownGsonObject(null);
    }

    public static Gson createBandsintownGsonObject(Class classToIgnore) {
        GsonBuilder builder = new GsonBuilder();

        if(classToIgnore != ActivityFeedItem.class)
            builder.registerTypeAdapter(ActivityFeedItem.class, new ActivityFeedItemDeserializer());

        if(classToIgnore != ActivityFeedItemActor.class)
            builder.registerTypeAdapter(ActivityFeedItemActor.class, new ActivityFeedItemActorDeserializer());

        if(classToIgnore != ActivityFeedItemObject.class)
            builder.registerTypeAdapter(ActivityFeedItemObject.class, new ActivityFeedItemObjectDeserializer());

        return builder.create();
    }

}
