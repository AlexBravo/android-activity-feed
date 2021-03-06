package com.bandsintown.activityfeed.objects;

import android.os.Bundle;

public interface IntentRouter {

    void onHeaderClicked(FeedItemInterface feedItem);
    void onHeaderClicked(FeedGroupInterface feedGroup);

    void onObjectClicked(FeedItemInterface feedItem);
    void onPlayTrailerClicked(FeedItemInterface item);

    void onLikesTotalClick(FeedItemInterface feedItem);
    void onCommentClicked(FeedItemInterface feedItem);

    /**
     * @param feedId activity feed id
     */
    void onFlagFeedItem(int feedId);

    /**
     * @param item the item
     * @param index the index of the item in the list
     * @param subIndex the index of the click inside the item
     * @param requestCode the request code that is used to listen for changes to like status of items
     */
    //Start for result
    void onGroupClicked(FeedGroupInterface item, int index, int subIndex, int requestCode);

    /**
     * @param url the url that was clicked on
     * @return true if link has been handled, false if we want the framework to handle it.
     */
    boolean onLinkClicked(String url);

    void playPreviewFromSearch(String search, Bundle bundle);

    void pausePreview();

    void onUntrackClicked(FeedItemInterface feedItemInterface);

}