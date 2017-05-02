package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bandsintown.activityfeed.view.FeedItemPlayMyCityView;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndexAtSubIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;

/**
 * Created by rjaylward on 5/4/16 for Bandsintown
 */
public class PlayMyCityActivityGroupViewHolder extends AbsActivityFeedGroupViewHolder {

    private FeedItemPlayMyCityView mFeedItemPlayMyCityView;

    public PlayMyCityActivityGroupViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
        super(activity, options, itemView);
        mFeedItemPlayMyCityView = (FeedItemPlayMyCityView) mView;
    }

    @Override
    public void buildItem(FeedGroupInterface group, boolean lastItem, OnLikeClickedListener<FeedGroupInterface> onLikeClickListener,
                          OnItemClickAtIndexAtSubIndex<FeedGroupInterface> itemOrViewMoreListener, IntentRouter router) {
        super.buildItem(group, lastItem, onLikeClickListener, itemOrViewMoreListener, router);

        mFeedItemPlayMyCityView.loadMap(group.getActivities().get(0).getObject().getLatitude(),
                group.getActivities().get(0).getObject().getLongitude());
    }
}
