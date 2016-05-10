package com.bandsintown.activityfeed.audio.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rjaylward on 3/5/16 for Bandsintown
 */
public class SpotifyArtistSearchResponse {

    @SerializedName("artists")
    SpotifyResponse mResponse;

    public ArrayList<SpotifyArtist> getArtist() {
        if(mResponse != null)
            return mResponse.getArtists();
        else
            return new ArrayList<>();
    }

    public SpotifyArtist getExactMatch(String artistName) {
        if(mResponse != null && mResponse.getArtists() != null) {
            for(SpotifyArtist artist : mResponse.getArtists()) {
                if(artistName.toLowerCase().equalsIgnoreCase(artist.getName()))
                    return artist;
            }
            return null;
        }
        else
            return null;
    }

    class SpotifyResponse {

        @SerializedName("items")
        private ArrayList<SpotifyArtist> mArtists;

        public ArrayList<SpotifyArtist> getArtists() {
            return mArtists;
        }
    }
}