package com.bandsintown.activityfeed;

import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by rjaylward on 5/6/16 for Bandsintown
 */
public interface BandsintownApi {

    void deleteActivityFeedItem(int id, ApiListener listener);
    void updatedLikeStatus(int id, boolean isLiked, ApiListener<JsonObject> listener);
    void updateGroupLikeStatus(List<? extends FeedItemInterface> items, boolean isLiked, ApiListener<JsonObject> listener);

}
