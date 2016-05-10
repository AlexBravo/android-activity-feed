package com.bandsintown.activityfeed.interfaces;

/**
 * Created by rjaylward on 4/15/16 for Bandsintown
 */
public interface LifecycleListener {
    void onContextCreated();
    void onContextStarted();
    void onContextResumed();
    void onContextPaused();
    void onContextStopped();
    void onContextDestroyed();
}
