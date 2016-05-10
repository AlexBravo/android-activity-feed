package com.bandsintown.activityfeed.objects;

/**
 * Created by rjaylward on 5/2/16 for Bandsintown
 */
public interface ActivityFeedEntry {

    enum Type {
        ITEM, GROUP
    }

    Type getType();

    Object getIdentifier();

    String getVerb();

    int getSize();

}
