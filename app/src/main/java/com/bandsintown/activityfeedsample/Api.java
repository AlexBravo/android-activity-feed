package com.bandsintown.activityfeedsample;

import com.bandsintown.activityfeedsample.gson.GsonFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class Api {

    public static final int USER_ID = 19620369;
    private static final String BIT_API_BASE = "https://app.bandsintown.com/v2.2/";
    public static final String SPOTIFY_BASE_URL = "https://api.spotify.com/v1/";

    private static final String RJS_AUTH = "Token token=CAABwvx9igq8BAPdl6t7jEuqkMLgZBRlIWoZAnbrgj7DoeDZB3qGSiZCH" +
            "Byqyxg2FPbsWmRL69li5gwDxUJAPyFJoMTx91uFXMpIMNm9zked7vAGUDMyAajHdcQdwqTDOpugaY190foq9rqLGxumGkJO0Hp2rLl" +
            "lWztK9PdAysp4adZCzo7uYjtFhxvgjuwG0ZD, auth_method=facebook, auth_login=550118100";

    public static FeedApi create() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);

        builder.addInterceptor(new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", RJS_AUTH).build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BIT_API_BASE).client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonFactory.createBandsintownGsonObject()))
                .build();

        return retrofit.create(FeedApi.class);
    }

    public static SpotifyApi createSpotify() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPOTIFY_BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SpotifyApi.class);
    }
}
