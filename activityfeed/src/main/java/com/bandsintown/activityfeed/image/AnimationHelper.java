package com.bandsintown.activityfeed.image;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by rjaylward on 5/6/16 for Bandsintown
 */
public class AnimationHelper {

    public static void fadeIn(View view, int durationMillis) {
        if(view != null) {
            AlphaAnimation fadeImage = new AlphaAnimation(0, 1);
            fadeImage.setDuration(durationMillis);
            fadeImage.setInterpolator(new DecelerateInterpolator());
            view.startAnimation(fadeImage);
        }
    }
}
