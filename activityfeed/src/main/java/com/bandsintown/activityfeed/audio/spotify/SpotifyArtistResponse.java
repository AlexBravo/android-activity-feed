package com.bandsintown.activityfeed.audio.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rjaylward on 3/5/16 for Bandsintown
 */
public class SpotifyArtistResponse {

    @SerializedName("tracks")
    private ArrayList<SpotifyTrack> mTracks;

    public ArrayList<SpotifyTrack> getTracks() {
        return mTracks != null ? mTracks : new ArrayList<SpotifyTrack>();
    }
}