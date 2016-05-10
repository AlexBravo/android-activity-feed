package com.bandsintown.activityfeed.audio;

/**
 * Created by rjaylward on 5/6/16 for Bandsintown
 */
public class AudioStateItem {

    public int mKnownIndex = -1;
    public int mActivityFeedItemId = -1;
    public String mActivityFeedGroupId = null;
    public int mState = -1;

    public AudioStateItem(String activityFeedGroupId, int activityFeedItemId, int knownIndex, int state) {
        mKnownIndex = knownIndex;
        mActivityFeedItemId = activityFeedItemId;
        mActivityFeedGroupId = activityFeedGroupId;
        mState = state;
    }

    public int getKnownIndex() {
        return mKnownIndex;
    }

    public void setKnownIndex(int knownIndex, boolean notifyListeners) {
        mKnownIndex = knownIndex;

        if(notifyListeners)
            AudioStateManager.getInstance().notifyListeners();
    }

    public int getActivityFeedItemId() {
        return mActivityFeedItemId;
    }

    public void setActivityFeedItemId(int activityFeedItemId, boolean notifyListeners) {
        mActivityFeedItemId = activityFeedItemId;

        if(notifyListeners)
            AudioStateManager.getInstance().notifyListeners();
    }

    public String getActivityFeedGroupId() {
        return mActivityFeedGroupId;
    }

    public void setActivityFeedGroupId(String activityFeedGroupId, boolean notifyListeners) {
        mActivityFeedGroupId = activityFeedGroupId;

        if(notifyListeners)
            AudioStateManager.getInstance().notifyListeners();
    }

    public int getState() {
        return mState;
    }

    public void setState(int state, boolean notifyListeners) {
        mState = state;

        if(notifyListeners)
            AudioStateManager.getInstance().notifyListeners();
    }

    public static class Builder {
        private int mBuilderKnownIndex = -1;
        private int mBuilderActivityFeedItemId = -1;
        private String mBuilderActivityFeedGroupId = null;
        private int mBuilderState = -1;

        public Builder knownIndex(int index) {
            mBuilderKnownIndex = index;
            return this;
        }

        public Builder feedId(int id) {
            mBuilderActivityFeedItemId = id;
            return this;
        }

        public Builder groupId(String id) {
            mBuilderActivityFeedGroupId = id;
            return this;
        }

        public Builder mediaPlayerState(int state) {
            mBuilderState = state;
            return this;
        }

        public AudioStateItem build() {
            return new AudioStateItem(mBuilderActivityFeedGroupId, mBuilderActivityFeedItemId, mBuilderKnownIndex, mBuilderState);
        }
    }

}
