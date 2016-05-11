package com.bandsintown.activityfeedsample.gson;

import com.bandsintown.activityfeedsample.FieldNames;
import com.bandsintown.activityfeedsample.objects.ActivityFeedItemObject;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

public class ActivityFeedItemObjectDeserializer implements JsonDeserializer<ActivityFeedItemObject> {

    @Override
    public ActivityFeedItemObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = GsonFactory.createBandsintownGsonObject(ActivityFeedItemObject.class);

        ActivityFeedItemObject activityFeedItemObject = gson.fromJson(json, ActivityFeedItemObject.class);

        for(Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
            if(!entry.getValue().isJsonNull()) {
                switch(entry.getKey()) {
                    case FieldNames.EVENT_ID:
                        activityFeedItemObject.setEventId(entry.getValue().getAsInt());
                        break;
                    case FieldNames.ARTIST_ID:
                        activityFeedItemObject.setArtistId(entry.getValue().getAsInt());
                        break;
                    case FieldNames.VENUE_ID:
                        activityFeedItemObject.setVenueId(entry.getValue().getAsInt());
                        break;
                    case FieldNames.USER_ID:
                        activityFeedItemObject.setUserId(entry.getValue().getAsInt());
                        break;
                    case FieldNames.LIKED_ACTIVITY_ID:
                        activityFeedItemObject.setLikedItemId(entry.getValue().getAsInt());
                        break;
                    case FieldNames.TOUR_TRAILER_MEDIA_ID:
                        activityFeedItemObject.setTourTrailerMediaId(entry.getValue().getAsInt());
                }
            }
        }

        //Get place if it exists
        if(json.getAsJsonObject().has(FieldNames.PLACE)) {
            JsonObject place = json.getAsJsonObject().getAsJsonObject(FieldNames.PLACE);
            String location = place.getAsJsonPrimitive(FieldNames.LOCATION).getAsString();
            double latitude = place.getAsJsonPrimitive(FieldNames.LATITUDE).getAsDouble();
            double longitude = place.getAsJsonPrimitive(FieldNames.LONGITUDE).getAsDouble();

            activityFeedItemObject.setLocation(location);
            activityFeedItemObject.setLatitude(latitude);
            activityFeedItemObject.setLongitude(longitude);
        }

        return activityFeedItemObject;
    }

}
