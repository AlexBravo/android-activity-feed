package com.bandsintown.activityfeed;

import com.bandsintown.activityfeed.interfaces.AudioPreviewLinkProcessor;
import com.bandsintown.activityfeed.interfaces.SpotifyPreviewLinkProcessor;

/**
 * Created by rjaylward on 5/11/16 for Bandsintown
 */
public class FeedViewOptions {

    private boolean mEnableLiking;
    private boolean mEnableReporting;
    private boolean mEnableDeleting;
    private boolean mEnableCommentButton;
    private AudioPreviewLinkProcessor mAudioPreviewLinkProcessor;

    private FeedViewOptions(boolean enableLiking, boolean enableReporting, boolean enableDeleting,
                            boolean enableCommentButton, AudioPreviewLinkProcessor processor) {
        mEnableLiking = enableLiking;
        mEnableReporting = enableReporting;
        mEnableDeleting = enableDeleting;
        mEnableCommentButton = enableCommentButton;
        mAudioPreviewLinkProcessor = processor;
    }

    public boolean isEnableLiking() {
        return mEnableLiking;
    }

    public boolean isEnableReporting() {
        return mEnableReporting;
    }

    public boolean isEnableDeleting() {
        return mEnableDeleting;
    }

    public boolean isCommentingEnabled() {
        return mEnableCommentButton;
    }

    public AudioPreviewLinkProcessor getLinkProcessor() {
        return mAudioPreviewLinkProcessor;
    }

    public static class Builder {
        private boolean mLikingEnabled = true;
        private boolean mReportingEnabled = true;
        private boolean mDeletingEnabled = true;
        private boolean mCommentingEnabled = true;

        private AudioPreviewLinkProcessor mAudioPreviewLinkProcessor = new SpotifyPreviewLinkProcessor();

        public Builder liking(boolean isEnabled) {
            mLikingEnabled = isEnabled;
            return this;
        }

        public Builder reporting(boolean isEnabled) {
            mReportingEnabled = isEnabled;
            return this;
        }

        public Builder deleting(boolean isEnabled) {
            mDeletingEnabled = isEnabled;
            return this;
        }

        public Builder commenting(boolean isEnabled) {
            mCommentingEnabled = isEnabled;
            return this;
        }

        public Builder audioLinkProcessor(AudioPreviewLinkProcessor processor) {
            mAudioPreviewLinkProcessor = processor;
            return this;
        }

        public FeedViewOptions build() {
            return new FeedViewOptions(mLikingEnabled, mReportingEnabled, mDeletingEnabled,
                    mCommentingEnabled, mAudioPreviewLinkProcessor);
        }
    }

}
