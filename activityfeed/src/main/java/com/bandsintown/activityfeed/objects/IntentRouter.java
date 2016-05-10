package com.bandsintown.activityfeed.objects;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by rjaylward on 5/4/16 for Bandsintown
 */
public interface IntentRouter {

    void onArtistClicked(FeedArtistStub stub);
    void onEventClicked(FeedEventStub stub);
    void onPlayTrailerClicked(FeedItemInterface item);
    void onUserClicked(FeedUser feedUser);

    void onLikesTotalClick(AppCompatActivity activity, FeedItemInterface feedItem);

    void onHeaderClicked(AppCompatActivity activity, FeedItemInterface feedItem);
    void onHeaderClicked(AppCompatActivity activity, FeedGroupInterface feedGroup);

    void onCommentClicked(FeedItemInterface feedItem);

    void onObjectClicked(FeedItemInterface feedItem);

    void onReportClick(int feedId);

    void onFlagFeedItem(int feedId);

    //Start for result
    void onGroupClicked(AppCompatActivity activity, FeedGroupInterface item, int index, int subIndex, int requestCode);
}