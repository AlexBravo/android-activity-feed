package com.bandsintown.activityfeed.interfaces;

import android.support.annotation.CheckResult;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.objects.AudioPreviewInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rjaylward on 10/19/16
 */

public class SpotifyPreviewLinkProcessor implements AudioPreviewLinkProcessor {

    private static final String SPOTIFY_GENERAL_PREFIX = "https://open.";
    private static final Pattern mPattern =
            Pattern.compile("spotify.com\\/(artist|album|track)\\/([a-zA-Z0-9]{1,30})\\w*");

    @CheckResult
    @Override
    public AudioPreviewInfo process(String text) {
        Matcher matcher = mPattern.matcher(text);

        String urlFound = null;
        String type = null;
        String spotifyId = null;

        while(matcher.find()) {
            if(matcher.group(0) != null)
                urlFound = matcher.group(0);

            if(matcher.group(1) != null)
                type = matcher.group(1);

            if(matcher.group(2) != null) {
                spotifyId = matcher.group(2);
            }
        }

        if(urlFound != null && type != null && spotifyId != null) {
            return new AudioPreviewInfo.Builder()
                    .id(spotifyId)
                    .type(type)
                    .source(FeedValues.SPOTIFY)
                    .urlInfoWasGeneratedFrom(SPOTIFY_GENERAL_PREFIX + urlFound)
                    .build();
        } else
            return null;
    }
}
