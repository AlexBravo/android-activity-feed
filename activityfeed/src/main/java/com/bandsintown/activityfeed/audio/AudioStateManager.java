package com.bandsintown.activityfeed.audio;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rjaylward on 5/6/16 for Bandsintown
 */
public class AudioStateManager {

    private static AudioStateManager instance;
    private AudioStateItem mPreviousItem;
    private AudioStateItem mCurrentItem;

    private HashMap<OnAudioStateChangeListener, String> mListeners;

    private AudioStateManager() {
        mPreviousItem = null;
        mCurrentItem = null;
        mListeners = new HashMap<>();
    }

    public static AudioStateManager getInstance() {
        if(instance == null)
            instance = new AudioStateManager();

        return instance;
    }

    public static void reset() {
        instance = new AudioStateManager();
    }

    public AudioStateItem getPrevious() {
        return mPreviousItem;
    }

    public void setPrevious(AudioStateItem previousItem, boolean notifyListeners) {
        mPreviousItem = previousItem;

        if(notifyListeners)
            notifyListeners();
    }

    public AudioStateItem getCurrent() {
        return mCurrentItem;
    }

    public void setCurrent(AudioStateItem currentItem, boolean notifyListeners) {
        mPreviousItem = mCurrentItem;
        mCurrentItem = currentItem;

        if(notifyListeners)
            notifyListeners();
    }

    public void addListener(OnAudioStateChangeListener listener, String tag) {
        mListeners.put(listener, tag);
    }

    public void removeListener(OnAudioStateChangeListener listener) {
        mListeners.remove(listener);
    }

    public void removeAllListeners(@Nullable String tag) {
        if(tag != null) {
            HashMap<OnAudioStateChangeListener, String> newMap = new HashMap<>();

            for(Map.Entry<OnAudioStateChangeListener, String> entry : mListeners.entrySet()) {
                if(!entry.getValue().equals(tag))
                    newMap.put(entry.getKey(), entry.getValue());
            }

            mListeners = newMap;
        }
        else
            mListeners.clear();
    }

    public void notifyListeners() {
        for(OnAudioStateChangeListener listener : mListeners.keySet()) {
            if(listener != null)
                listener.onAudioStateChanged(mCurrentItem, mPreviousItem);
        }
    }
}
