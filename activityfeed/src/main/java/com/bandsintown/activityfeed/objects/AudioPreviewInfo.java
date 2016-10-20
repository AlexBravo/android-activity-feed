package com.bandsintown.activityfeed.objects;

/**
 * Created by rjaylward on 10/19/16
 */

public class AudioPreviewInfo {

    private String mId;
    private String mType;
    private String mSource;
    private String mUrlInfoWasGeneratedFrom;

    private AudioPreviewInfo(Builder builder) {
        mId = builder.mId;
        mType = builder.mType;
        mSource = builder.mSource;
        mUrlInfoWasGeneratedFrom = builder.mUrlInfoWasGeneratedFrom;
    }

    public String getId() {
        return mId;
    }

    public String getType() {
        return mType;
    }

    public String getSource() {
        return mSource;
    }

    public String getUrlInfoWasGeneratedFrom() {
        return mUrlInfoWasGeneratedFrom;
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
