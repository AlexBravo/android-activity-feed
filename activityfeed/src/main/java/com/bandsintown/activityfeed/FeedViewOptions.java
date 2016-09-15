package com.bandsintown.activityfeed;

/**
 * Created by rjaylward on 5/11/16 for Bandsintown
 */
public class FeedViewOptions {

    private boolean mEnableLiking;
    private boolean mEnableReporting;
    private boolean mEnableDeleting;
    private boolean mEnableCommentButton;
    private boolean mEnableUntracking;

    private FeedViewOptions(boolean enableLiking, boolean enableReporting, boolean enableDeleting,
                            boolean enableCommentButton, boolean enableUntracking) {
        mEnableLiking = enableLiking;
        mEnableReporting = enableReporting;
        mEnableDeleting = enableDeleting;
        mEnableCommentButton = enableCommentButton;
        mEnableUntracking = enableUntracking;
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

    public boolean isEnableUntracking() {
        return mEnableUntracking;
    }

    public static class Builder {
        private boolean mLikingEnabled = true;
        private boolean mReportingEnabled = true;
        private boolean mDeletingEnabled = true;
        private boolean mCommentingEnabled = true;
        private boolean mUntrackingEnabled = true;

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

        public Builder untracking(boolean isEnabled) {
            mUntrackingEnabled = isEnabled;
            return this;
        }

        public FeedViewOptions build() {
            return new FeedViewOptions(mLikingEnabled, mReportingEnabled, mDeletingEnabled, mCommentingEnabled, mUntrackingEnabled);
        }
    }

}
