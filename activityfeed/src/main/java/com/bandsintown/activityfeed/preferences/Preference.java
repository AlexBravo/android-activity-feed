package com.bandsintown.activityfeed.preferences;

import android.content.Context;

/**
 * Created by rjaylward on 5/19/16 for Bandsintown
 */
public class Preference {

    private Context mContext;
    private UserPrefs mUserPrefs;

    public Preference(Context context, UserPrefs userPrefs) {
        mContext = context;
        mUserPrefs = userPrefs;
    }

    public int getUserId() {
        return mUserPrefs.getUserId(mContext);
    }

//    public String getProfileImageLocalUri() {
//        return mUserPrefs.getProfileImageLocalUri(mContext);
//    }

}
