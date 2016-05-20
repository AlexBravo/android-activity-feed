package com.bandsintown.activityfeedsample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public interface FeedApi {

    @GET("me/activities?limit=100")
    Call<FeedResponse> getActivities(@Query("before_stream_id") String beforeId,
                                     @Query("after_stream_id") String afterId);

    @GET("artists/{artistId}/activities?limit=100")
    Call<FeedResponse> getArtistActivities(
            @Path("artistId") int artistId,
            @Query("before_stream_id") String beforeId,
            @Query("after_stream_id") String afterId
    );
}
