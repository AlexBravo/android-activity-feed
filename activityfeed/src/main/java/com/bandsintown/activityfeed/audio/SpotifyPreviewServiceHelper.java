package com.bandsintown.activityfeed.audio;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.bandsintown.activityfeed.ApiListener;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.audio.spotify.SpotifyArtist;
import com.bandsintown.activityfeed.audio.spotify.SpotifyArtistResponse;
import com.bandsintown.activityfeed.audio.spotify.SpotifyArtistSearchResponse;
import com.bandsintown.activityfeed.audio.spotify.SpotifyTrack;
import com.bandsintown.activityfeed.interfaces.OnCompleteListener;
import com.bandsintown.activityfeed.objects.SpotifyProvider;
import com.bandsintown.activityfeed.util.Print;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rjaylward on 5/26/16 for Bandsintown
 */
public class SpotifyPreviewServiceHelper {

    private SpotifyProvider mApiHelper;
    private Context mApplicationContext;

    MaxSizeHashMap<String, AudioTrackInfo> mNameUrlCache = new MaxSizeHashMap<>(20);
    MaxSizeHashMap<String, AudioTrackInfo> mIdUrlCache = new MaxSizeHashMap<>(20);

    private MediaNotificationManager mMediaNotificationManager;

    public SpotifyPreviewServiceHelper(MediaBrowserServiceCompat activity, SpotifyProvider api) {
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
                Print.log("Error getting artists", error.toString());
                onCompleteListener.onComplete(null);
            }
        });
    }

    private void getPreviewUrl(final String id, final OnCompleteListener<AudioTrackInfo> onCompleteListener) {
        Print.log("Spotify id", id);
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
                        Print.log("Error getting preview url, no preview url");
                        onCompleteListener.onComplete(null);
                    }
                }

                @Override
                public void onErrorResponse(Exception error) {
                    Print.log("Error getting preview url", error.toString());
                    onCompleteListener.onComplete(null);
                }

            });
        }
        else {
            Print.exception(new Exception("Spotify uri split failed"), false);
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

    private void setUpMediaSession(MediaBrowserServiceCompat activity) {
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

                mPlayback.setLoading();
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

            @Override
            public void onPlayFromMediaId(String mediaId, Bundle extras) {
                super.onPlayFromMediaId(mediaId, extras);

                mPlayback.setLoading();
                getUrlForId(mediaId, new OnCompleteListener<AudioTrackInfo>() {

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

//        activity.setSupportMediaController(new MediaControllerCompat(activity, mSession));

        try {
            mMediaNotificationManager = new MediaNotificationManager(activity.getApplicationContext(), mSession, null);
        }
        catch(Exception e) {
            Print.exception(e, false);
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

        Print.log("Updating metadata for MusicID = " + trackId);
        mSession.setMetadata(metadata);
    }

    private void updatePlaybackState(String error) {
        Print.log("updatePlaybackState, playback state=", mPlayback.getState());
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

    public Playback getPlayback() {
        return mPlayback;
    }

    public MediaSessionCompat getSession() {
        return mSession;
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
