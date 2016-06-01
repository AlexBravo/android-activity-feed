package com.bandsintown.activityfeed.image;

import android.widget.ImageView;

import com.bandsintown.activityfeed.util.Logger;
import com.bandsintown.kahlo.image.callback.BitImageCallback;

/**
 * Created by rjaylward on 5/6/16 for Bandsintown
 */
public class RoundedUserImageCallback extends BitImageCallback {

    private int mErrorResId;
    private ImageProvider mProvider;
    private String mErrorUrl;
    private int mDesiredWidth;
    private int mDesiredHeight;
    private boolean mFadeIn = true;

    public RoundedUserImageCallback(ImageProvider provider, int errorResId, String url, int desiredWidth,
                                    int desiredHeight, String errorUrl, boolean fadeIn, ImageView imageView) {
        super(url, imageView);

        mProvider = provider;
        mErrorUrl = errorUrl;
        mErrorResId = errorResId;
        mDesiredHeight = desiredHeight;
        mDesiredWidth = desiredWidth;
        mFadeIn = fadeIn;
    }

    public RoundedUserImageCallback(ImageProvider provider, int errorResId, String url, int desiredWidth, int desiredHeight, String errorUrl, ImageView imageView) {
        this(provider, errorResId, url, desiredWidth, desiredHeight, errorUrl, true, imageView);
    }


    public RoundedUserImageCallback(ImageProvider provider, int errorResId, String url, ImageView imageView) {
        super(url, imageView);

        mErrorResId = errorResId;
        mProvider = provider;
    }

    @Override
    public void onFailure(String url, ImageView iv, Exception e) {

        if(mErrorUrl != null)
            mProvider.displayPersonImage(mErrorUrl, iv, mDesiredWidth, mDesiredHeight, mErrorResId);
        else if(mErrorResId > 0)
            mProvider.systemDisplayPersonImage(mErrorResId, iv);

        Logger.log("Loading image failed", url);
    }

    @Override
    public void onSuccess(String url, ImageView iv) {
        if(mFadeIn)
            fadeInIfNecessary(url, iv);
    }

    private void fadeInIfNecessary(String imageUri, ImageView iv) {
        AnimationHelper.fadeIn(iv, 200);
    }
}