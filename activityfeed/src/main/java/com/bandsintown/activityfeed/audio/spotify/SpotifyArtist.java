package com.bandsintown.activityfeed.audio.spotify;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rjaylward on 4/7/16 for Bandsintown
 */
public class SpotifyArtist {

    @SerializedName("name")
    private String mName;

    @SerializedName("id")
    private String mId;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

}
