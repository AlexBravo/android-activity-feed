package com.bandsintown.activityfeed.audio.spotify;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rjaylward on 4/25/16 for Bandsintown
 */
public class SpotifyAlbum {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mTitle;

    @SerializedName("uri")
    private String mUri;

    @SerializedName("images")
    private ArrayList<Image> mImages;

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUri() {
        return mUri;
    }

    public ArrayList<Image> getImages() {
        return mImages;
    }

    public class Image {

        @SerializedName("height")
        private int mHeight;

        @SerializedName("width")
        private int mWidth;

        @SerializedName("url")
        private String mUrl;

        public int getHeight() {
            return mHeight;
        }

        public int getWidth() {
            return mWidth;
        }

        public String getUrl() {
            return mUrl;
        }

    }
}
