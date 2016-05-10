package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bandsintown.activityfeed.AbsFeedItemSingleView;
import com.bandsintown.activityfeed.FeedItemSingleLikedActivity;
import com.bandsintown.activityfeed.interfaces.OnFeedMenuItemAdapterClickListener;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;

/**
 * Created by rjaylward on 5/4/16 for Bandsintown
 */
public class LikedActivityViewHolder extends AbsActivityFeedSingleViewHolder {

    private FeedItemSingleLikedActivity mFeedItemLikedActivityView;
    private ActivityViewBuilder mActivityViewBuilder;

    public LikedActivityViewHolder(AppCompatActivity context, View itemView, ActivityViewBuilder viewBuilder) {
        super(context, itemView);

        mFeedItemLikedActivityView = (FeedItemSingleLikedActivity) itemView;
        mActivityViewBuilder = viewBuilder;

        mFeedItemLikedActivityView.getFooter().setVisibility(View.GONE);
    }

    @Override
    public void buildItem(FeedItemInterface feedItem, boolean lastItem, OnLikeClickedListener<FeedItemInterface> onLikeClickListener,
                          OnFeedMenuItemAdapterClickListener feedMenuItemClickListener, IntentRouter router) {
        super.buildItem(feedItem, lastItem, onLikeClickListener, feedMenuItemClickListener, router);

        AbsFeedItemSingleView likedItem = mActivityViewBuilder.getView(feedItem.getObject().getLikedItem(), router);

        mActivityViewBuilder.buildHeader(feedItem.getObject().getLikedItem(), likedItem, router);
        mActivityViewBuilder.buildFooter(feedItem.getObject().getLikedItem(), likedItem, router);
        mActivityViewBuilder.applyMargins(likedItem);

        mFeedItemLikedActivityView.addChildToLikedContainer(likedItem);
    }
}
