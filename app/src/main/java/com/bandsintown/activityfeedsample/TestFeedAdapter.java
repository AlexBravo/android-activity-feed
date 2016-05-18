package com.bandsintown.activityfeedsample;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.WindowManager;

import com.bandsintown.activityfeed.BitFeedApi;
import com.bandsintown.activityfeed.FeedDatabase;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.adapters.AbsFeedAdapter;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.FeedListItem;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.objects.SizeEstimate;
import com.bandsintown.activityfeedsample.objects.ActivityFeedGroup;
import com.trello.navi.NaviComponent;

import java.util.ArrayList;

/**
 * Created by rjaylward on 5/10/16 for Bandsintown
 */
public class TestFeedAdapter extends AbsFeedAdapter {

    public TestFeedAdapter(AppCompatActivity activity, NaviComponent component, RecyclerView recyclerView,
                           BitFeedApi api, FeedDatabase database, IntentRouter router) {
        super(activity, component, recyclerView, api, database, router);
    }

    @Override
    protected FeedViewOptions getFeedViewOptions() {
        return new FeedViewOptions.Builder().build();
    }

    public void setItems(@NonNull ArrayList<ActivityFeedGroup> items) {
        mItems.clear();
        mIndexOfItemsWithMediaControls.clear();

        for(int i = 0; i < items.size(); i++) {
            FeedListItem feedListItem = new FeedListItem();
            feedListItem.setEntry(items.get(i));

            mItems.add(feedListItem);
        }

        notifyDataSetChanged();
    }

    @Override
    protected SizeEstimate getAverageFeedItemImageSizeEstimate() {
        final float fraction = mActivity.getResources().getFraction(R.fraction.sixteen_by_nine, 1, 1);
        final float horizontalMargin = mActivity.getResources().getDimension(R.dimen.activity_feed_card_horizontal_margin);

        return new SizeEstimate() {

            @Override
            protected Point estimateSize() {
                //this assumes feed fills the screen, if we change the layout we need to change this
                WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                int x = (int) (size.x - 2 * horizontalMargin);
                int y = (int) (x / fraction);

                return new Point(x, y);
            }

            @Override
            protected Point estimateTabletSize() {
                int x = (int) (mActivity.getResources().getDimension(R.dimen.feed_width) - horizontalMargin * 2);
                int y = (int) (x/fraction);

                return new Point(x, y);
            }

            @Override
            protected boolean useTabletEstimate() {
                return mActivity.getResources().getBoolean(R.bool.isTablet);
            }

        };
    }

    @Override
    protected FeedListItem convertGroupToFeedListItem(FeedGroupInterface group) {
        FeedListItem feedListItem = new FeedListItem();
        feedListItem.setEntry(group);

        return feedListItem;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

}
