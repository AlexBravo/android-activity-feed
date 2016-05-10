package com.bandsintown.activityfeed.util;

import android.content.Context;

/**
 * Created by rjaylward on 5/6/16 for Bandsintown
 */
public class FeedPrefs extends AbsPrefs {

    private static FeedPrefs instance;

    private static final String USER_ID = "user_id";
    private Object mProfileImageLocalUri;

    public static void initialize(Context mContext) {
        instance = new FeedPrefs(mContext);
    }

    public static FeedPrefs getInstance() {
        if(instance == null)
            throw new NullPointerException("instance does not exist");

        return instance;
    }

    protected FeedPrefs(Context context) {
        super(context);
    }

    public void setUserId(int id) {
        setPref(USER_ID, id);
    }

    public int getUserId() {
        return getIntPref(USER_ID);
    }

    public Object getProfileImageLocalUri() {
        return mProfileImageLocalUri;
    }
}
