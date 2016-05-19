package com.bandsintown.activityfeedsample;

import android.app.Application;
import android.content.Context;

import com.bandsintown.activityfeed.FeedModule;
import com.bandsintown.activityfeed.preferences.UserPrefs;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class FeedApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FeedModule.initialize(this, new UserPrefs() {

            @Override
            public int getUserId(Context context) {
                return Api.USER_ID;
            }

        });
    }
}
