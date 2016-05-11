package com.bandsintown.activityfeedsample;

import com.bandsintown.activityfeed.audio.spotify.SpotifyArtistResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rjaylward on 5/11/16 for Bandsintown
 */
public interface SpotifyApi {

    @GET("artists/{id}/top-tracks?country=US")
    Call<SpotifyArtistResponse> getActivities(@Path("id") String id, @Query("limit") Integer limit);

}