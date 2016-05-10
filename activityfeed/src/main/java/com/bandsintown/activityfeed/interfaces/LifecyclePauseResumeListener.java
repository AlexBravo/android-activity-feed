package com.bandsintown.activityfeed.interfaces;

/**
 * Created by rjaylward on 4/18/16 for Bandsintown
 */
public abstract class LifecyclePauseResumeListener implements LifecycleListener {

    @Override
    public void onContextCreated() {}

    @Override
    public void onContextStarted() {}

    @Override
    public void onContextStopped() {}

    @Override
    public void onContextDestroyed() {}

}
