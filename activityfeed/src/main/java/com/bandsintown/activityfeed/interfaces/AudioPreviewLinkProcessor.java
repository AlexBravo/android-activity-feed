package com.bandsintown.activityfeed.interfaces;

import android.support.annotation.CheckResult;

import com.bandsintown.activityfeed.objects.AudioPreviewInfo;

/**
 * Created by rjaylward on 10/19/16
 */

public interface AudioPreviewLinkProcessor {

    @CheckResult
    AudioPreviewInfo process(String text);

}
