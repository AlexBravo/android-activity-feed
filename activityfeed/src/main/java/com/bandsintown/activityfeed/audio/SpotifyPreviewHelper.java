package com.bandsintown.activityfeed.audio;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;

import com.bandsintown.activityfeed.ApiListener;
import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.audio.spotify.SpotifyArtist;
import com.bandsintown.activityfeed.audio.spotify.SpotifyArtistResponse;
import com.bandsintown.activityfeed.audio.spotify.SpotifyArtistSearchResponse;
import com.bandsintown.activityfeed.audio.spotify.SpotifyTrack;
import com.bandsintown.activityfeed.interfaces.OnCompleteListener;
import com.bandsintown.activityfeed.objects.SpotifyProvider;
import com.bandsintown.activityfeed.util.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rjaylward on 3/5/16 for Bandsintown
 *
 * This class would probably be better as a service but for playing simple previews on one
 * screen it is not worth the added layer of complexity. If we ever add more spotify integration
 * this should be switched to a service and we should utilize media notifications.
 */
public class SpotifyPreviewHelper {

    private SpotifyProvider mApiHelper;
    private Context mApplicationContext;

    MaxSizeHashMap<String, AudioTrackInfo> mNameUrlCache = new MaxSizeHashMap<>(20);
    MaxSizeHashMap<String, AudioTrackInfo> mIdUrlCache = new MaxSizeHashMap<>(20);

    private MediaNotificationManager mMediaNotificationManager;

    private static SpotifyPreviewHelper instance;

    public static SpotifyPreviewHelper initiate(AppCompatActivity activity, SpotifyProvider spotifyProvider) {
        instance = new SpotifyPreviewHelper(activity, spotifyProvider);

        return instance;
    }

    public static SpotifyPreviewHelper getInstance() {
        if(instance == null)
            throw new NullPointerException("must call initiate on spotify preview helper");

        return instance;
    }

    public static MediaSessionCompat.Token attemptToUpdateToken() {
        if(instance != null && instance.mSession != null)
            return instance.mSession.getSessionToken();
        else
            return null;
    }

    public static MediaSessionCompat attemptToGetSession() {
        if(instance != null)
            return instance.mSession;
        else
            return null;
    }

    public static void bindMediaController(AppCompatActivity activity, SpotifyProvider api) {
        if(instance == null)
            instance = new SpotifyPreviewHelper(activity, api);

        activity.setSupportMediaController(new MediaControllerCompat(activity, instance.mSession));
    }

    public static void unbindMediaController(AppCompatActivity activity) {
        try {
            if(activity.getSupportMediaController() != null)
                activity.setSupportMediaController(null);
        }
        catch(Exception e) {
            Logger.log("Unable to null out the media controller");
        }
    }

    public static void releaseMediaSession() {
        if(instance != null && instance.mSession != null)
            instance.mSession.release();
        else
            Logger.log("failed to release the media session");
    }

    private SpotifyPreviewHelper(AppCompatActivity activity, SpotifyProvider api) {
        mApplicationContext = activity.getApplicationContext();
        //creates a new volley context instead of holding reference to the Activity to prevent leaks
        mApiHelper = api;

        setUpMediaSession(activity);
    }

    public void startPreview(String artistName) {
        getUrlForArtistName(artistName, mPlayOnCompleteListener);
    }

    public void startPreviewFromUri(String spotifyUri) {
        getUrlForId(spotifyUri, mPlayOnCompleteListener);
    }

    private void getUrlForId(String spotifyUri, OnCompleteListener<AudioTrackInfo> listener) {
        String id = uriToId(spotifyUri);

        if(mIdUrlCache.containsKey(id))
            mPlayOnCompleteListener.onComplete(mIdUrlCache.get(id));
        else
            getPreviewUrl(uriToId(id), listener);
    }

    private void getUrlForArtistName(String artistName, OnCompleteListener<AudioTrackInfo> listener) {
        if(mNameUrlCache.containsKey(artistName.toLowerCase()))
            mPlayOnCompleteListener.onComplete((mNameUrlCache.get(artistName)));
        else
            getArtists(artistName, listener);
    }

    private OnCompleteListener<AudioTrackInfo> mPlayOnCompleteListener = new OnCompleteListener<AudioTrackInfo>() {

        @Override
        public void onComplete(AudioTrackInfo response) {
            if(response != null) {
                updateMetadata(response);
                mPlayback.play(response.getUrl());
            }
        }

    };

    private void getArtists(final String artistName, final OnCompleteListener<AudioTrackInfo> onCompleteListener) {
        mApiHelper.getSpotifyArtistSearch(artistName, new ApiListener<SpotifyArtistSearchResponse>() {

            @Override
            public void onResponse(SpotifyArtistSearchResponse response) {
                try {
                    getPreviewUrl(response.getExactMatch(artistName).getId(), onCompleteListener);
                }
                catch(Exception e) {
                    onCompleteListener.onComplete(null);
                }
            }

            @Override
            public void onErrorResponse(Exception error) {
                Logger.log("Error getting artists", error.toString());
                onCompleteListener.onComplete(null);
            }
        });
    }

    private void getPreviewUrl(final String id, final OnCompleteListener<AudioTrackInfo> onCompleteListener) {
        Logger.log("Spotify id", id);
        if(id != null) {
            mApiHelper.getSpotifyTrackForArtistId(id, new ApiListener<SpotifyArtistResponse>() {

                @Override
                public void onResponse(SpotifyArtistResponse response) {
                    if(response.getTracks().size() > 0) {
                        SpotifyTrack track = response.getTracks().get(0);

                        AudioTrackInfo audioItem = new AudioTrackInfo();

                        for(SpotifyArtist artist : track.getArtists()) {
                            if(artist.getId().equals(id))
                                audioItem.setArtist(artist.getName());
                        }

                        if(track.getAlbum() != null && track.getAlbum().getImages() != null && track.getAlbum().getImages().get(0) != null)
                            audioItem.setImageUrl(track.getAlbum().getImages().get(0).getUrl());

                        audioItem.setUrl(response.getTracks().get(0).getPreviewUrl());
                        audioItem.setTitle(track.getName());
                        audioItem.setDescription(mApplicationContext.getString(R.string.preview_provided_by_spotify));

                        mIdUrlCache.put(id, audioItem);
                        onCompleteListener.onComplete(audioItem);
                    }
                    else {
                        Logger.log("Error getting preview url, no preview url");
                        onCompleteListener.onComplete(null);
                    }
                }

                @Override
                public void onErrorResponse(Exception error) {
                    Logger.log("Error getting preview url", error.toString());
                    onCompleteListener.onComplete(null);
                }

            });
        }
        else {
            Logger.exception(new Exception("Spotify uri split failed"), false);
            onCompleteListener.onComplete(null);
        }
    }

    public static String uriToId(String uri) {
        if(uri != null && uri.contains(":")) {
            try {
                return uri.split(":")[2];
            }
            catch(Exception e) {
                return null;
            }
        }
        else
            return uri;
    }

    private MediaSessionCompat mSession;
    private Playback mPlayback;

    private void setUpMediaSession(AppCompatActivity activity) {
        mSession = new MediaSessionCompat(activity.getApplicationContext(), SpotifyPreviewHelper.class.getCanonicalName());
        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mPlayback = new Playback(activity.getApplicationContext());
        mPlayback.setCallback(new Playback.Callback() {

            @Override
            public void onCompletion() {
                if(mPlayback != null)
                    mPlayback.stop(true);
            }

            @Override
            public void onPlaybackStatusChanged(int state) {
                updatePlaybackState(null);
            }

            @Override
            public void onError(String error) {
                if(mPlayback != null)
                    mPlayback.stop(true);
            }

        });
        mSession.setCallback(new MediaSessionCompat.Callback() {

            @Override
            public void onPlay() {
                super.onPlay();

                if(mPlayback != null)
                    mPlayback.play(null);
            }

            @Override
            public void onPlayFromSearch(String query, Bundle extras) {
                super.onPlayFromSearch(query, extras);

                Logger.log("SpotifyPreviewHelper", "onPlayFromSearch", query, extras);

                if(FeedValues.SPOTIFY.equals(extras.getString(FeedValues.TYPE))) {
                    mPlayback.setLoading();
                    if(FeedValues.SPOTIFY_URI.equals(extras.getString(FeedValues.TYPE))) {
                        getUrlForId(query, new OnCompleteListener<AudioTrackInfo>() {

                            @Override
                            public void onComplete(AudioTrackInfo response) {
                                if(response != null) {
                                    mPlayback.play(response.getUrl());
                                    updateMetadata(response);
                                }
                                else
                                    mPlayback.stop(true);
                            }

                        });
                    }
                    else {
                        getUrlForArtistName(query, new OnCompleteListener<AudioTrackInfo>() {

                            @Override
                            public void onComplete(AudioTrackInfo response) {
                                if(mPlayback != null) {
                                    if(response != null) {
                                        mPlayback.play(response.getUrl());
                                        updateMetadata(response);
                                    }
                                    else
                                        mPlayback.stop(true);
                                }
                            }

                        });
                    }
                }
            }

            @Override
            public void onPlayFromMediaId(String mediaId, Bundle extras) {
                super.onPlayFromMediaId(mediaId, extras);

            }

            @Override
            public void onStop() {
                super.onStop();
                mPlayback.stop(true);
            }

            @Override
            public void onPause() {
                super.onPause();
                mPlayback.pause();
            }
        });

        PlaybackStateCompat stateCompat = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PAUSE )
                .setState(PlaybackStateCompat.STATE_STOPPED, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1)
                .build();

        mSession.setPlaybackState(stateCompat);

        activity.setSupportMediaController(new MediaControllerCompat(activity, mSession));

        try {
            mMediaNotificationManager = new MediaNotificationManager(activity.getApplicationContext(), mSession, null);
        }
        catch(Exception e) {
            Logger.exception(e, false);
        }
    }

    private void updateMetadata(AudioTrackInfo audioInfo) {

        MediaMetadataCompat metadata = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, audioInfo.getTitle())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, audioInfo.getArtist())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, audioInfo.getDescription())
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, audioInfo.getUrl())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, audioInfo.getImageUrl())
                .build();

        String trackId = metadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID);

        Logger.log("Updating metadata for MusicID = " + trackId);
        mSession.setMetadata(metadata);
    }

    private void updatePlaybackState(String error) {
        Logger.log("updatePlaybackState, playback state=", mPlayback.getState());
        long position = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN;
        if(mPlayback != null && mPlayback.isConnected()) {
            position = mPlayback.getCurrentStreamPosition();
        }

        @SuppressWarnings("WrongConstant")
        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder().setActions(getAvailableActions());

        int state = mPlayback.getState();

        // If there is an error message, send it to the playback state:
        if(error != null) {
            // Error states are really only supposed to be used for errors that cause playback to
            // stop unexpectedly and persist until the user takes action to fix it.
            stateBuilder.setErrorMessage(error);
            state = PlaybackStateCompat.STATE_ERROR;
        }
        //noinspection WrongConstant
        stateBuilder.setState(state, position, 1.0f, SystemClock.elapsedRealtime());

        mSession.setPlaybackState(stateBuilder.build());

        if(mMediaNotificationManager != null && (state == PlaybackStateCompat.STATE_PLAYING || state == PlaybackStateCompat.STATE_PAUSED ||
                state == PlaybackStateCompat.STATE_BUFFERING || state == PlaybackStateCompat.STATE_CONNECTING))
            mMediaNotificationManager.startNotification();
    }

    private long getAvailableActions() {
        long actions = PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID |
                PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;

        if (mPlayback.isPlaying()) {
            actions |= PlaybackStateCompat.ACTION_PAUSE;
        }

        return actions;
    }

    public int getState() {
        return mPlayback.getState();
    }

    public class MaxSizeHashMap<K, V> extends LinkedHashMap<K, V> {
        private final int maxSize;

        public MaxSizeHashMap(int maxSize) {
            this.maxSize = maxSize;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > maxSize;
        }
    }

}