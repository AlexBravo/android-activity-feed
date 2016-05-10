package com.bandsintown.activityfeed.interfaces;

/**
 * Created by rjaylward on 5/4/16 for Bandsintown
 */
public interface OnFeedMenuItemAdapterClickListener {

    void onDeleteClick(int feedId, int position);

    void onReportClick(int feedId, int position);

}
