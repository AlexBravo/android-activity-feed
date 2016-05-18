package com.bandsintown.activityfeed;

import android.content.Context;

import com.bandsintown.activityfeed.util.FeedPrefs;
import com.bandsintown.kahlo.image.provider.Kahlo;
import com.bandsintown.kahlo.image.provider.UILImgProvider;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by rjaylward on 5/11/16 for Bandsintown
 */
public class FeedModule {

    public static void initialize(Context context) {
        FeedPrefs.initialize(context.getApplicationContext());
        ImageLoaderConfiguration imageConfig = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                .memoryCache(new WeakMemoryCache())
                .build();

        Kahlo.initialize(new UILImgProvider(), imageConfig);
    }

}
