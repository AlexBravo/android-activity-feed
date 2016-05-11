package com.bandsintown.activityfeedsample.gson;

import com.bandsintown.activityfeed.util.Print;
import com.bandsintown.activityfeedsample.FieldNames;
import com.bandsintown.activityfeedsample.objects.ActivityFeedItemActor;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class ActivityFeedItemActorDeserializer implements JsonDeserializer<ActivityFeedItemActor> {

    @Override
    public ActivityFeedItemActor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ActivityFeedItemActor activityFeedItemActor = new ActivityFeedItemActor();

        Iterator<Map.Entry<String, JsonElement>> actorIterator = json.getAsJsonObject().entrySet().iterator();
        if(actorIterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = actorIterator.next();

            int actorId = entry.getValue().getAsInt();

            switch(entry.getKey()) {
                case FieldNames.USER_ID :
                    activityFeedItemActor.setUserId(actorId);
                    break;
                case FieldNames.ARTIST_ID :
                    activityFeedItemActor.setArtistId(actorId);
                    break;
                default:
                    Print.exception(new Exception("unhandled actor key: " + entry.getKey()));
                    break;
            }
        }

        return activityFeedItemActor;
    }

}
