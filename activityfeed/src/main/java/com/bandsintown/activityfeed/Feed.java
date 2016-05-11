package com.bandsintown.activityfeed;

import android.content.Context;

import com.bandsintown.activityfeed.util.FeedPrefs;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by rjaylward on 5/11/16 for Bandsintown
 */
public class Feed {

    public static void initialize(Context context) {
        FeedPrefs.initialize(context.getApplicationContext());
        ImageLoaderConfiguration imageConfig = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                .memoryCache(new WeakMemoryCache())
                .build();

        ImageLoader.getInstance().init(imageConfig);
    }

}
