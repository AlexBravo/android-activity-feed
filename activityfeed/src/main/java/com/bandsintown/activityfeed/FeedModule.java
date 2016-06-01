package com.bandsintown.activityfeed;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bandsintown.activityfeed.preferences.UserPrefs;
import com.bandsintown.activityfeed.preferences.FeedPrefs;
import com.bandsintown.activityfeed.util.Logger;
import com.bandsintown.kahlo.image.provider.Kahlo;
import com.bandsintown.kahlo.image.provider.UILImgProvider;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by rjaylward on 5/11/16 for Bandsintown
 */
public class FeedModule {

    private static FeedPrefs mUserPrefs;
    public static boolean mIsDebugMode;

    public static void initialize(Context context, UserPrefs userPrefs) {
        ImageLoaderConfiguration imageConfig = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                .memoryCache(new WeakMemoryCache())
                .build();

        Kahlo.initialize(new UILImgProvider(), imageConfig);
        mUserPrefs = new FeedPrefs(context.getApplicationContext(), userPrefs);
    }

    public static void setUpLogging(boolean isDebugMode, @Nullable Logger.CrashReporter crashReporter) {
        Logger.init(isDebugMode, crashReporter);
    }

    public static FeedPrefs getPreferences() {
        return mUserPrefs;
    }

}
