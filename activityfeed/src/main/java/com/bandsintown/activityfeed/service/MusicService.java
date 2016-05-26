package com.bandsintown.activityfeed.service;

import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.bandsintown.activityfeed.audio.Playback;
import com.bandsintown.activityfeed.audio.SpotifyPreviewServiceHelper;
import com.bandsintown.activityfeed.util.Print;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjaylward on 5/26/16 for Bandsintown
 */
public class MusicService extends MediaBrowserServiceCompat {

    // The action of the incoming Intent indicating that it contains a command
    // to be executed (see {@link #onStartCommand})
    public static final String ACTION_CMD = "com.bandsintown.activityfeed.service.mediabrowserservice.ACTION_CMD";
    // The key in the extras of the incoming Intent indicating the command that
    // should be executed (see {@link #onStartCommand})
    public static final String CMD_NAME = "CMD_NAME";
    // A value of a CMD_NAME key in the extras of the incoming Intent that
    // indicates that the music playback should be paused (see {@link #onStartCommand})
    public static final String CMD_PAUSE = "CMD_PAUSE";

    private List<MediaSessionCompat.QueueItem> mPlayingQueue;
    private boolean mServiceStarted;
    private DelayedStopHandler mDelayedStopHandler = new DelayedStopHandler(this);
    private Playback mPlayback;

    private static final int STOP_DELAY = 15000;
    private static final String MEDIA_ID_ROOT = "media_id_root";

    private SpotifyPreviewServiceHelper mServiceHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        mServiceHelper = new SpotifyPreviewServiceHelper(this, MediaBrowser);
        setSessionToken(mServiceHelper.getSession().getSessionToken());
        mPlayback = mServiceHelper.getPlayback();
    }

    /**
     * (non-Javadoc)
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent startIntent, int flags, int startId) {
        if (startIntent != null) {
            String action = startIntent.getAction();
            String command = startIntent.getStringExtra(CMD_NAME);
            if (ACTION_CMD.equals(action)) {
                if (CMD_PAUSE.equals(command)) {
                    if (mPlayback != null && mPlayback.isPlaying()) {
                        handlePauseRequest();
                    }
                }
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        Print.log("OnGetRoot: clientPackageName=" + clientPackageName,
                "; clientUid=" + clientUid + " ; rootHints=", rootHints);
//        // To ensure you are not allowing any arbitrary app to browse your app's contents, you
//        // need to check the origin:
//        if (!mPackageValidator.isCallerAllowed(this, clientPackageName, clientUid)) {
//            // If the request comes from an untrusted package, return null. No further calls will
//            // be made to other media browsing methods.
//            LogHelper.w(TAG, "OnGetRoot: IGNORING request from untrusted package "
//                    + clientPackageName);
//            return null;
//        }
//        //noinspection StatementWithEmptyBody
//        if (CarHelper.isValidCarPackage(clientPackageName)) {
//            // Optional: if your app needs to adapt ads, music library or anything else that
//            // needs to run differently when connected to the car, this is where you should handle
//            // it.
//        }
        return new BrowserRoot(MEDIA_ID_ROOT, null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(new ArrayList<MediaBrowserCompat.MediaItem>());
    }

    /**
     * Handle a request to play music
     */
    private void handlePlayRequest() {
        Print.log("MusicService", "handlePlayRequest: mState=" + mPlayback.getState());

        mDelayedStopHandler.removeCallbacksAndMessages(null);
        if(!mServiceStarted) {
            Print.log("MusicService", "Starting service");
            // The MusicService needs to keep running even after the calling MediaBrowser
            // is disconnected. Call startService(Intent) and then stopSelf(..) when we no longer
            // need to play media.
            startService(new Intent(getApplicationContext(), MusicService.class));
            mServiceStarted = true;
        }

        if(!mServiceHelper.getSession().isActive())
            mServiceHelper.getSession().setActive(true);

        //TODO... updateMetadata(), mPlayback.play()
    }

    /**
     * Handle a request to pause music
     */
    private void handlePauseRequest() {
        Print.log("MusicService", "handlePauseRequest: mState=" + mPlayback.getState());

        //TODO... mPlayback.pause()

        // reset the delayed stop handler.
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        mDelayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY);
    }

    /**
     * Handle a request to stop music
     */
    private void handleStopRequest(String withError) {
        Print.log("MusicService", "handleStopRequest: mState=" + mPlayback.getState() + " error=", withError);
        //TODO mPlayback.stop(true);
        // reset the delayed stop handler.
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        mDelayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY);

        //TODO updatePlaybackState(withError);

        // service is no longer necessary. Will be started again if needed.
        stopSelf();
        mServiceStarted = false;
    }

    /**
     * A simple handler that stops the service if playback is not active (playing)
     */
    private static class DelayedStopHandler extends Handler {
        private final WeakReference<MusicService> mWeakReference;

        private DelayedStopHandler(MusicService service) {
            mWeakReference = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            MusicService service = mWeakReference.get();
            if (service != null && service.mPlayback != null) {
                if (service.mPlayback.isPlaying()) {
                    Print.log("Music Service", "Ignoring delayed stop since the media player is in use.");
                    return;
                }
                Print.log("Music Service", "Stopping service with delay handler.");
                service.stopSelf();
                service.mServiceStarted = false;
            }
        }
    }

}
