package com.bandsintown.activityfeed.audio.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rjaylward on 4/7/16 for Bandsintown
 */
public class SpotifyTrack {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("uri")
    private String mUri;

    @SerializedName("preview_url")
    private String mPreviewUrl;

    @SerializedName("artists")
    private ArrayList<SpotifyArtist> mArtists;

    @SerializedName("album")
    private SpotifyAlbum mAlbum;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getUri() {
        return mUri;
    }

    public String getPreviewUrl() {
        return mPreviewUrl;
    }

    public ArrayList<SpotifyArtist> getArtists() {
        return mArtists;
    }

    public SpotifyAlbum getAlbum() {
        return mAlbum;
    }

}
