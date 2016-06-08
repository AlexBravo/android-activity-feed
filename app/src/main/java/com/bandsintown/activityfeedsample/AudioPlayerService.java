package com.bandsintown.activityfeedsample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;

import com.bandsintown.activityfeed.audio.SpotifyPreviewHelper;

/**
 * Created by rjaylward on 5/25/16 for Bandsintown
 */
public class AudioPlayerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaSessionCompat sessionCompat = SpotifyPreviewHelper.attemptToGetSession();

        if(sessionCompat != null)
            MediaButtonReceiver.handleIntent(sessionCompat, intent);

        stopSelf();
        return START_NOT_STICKY;
    }
}
