package com.bandsintown.activityfeed.interfaces;

import android.widget.TextView;

import me.saket.bettermovementmethod.BetterLinkMovementMethod;

/**
 * Created by rjaylward on 10/19/16
 */

public interface OnLinkClickListener extends BetterLinkMovementMethod.OnLinkClickListener {

    @Override
    boolean onClick(TextView textView, String url);

}
