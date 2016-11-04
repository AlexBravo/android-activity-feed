package com.bandsintown.activityfeed.interfaces;

import com.bandsintown.activityfeed.objects.FeedItemInterface;

/**
 * Created by rjaylward on 5/4/16 for Bandsintown
 */
public interface OnFeedMenuItemAdapterClickListener {

    void onDeleteClick(int feedId, int position);

    void onReportClick(int feedId, int position);

    void onUntrackClick(FeedItemInterface feedItem, int adapterPosition);

}
