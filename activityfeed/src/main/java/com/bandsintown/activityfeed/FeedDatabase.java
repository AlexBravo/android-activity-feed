package com.bandsintown.activityfeed;

import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.FeedItemInterface;

/**
 * Created by rjaylward on 5/9/16 for Bandsintown
 */
public interface FeedDatabase {

    void deleteActivityFeedItem(int id);

    void updateActivityFeedLikeStatus(FeedItemInterface feedItem, boolean isLiked);

    void updateActivityGroupLikeStatus(FeedGroupInterface group, boolean isLiked);
}
