package com.bandsintown.activityfeed;

import android.content.Context;

import com.bandsintown.activityfeed.preferences.UserPrefs;
import com.bandsintown.activityfeed.preferences.Preference;
import com.bandsintown.kahlo.image.provider.Kahlo;
import com.bandsintown.kahlo.image.provider.UILImgProvider;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by rjaylward on 5/11/16 for Bandsintown
 */
public class FeedModule {

    private static Preference mUserPrefs;

    public static void initialize(Context context, UserPrefs userPrefs) {
        ImageLoaderConfiguration imageConfig = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                .memoryCache(new WeakMemoryCache())
                .build();

        Kahlo.initialize(new UILImgProvider(), imageConfig);
        mUserPrefs = new Preference(context.getApplicationContext(), userPrefs);
    }

    public static Preference getPreferences() {
        return mUserPrefs;
    }

}
