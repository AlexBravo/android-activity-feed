package com.bandsintown.activityfeed.objects;

import android.app.Application;
import android.content.Context;

/**
 * Created by rjaylward on 5/2/16 for Bandsintown
 */
public interface VolleyContext {

    /**
     * A request filter for request cancellation
     *
     * @return the filter
     */
    String getRequestFilter();

    /**
     * Get an app context
     *
     * @return app context
     */
    Context getContext();

    /**
     * Get the application class
     *
     * @return application
     */

    //TODO figure out if we need the Bandsintown Application here
    Application getBitApplication();

}
