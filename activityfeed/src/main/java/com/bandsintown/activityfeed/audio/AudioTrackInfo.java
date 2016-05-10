package com.bandsintown.activityfeed.audio;

/**
 * Created by rjaylward on 4/25/16 for Bandsintown
 */
public class AudioTrackInfo {

    private String mUrl;
    private String mArtist;
    private String mTitle;
    private String mDescription;
    private String mImageUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String track) {
        mTitle = track;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

}
