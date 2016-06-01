package com.bandsintown.activityfeed.audio;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.NotificationCompat;

import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.util.Logger;
import com.bandsintown.kahlo.KahloOnCompleteListener;
import com.bandsintown.kahlo.image.provider.Kahlo;

/**
 * Mostly copied from google samples
 * https://github.com/googlesamples/android-MediaBrowserService/blob/master/Application/src/main/java/com/example/android/mediabrowserservice/MediaNotificationManager.java
 */
public class MediaNotificationManager extends BroadcastReceiver {
    private static final String TAG = MediaNotificationManager.class.getSimpleName();

    private static final int NOTIFICATION_ID = 800;
    private static final int REQUEST_CODE = 100;

    private static final String BASE_PACKAGE = "com.bandsintown.media";

    public static final String ACTION_PAUSE = BASE_PACKAGE + ".pause";
    public static final String ACTION_PLAY = BASE_PACKAGE + ".play";
    public static final String ACTION_PREV = BASE_PACKAGE + ".prev";
    public static final String ACTION_NEXT = BASE_PACKAGE + ".next";
    public static final String ACTION_STOP = BASE_PACKAGE + ".stop";
    public static final String ACTION_STOP_CASTING = BASE_PACKAGE + ".stop_cast";

    private final Context mContext;
    private MediaSessionCompat.Token mSessionToken;
    private MediaControllerCompat mController;
    private MediaControllerCompat.TransportControls mTransportControls;

    private PlaybackStateCompat mPlaybackState;
    private MediaMetadataCompat mMetadata;

    private final NotificationManagerCompat mNotificationManager;

    private Intent mProvidedIntent;

    private final PendingIntent mPauseIntent;
    private final PendingIntent mPlayIntent;
    private final PendingIntent mPreviousIntent;
    private final PendingIntent mNextIntent;
    private final PendingIntent mStopIntent;

    private final PendingIntent mStopCastIntent;

    private boolean mStarted = false;

    public MediaNotificationManager(Context context, MediaSessionCompat session, @Nullable Intent pendingIntent) throws RemoteException {
        mContext = context;
        mProvidedIntent = pendingIntent;

        if(session != null) {
            mSessionToken = session.getSessionToken();
            mController = session.getController();
            if(mController != null)
                mTransportControls = mController.getTransportControls();
        }

        updateSessionToken();

        mNotificationManager = NotificationManagerCompat.from(context);

        String pkg = mContext.getPackageName();
        mPauseIntent = PendingIntent.getBroadcast(mContext, REQUEST_CODE, new Intent(ACTION_PAUSE).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);
        mPlayIntent = PendingIntent.getBroadcast(mContext, REQUEST_CODE, new Intent(ACTION_PLAY).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);
        mPreviousIntent = PendingIntent.getBroadcast(mContext, REQUEST_CODE, new Intent(ACTION_PREV).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);
        mNextIntent = PendingIntent.getBroadcast(mContext, REQUEST_CODE, new Intent(ACTION_NEXT).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);
        mStopIntent = PendingIntent.getBroadcast(mContext, REQUEST_CODE, new Intent(ACTION_STOP).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);
        mStopCastIntent = PendingIntent.getBroadcast(mContext, REQUEST_CODE, new Intent(ACTION_STOP_CASTING).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT);

        // Cancel all notifications to handle the case where the Service was killed and
        // restarted by the system.
        mNotificationManager.cancelAll();
    }

    /**
     * Posts the notification and starts tracking the session to keep it
     * updated. The notification will automatically be removed if the session is
     * destroyed before {@link #stopNotification} is called.
     */
    public void startNotification() {
        if(!mStarted) {
            mMetadata = mController.getMetadata();
            mPlaybackState = mController.getPlaybackState();

            // The notification must be updated after setting started to true
            Notification notification = createNotification();
            if(notification != null) {
                mController.registerCallback(mMediaControllerCallback);
                IntentFilter filter = new IntentFilter();
                filter.addAction(ACTION_NEXT);
                filter.addAction(ACTION_PAUSE);
                filter.addAction(ACTION_PLAY);
                filter.addAction(ACTION_PREV);
                filter.addAction(ACTION_STOP);
                filter.addAction(ACTION_STOP_CASTING);
                mContext.registerReceiver(this, filter);

                mStarted = true;
                mNotificationManager.notify(NOTIFICATION_ID, notification);
//                mMediaService.startForeground(NOTIFICATION_ID, notification);
            }
        }
    }

    /**
     * Removes the notification and stops tracking the session. If the session
     * was destroyed this has no effect.
     */
    public void stopNotification() {
        if(mStarted) {
            mStarted = false;
            mController.unregisterCallback(mMediaControllerCallback);
            try {
                mNotificationManager.cancel(NOTIFICATION_ID);
                mContext.unregisterReceiver(this);
            } catch (IllegalArgumentException ex) {
                // ignore if the receiver is not registered.
            }
//            mMediaService.stopForeground(true);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Logger.log(TAG, "Received intent with action " + action);
        switch (action) {
            case ACTION_PAUSE:
                mTransportControls.pause();
                break;
            case ACTION_PLAY:
                mTransportControls.play();
                break;
            case ACTION_NEXT:
                mTransportControls.skipToNext();
                break;
            case ACTION_PREV:
                mTransportControls.skipToPrevious();
            case ACTION_STOP:
                mTransportControls.stop();
                break;
//            case ACTION_STOP_CASTING:
//                Intent i = new Intent(context, MediaPlayerService.class);
//                i.setAction(MediaPlayerService.ACTION_CMD);
//                i.putExtra(MediaPlayerService.CMD_EXTRA, MediaPlayerService.CMD_STOP_CASTING);
//                mContext.startService(i);
//                break;
            default:
                Logger.log(TAG, "Unknown intent ignored. Action=", action);
        }
    }

    /**
     * Update the state based on a change on the session token. Called either when
     * we are running for the first time or when the media session owner has destroyed the session
     * (see {@link android.media.session.MediaController.Callback#onSessionDestroyed()})
     */
    private void updateSessionToken() throws RemoteException {
        MediaSessionCompat.Token freshToken = SpotifyPreviewHelper.attemptToUpdateToken();
        if(mSessionToken == null && freshToken != null || mSessionToken != null && !mSessionToken.equals(freshToken)) {
            if(mController != null)
                mController.unregisterCallback(mMediaControllerCallback);

            mSessionToken = freshToken;
            if(mSessionToken != null) {
                mController = new MediaControllerCompat(mContext, mSessionToken);
                mTransportControls = mController.getTransportControls();

                if(mStarted)
                    mController.registerCallback(mMediaControllerCallback);
            }
        }
    }

    private PendingIntent createContentIntent(MediaDescriptionCompat description) {
        if(mProvidedIntent != null) {
//        Intent openUI = new Intent(mContext, HotMainActivity.class);

            mProvidedIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        openUI.putExtra(MusicPlayerActivity.EXTRA_START_FULLSCREEN, true);
//        if (description != null) {
//            openUI.putExtra(MusicPlayerActivity.EXTRA_CURRENT_MEDIA_DESCRIPTION, description);
//        }
            return PendingIntent.getActivity(mContext, REQUEST_CODE, mProvidedIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }
        return null;
    }

    private final MediaControllerCompat.Callback mMediaControllerCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
            mPlaybackState = state;
            Logger.log(TAG, "Received new playback state", state);
            if(state.getState() == PlaybackStateCompat.STATE_STOPPED || state.getState() == PlaybackStateCompat.STATE_NONE)
                stopNotification();
            else {
                Notification notification = createNotification();
                if(notification != null)
                    mNotificationManager.notify(NOTIFICATION_ID, notification);
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            mMetadata = metadata;
            Logger.log(TAG, "Received new metadata ", metadata);
            Notification notification = createNotification();
            if(notification != null)
                mNotificationManager.notify(NOTIFICATION_ID, notification);
        }

        @Override
        public void onSessionDestroyed() {
            super.onSessionDestroyed();
            Logger.log(TAG, "Session was destroyed, resetting to the new session token");
            try {
                updateSessionToken();
            } catch (RemoteException e) {
                Logger.log("could not connect media controller");
                Logger.exception(e);
            }
        }
    };

    private Notification createNotification() {
        Logger.log(TAG, "updateNotificationMetadata. mMetadata=" + mMetadata);
        if(mMetadata == null || mPlaybackState == null)
            return null;

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
        int playPauseButtonPosition = addNotificationActionsAndReturnPlayButtonIndex(notificationBuilder, false);

        MediaDescriptionCompat description = mMetadata.getDescription();

        String fetchArtUrl = null;
        Bitmap placeholder = null;
        if(description.getIconUri() != null) {
            // This sample assumes the iconUri will be a valid URL formatted String, but
            // it can actually be any valid Android Uri formatted String.
            // async fetch the album art icon
            fetchArtUrl = description.getIconUri().toString();
            // use a placeholder art while the remote art is being downloaded
            placeholder = BitmapFactory.decodeResource(mContext.getResources(),
                    android.R.drawable.gallery_thumb);
        }

        notificationBuilder
                .setStyle(new NotificationCompat.MediaStyle().setShowActionsInCompactView(new int[]{playPauseButtonPosition})  // show only play/pause in compact view
                .setMediaSession(mSessionToken))
                .setSmallIcon(R.drawable.bit_notif_icon)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setColor(ContextCompat.getColor(mContext, R.color.bit_teal))
                .setUsesChronometer(true)
                .setContentIntent(createContentIntent(description))
                .setContentTitle(description.getTitle())
                .setContentText(description.getSubtitle())
                .setSubText(description.getDescription())
                .setLargeIcon(placeholder)
                .setDeleteIntent(mStopIntent);

//        if (mController != null && mController.getExtras() != null) {
//            String castName = mController.getExtras().getString(MediaPlayerService.EXTRA_CONNECTED_CAST);
//            if (castName != null) {
//                String castInfo = "Casting to device " + castName;
//                notificationBuilder.setSubText(castInfo);
//                notificationBuilder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "Stop Casting", mStopCastIntent);
//            }
//        }

        setNotificationPlaybackState(notificationBuilder);
        if(fetchArtUrl != null)
            fetchBitmapFromURLAsync(fetchArtUrl, notificationBuilder);

        return notificationBuilder.build();
    }

    private int addNotificationActionsAndReturnPlayButtonIndex(NotificationCompat.Builder notificationBuilder, boolean clearOldActions) {
        int playPauseButtonPosition = 0;

        if(clearOldActions)
            notificationBuilder.mActions.clear();

        // If skip to previous action is enabled
//        if((mPlaybackState.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS) != 0) {
//            notificationBuilder.addAction(android.R.drawable.ic_media_previous, mContext.getString(R.string.previous), mPreviousIntent);
//
//            // If there is a "skip to previous" button, the play/pause button will
//            // be the second one. We need to keep track of it, because the MediaStyle notification
//            // requires to specify the index of the buttons (actions) that should be visible
//            // when in compact view.
//            playPauseButtonPosition = 1;
//        }

        Logger.log(TAG, "updatePlayPauseAction");
        String label;
        int icon;
        PendingIntent intent;
        if(mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING) {
            label = mContext.getString(R.string.pause);
            icon = R.drawable.ic_pause_white_24dp;
            intent = mPauseIntent;
        }
//        else if(mPlaybackState.getState() == PlaybackStateCompat.STATE_BUFFERING || mPlaybackState.getState() == PlaybackStateCompat.STATE_CONNECTING) {
//            label = "loading";
//            icon = R.drawable.circle_progress_white;
//            intent = mPlayIntent;
//        }
        else {
            label = mContext.getString(R.string.play);
            icon = R.drawable.ic_play_arrow_white_24dp;
            intent = mPlayIntent;
        }

        notificationBuilder.addAction(new NotificationCompat.Action(icon, label, intent));
        notificationBuilder.addAction(new NotificationCompat.Action(R.drawable.ic_stop_white_24dp, mContext.getString(R.string.stop), mStopIntent));

        // If skip to next action is enabled
        if((mPlaybackState.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_NEXT) != 0)
            notificationBuilder.addAction(android.R.drawable.ic_media_next, mContext.getString(R.string.next), mNextIntent);

        return playPauseButtonPosition;
    }

    private void setNotificationPlaybackState(NotificationCompat.Builder builder) {
        Logger.log(TAG, "updateNotificationPlaybackState. mPlaybackState=" + mPlaybackState);
        if(mPlaybackState == null || !mStarted) {
            Logger.log(TAG, "updateNotificationPlaybackState. cancelling notification!");
//            mContext.stopForeground(true);
            return;
        }
        if(mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING && mPlaybackState.getPosition() >= 0) {
            Logger.log(TAG, "updateNotificationPlaybackState. updating playback position to ",
                    (System.currentTimeMillis() - mPlaybackState.getPosition()) / 1000, " seconds");
            builder.setWhen(System.currentTimeMillis() - mPlaybackState.getPosition())
                    .setShowWhen(true)
                    .setUsesChronometer(true);
        }
        else {
            Logger.log(TAG, "updateNotificationPlaybackState. hiding playback position");
            builder.setWhen(0)
                    .setShowWhen(false)
                    .setUsesChronometer(false);
        }

        // Make sure that the notification can be dismissed by the user when we are not playing:
        builder.setOngoing(mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING);
    }

    private void fetchBitmapFromURLAsync(final String bitmapUrl, final NotificationCompat.Builder builder) {

        Kahlo.with(mContext).source(bitmapUrl).fetch(new KahloOnCompleteListener<Bitmap>() {

            @Override
            public void onComplete(Bitmap data) {
                if(data != null && mMetadata != null && mMetadata.getDescription().getIconUri() != null
                        && mMetadata.getDescription().getIconUri().toString().equals(bitmapUrl)) {
                    // If the media is still the same, update the notification:
                    Logger.log(TAG, "fetchBitmapFromURLAsync: set bitmap to ", bitmapUrl);
                    builder.setLargeIcon(data);
                    addNotificationActionsAndReturnPlayButtonIndex(builder, true);
                    mNotificationManager.notify(NOTIFICATION_ID, builder.build());
                }
            }

        });

    }
}
