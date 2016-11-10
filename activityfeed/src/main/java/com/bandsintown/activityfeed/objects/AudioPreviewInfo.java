package com.bandsintown.activityfeed.objects;

/**
 * Created by rjaylward on 10/19/16
 */

public class AudioPreviewInfo {

    private static final String DIVIDER = ":";

    private String mId;
    private String mType;
    private String mSource;
    private String mUrlInfoWasGeneratedFrom;

    private String mMediaUri;

    public AudioPreviewInfo(String mediaUri) {
        mMediaUri = mediaUri;
    }

    private AudioPreviewInfo(Builder builder) {
        mId = builder.mId;
        mType = builder.mType;
        mSource = builder.mSource;
        mUrlInfoWasGeneratedFrom = builder.mUrlInfoWasGeneratedFrom;
    }

    public String getId() {
        if(mId == null)
            splitMediaUri();

        return mId;
    }

    public String getType() {
        if(mType == null)
            splitMediaUri();

        return mType;
    }

    public String getSource() {
        if(mSource == null)
            splitMediaUri();

        return mSource;
    }

    public String getUrlInfoWasGeneratedFrom() {
        return mUrlInfoWasGeneratedFrom;
    }

    public String toMediaUri() {
        if(mMediaUri == null && mSource != null && mType != null && mId != null)
            mMediaUri = mSource + DIVIDER + mType + DIVIDER + mId;

        return mMediaUri;
    }

    private void splitMediaUri() {
        if(mMediaUri != null) {
            String[] split = mMediaUri.split(":");
            if(split.length == 3) {
                mSource = split[0];
                mType = split[1];
                mId = split[2];
            }
        }

    }

    public static final class Builder {
        private String mId;
        private String mType;
        private String mSource;
        private String mUrlInfoWasGeneratedFrom;

        public Builder() {
        }

        public Builder id(String spotifyId) {
            mId = spotifyId;
            return this;
        }

        public Builder type(String type) {
            mType = type;
            return this;
        }

        public Builder source(String source) {
            mSource = source;
            return this;
        }

        public Builder urlInfoWasGeneratedFrom(String originalUrl) {
            mUrlInfoWasGeneratedFrom = originalUrl;
            return this;
        }

        public AudioPreviewInfo build() {
            return new AudioPreviewInfo(this);
        }
    }
}
