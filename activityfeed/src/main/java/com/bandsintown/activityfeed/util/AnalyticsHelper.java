package com.bandsintown.activityfeed.util;

import android.content.Context;

import com.bandsintown.activityfeed.FeedValues;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Map;

/**
 * Created by rjaylward on 5/3/16 for Bandsintown
 */
public class AnalyticsHelper {

    private static Tracker mTracker;

    public AnalyticsHelper(Context context) {
        mTracker = getTracker(context);
    }

    public static void trackPageView(String screenName) {
        mTracker.setScreenName(screenName);
        Map<String, String> map = new HitBuilders.ScreenViewBuilder().build();
        if(FeedValues.IS_DEBUG_MODE)
            Print.analyticsScreenView(screenName, map);
        else if(mTracker != null)
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public static void trackEvent(String category, String action, String label, Long value) {
        Map<String, String> map = new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .setValue(value)
                .build();

        if(mTracker != null)
            mTracker.send(map);
    }

    public static void trackButtonClick(String action) {
        trackButtonClick(action, null);
    }

    public static void trackButtonClick(String action, String label) {
        trackEvent(AnalyticsTags.BUTTON_CLICK, action, label, 0L);
    }

    public static void trackEvent(String category) {
        trackEvent(category, null, null, 0L);
    }

    public static void trackEvent(String category, String action) {
        trackEvent(category, action, null, 0L);
    }

    public static void signIn(int userId) {
        if(mTracker != null)
            mTracker.send(new HitBuilders.AppViewBuilder()
                .set("&uid", String.valueOf(userId))
                .build());
    }

    public synchronized Tracker getTracker(Context context) {
        if(mTracker == null) {
            //TODO... this does nothing
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context.getApplicationContext());
//
//            analytics.setDryRun(FeedValues.IS_DEBUG_MODE);
//
//            if(FeedValues.IS_DEBUG_MODE)
//                GoogleAnalytics.getInstance(context.getApplicationContext()).getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
//
//            mTracker = analytics.newTracker(R.xml.global_tracker);
//
//            mTracker.set("&cd1", AnalyticsTags.ANDROID_APP);
//
//            int userId = FeedPrefs.getInstance().getUserId();
//            if(userId > 0)
//                mTracker.set("&uid", String.valueOf(userId));
        }

        return mTracker;
    }

}