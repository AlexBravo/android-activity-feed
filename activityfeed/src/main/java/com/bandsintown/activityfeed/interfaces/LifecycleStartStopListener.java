package com.bandsintown.activityfeed.interfaces;
/**
 * Created by rjaylward on 4/15/16 for Bandsintown
 */
public abstract class LifecycleStartStopListener implements LifecycleListener {

    @Override
    public void onContextCreated() {}

    @Override
    public void onContextResumed() {}

    @Override
    public void onContextPaused() {}

    @Override
    public void onContextDestroyed() {}

}
