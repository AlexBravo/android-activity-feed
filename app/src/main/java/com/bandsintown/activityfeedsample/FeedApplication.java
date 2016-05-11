package com.bandsintown.activityfeedsample;

import android.app.Application;

import com.bandsintown.activityfeed.util.FeedPrefs;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class FeedApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FeedPrefs.initialize(this);
        ImageLoaderConfiguration imageConfig = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new WeakMemoryCache())
                .build();

        ImageLoader.getInstance().init(imageConfig);
    }
}
