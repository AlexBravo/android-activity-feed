package com.bandsintown.activityfeed.objects;

/**
 * Created by rjaylward on 5/4/16 for Bandsintown
 */
public interface IntentRouter {

    void onHeaderClicked(FeedItemInterface feedItem);
    void onHeaderClicked(FeedGroupInterface feedGroup);

    void onObjectClicked(FeedItemInterface feedItem);
    void onPlayTrailerClicked(FeedItemInterface item);

    void onLikesTotalClick(FeedItemInterface feedItem);
    void onCommentClicked(FeedItemInterface feedItem);
    void onFlagFeedItem(int feedId);

    /**
     * @param item the item
     * @param index the index of the item in the list
     * @param subIndex the index of the click inside the item
     * @param requestCode the request code that is used to listen for changes to like status of items
     */
    //Start for result
    void onGroupClicked(FeedGroupInterface item, int index, int subIndex, int requestCode);
}