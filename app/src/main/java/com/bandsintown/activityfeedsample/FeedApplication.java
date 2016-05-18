package com.bandsintown.activityfeedsample;

import android.app.Application;

import com.bandsintown.activityfeed.FeedModule;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class FeedApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FeedModule.initialize(this);
    }
}
