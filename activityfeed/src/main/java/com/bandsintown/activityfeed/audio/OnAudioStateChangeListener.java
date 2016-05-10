package com.bandsintown.activityfeed.audio;

/**
 * Created by rjaylward on 5/6/16 for Bandsintown
 */
public interface OnAudioStateChangeListener {

    void onAudioStateChanged(AudioStateItem previousItem, AudioStateItem currentItem);

}
