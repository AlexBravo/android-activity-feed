package com.bandsintown.activityfeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.bandsintown.activityfeed.audio.AudioStateManager;
import com.bandsintown.activityfeed.audio.SpotifyPreviewHelper;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.SpotifyApi;
import com.bandsintown.activityfeed.util.FeedPrefs;
import com.bandsintown.activityfeed.util.Print;
import com.trello.navi.NaviComponent;

/**
 * Created by rjaylward on 5/9/16 for Bandsintown
 */
public class ActivityFeedManager {

    public void initalize(Context context) {
        FeedPrefs.initialize(context.getApplicationContext());
        AudioStateManager.reset();
    }

    public void onActivityCreated(AppCompatActivity activity, NaviComponent component, SpotifyApi spotifyApi) {
        SpotifyPreviewHelper.initiate(activity, spotifyApi);
        SpotifyPreviewHelper.bindMediaController(activity, spotifyApi);

//        component.addListener();
    }

    /**
     * Returns an activity feed group if something has changed, otherwise returns null
     */
    public FeedGroupInterface onActivityResultGetGroupThatChanged(int requestCode, int resultCode, Intent data) {

        Print.log("On activity result being called", requestCode, requestCode == FeedValues.REQUEST_CODE_VIEW_GROUP_FEED);

        if(requestCode == FeedValues.REQUEST_CODE_VIEW_GROUP_FEED && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                return data.getParcelableExtra(FeedValues.ACTIVITY_FEED_GROUP);
            }
        }

        return null;
    }
}
