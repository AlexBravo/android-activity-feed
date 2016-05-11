package com.bandsintown.activityfeedsample.gson;

import com.bandsintown.activityfeedsample.objects.ActivityFeedItem;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ActivityFeedItemDeserializer implements JsonDeserializer<ActivityFeedItem> {

    @Override
    public ActivityFeedItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = GsonFactory.createBandsintownGsonObject(ActivityFeedItem.class);

        ActivityFeedItem item = gson.fromJson(json, ActivityFeedItem.class);

        //Set ids for everything
        item.getObject().setActivityId(item.getId());
        item.getActor().setActivityId(item.getId());

        if(item.getObject().getPost() != null) {
            //Load activity feed item id into post
            item.getObject().getPost().setActivityId(item.getId());
        }

        return item;
    }

}
