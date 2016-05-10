package com.bandsintown.activityfeedsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bandsintown.activityfeed.ApiListener;
import com.bandsintown.activityfeed.BandsintownApi;
import com.bandsintown.activityfeed.FeedDatabase;
import com.bandsintown.activityfeed.audio.SpotifyPreviewHelper;
import com.bandsintown.activityfeed.audio.spotify.SpotifyArtistResponse;
import com.bandsintown.activityfeed.audio.spotify.SpotifyArtistSearchResponse;
import com.bandsintown.activityfeed.objects.FeedArtistStub;
import com.bandsintown.activityfeed.objects.FeedEventStub;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.FeedUser;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.objects.SpotifyApi;
import com.google.gson.JsonObject;
import com.trello.navi.component.support.NaviAppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class FeedActivity extends NaviAppCompatActivity {

    RecyclerView mRecyclerView;
    TestFeedAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_basic_recycler_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TestFeedAdapter(this, this, mRecyclerView, mBandsintownApi, mFeedDatabase, mIntentRouter);

        mRecyclerView.setAdapter(mAdapter);

        SpotifyPreviewHelper.initiate(this, mSpotifyApi);

        loadAdapter();
    }

    private void loadAdapter() {
        FeedApi api = new Api().create(this);
        api.getActivities(null, null).enqueue(new Callback<FeedResponse>() {

            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                if(response.isSuccessful()) {
                    mAdapter.setItems(response.body().getGroups());
                }
            }

            @Override
            public void onFailure(Call<FeedResponse> call, Throwable t) {

            }

        });
    }

    SpotifyApi mSpotifyApi = new SpotifyApi() {

        @Override
        public void getSpotifyArtistSearch(String artistName, ApiListener<SpotifyArtistSearchResponse> apiListener) {
            apiListener.onErrorResponse(new Exception("Not implemented yet"));
        }

        @Override
        public void getSpotifyTrackForArtistId(String id, ApiListener<SpotifyArtistResponse> apiListener) {
            apiListener.onErrorResponse(new Exception("Not implemented yet"));
        }
    };

    BandsintownApi mBandsintownApi = new BandsintownApi() {

        @Override
        public void deleteActivityFeedItem(int id, ApiListener apiListener) {
            apiListener.onErrorResponse(new Exception("Not implemented yet"));
        }

        @Override
        public void updatedLikeStatus(int id, boolean isLiked, ApiListener<JsonObject> apiListener) {
            apiListener.onErrorResponse(new Exception("Not implemented yet"));
        }

        @Override
        public void updateGroupLikeStatus(List<? extends FeedItemInterface> items, boolean isLiked, ApiListener<JsonObject> apiListener) {
            apiListener.onErrorResponse(new Exception("Not implemented yet"));
        }

    };

    FeedDatabase mFeedDatabase = new FeedDatabase() {

        @Override
        public void deleteActivityFeedItem(int id) {

        }

        @Override
        public void updateActivityFeedLikeStatus(FeedItemInterface feedItem, boolean isLiked) {

        }

        @Override
        public void updateActivityGroupLikeStatus(FeedGroupInterface group, boolean isLiked) {

        }

    };

    IntentRouter mIntentRouter = new IntentRouter() {

        @Override
        public void onArtistClicked(FeedArtistStub stub) {

        }

        @Override
        public void onEventClicked(FeedEventStub stub) {

        }

        @Override
        public void onPlayTrailerClicked(FeedItemInterface item) {

        }

        @Override
        public void onUserClicked(FeedUser feedUser) {

        }

        @Override
        public void onLikesTotalClick(AppCompatActivity activity, FeedItemInterface feedItem) {

        }

        @Override
        public void onHeaderClicked(AppCompatActivity activity, FeedItemInterface feedItem) {

        }

        @Override
        public void onHeaderClicked(AppCompatActivity activity, FeedGroupInterface feedGroup) {

        }

        @Override
        public void onCommentClicked(FeedItemInterface feedItem) {

        }

        @Override
        public void onObjectClicked(FeedItemInterface feedItem) {

        }

        @Override
        public void onReportClick(int feedId) {

        }

        @Override
        public void onFlagFeedItem(int feedId) {

        }

        @Override
        public void onGroupClicked(AppCompatActivity activity, FeedGroupInterface item, int index, int subIndex, int requestCode) {

        }

    };

}
