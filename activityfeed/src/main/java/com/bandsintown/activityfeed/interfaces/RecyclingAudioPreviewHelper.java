package com.bandsintown.activityfeed.interfaces;

import com.bandsintown.activityfeed.audio.AudioStateItem;
import com.bandsintown.activityfeed.audio.AudioStateManager;
import com.bandsintown.activityfeed.audio.OnAudioStateChangeListener;

/**
 * Created by rjaylward on 10/19/16
 */

public abstract class RecyclingAudioPreviewHelper implements OnItemClickOfTypeAtIndex, OnAudioStateChangeListener {

    public static final int ITEM_CLICK = 0;
    public static final int IMAGE_CLICK = 1;

    @Override
    public void onAudioStateChanged(AudioStateItem previousItem, AudioStateItem currentItem) {
        syncPlaybackState();
    }

    public abstract void syncPlaybackState();

    public void recycle() {
        AudioStateManager.getInstance().removeListener(this);
    }
}
