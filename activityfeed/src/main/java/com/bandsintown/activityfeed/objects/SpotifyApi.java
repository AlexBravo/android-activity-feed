package com.bandsintown.activityfeed.objects;

import com.bandsintown.activityfeed.ApiListener;
import com.bandsintown.activityfeed.audio.spotify.SpotifyArtistResponse;
import com.bandsintown.activityfeed.audio.spotify.SpotifyArtistSearchResponse;

/**
 * Created by rjaylward on 5/6/16 for Bandsintown
 */
public interface SpotifyApi {

    void getSpotifyArtistSearch(String artistName, ApiListener<SpotifyArtistSearchResponse> apiListener);
    void getSpotifyTrackForArtistId(String id, ApiListener<SpotifyArtistResponse> apiListener);

}
