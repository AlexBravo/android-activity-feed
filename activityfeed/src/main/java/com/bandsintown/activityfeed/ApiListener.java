package com.bandsintown.activityfeed;

/**
 * Created by rjaylward on 5/6/16 for Bandsintown
 */
public interface ApiListener<T> {

    void onResponse(T response);
    void onErrorResponse(Exception e);

}
