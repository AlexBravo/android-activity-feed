package com.bandsintown.activityfeed.image;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bandsintown.kahlo.image.callback.BitImageCallback;

/**
 * Created by rjaylward on 5/6/16 for Bandsintown
 */
public class FadeInCallback extends BitImageCallback {

    public FadeInCallback(String url, ImageView imageView) {
        super(url, imageView);
    }

    @Override
    public void onFailure(String url, ImageView iv, Exception e) {

    }

    @Override
    public void onSuccess(String url, ImageView iv) {
        if(url != null) {
//            boolean firstDisplay = !BaseActivity.displayedListImages.contains(url);
//
//            if(firstDisplay) {
                fadeIn(iv, 200);
//                BaseActivity.displayedListImages.add(url);
//            }
        }
    }

    public static void fadeIn(View view, int durationMillis) {
        if(view != null) {
            AlphaAnimation fadeImage = new AlphaAnimation(0, 1);
            fadeImage.setDuration(durationMillis);
            fadeImage.setInterpolator(new DecelerateInterpolator());
            view.startAnimation(fadeImage);
        }
    }

}
