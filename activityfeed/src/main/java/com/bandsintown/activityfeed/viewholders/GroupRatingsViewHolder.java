package com.bandsintown.activityfeed.viewholders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;

import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.GroupFeedItemMiniList;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndexAtSubIndex;
import com.bandsintown.activityfeed.interfaces.OnItemClickOfTypeAtIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.util.FeedUtil;
import com.bandsintown.activityfeed.util.Print;

import java.util.ArrayList;

/**
 * Created by rjaylward on 4/8/16 for Bandsintown
 */
public class GroupRatingsViewHolder extends AbsActivityFeedGroupViewHolder implements OnItemClickOfTypeAtIndex {

    private GroupFeedItemMiniList mView;
    private ArrayList<? extends FeedItemInterface> mFeedItems;
    private FeedGroupInterface mGroup;
    private OnItemClickAtIndexAtSubIndex<FeedGroupInterface> mOnItemClickAtIndex;

    private static final int ITEM_CLICK = 0;
    private static final int IMAGE_CLICK = 1;

    public GroupRatingsViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
        super(activity, options, itemView);

        mView = (GroupFeedItemMiniList) itemView;
    }

    @Override
    public void buildItem(final FeedGroupInterface feedGroup, boolean lastItem, OnLikeClickedListener<FeedGroupInterface> onLikeClickListener,
                          OnItemClickAtIndexAtSubIndex<FeedGroupInterface> itemOrViewMoreListener, IntentRouter router) {
        super.buildItem(feedGroup, lastItem, onLikeClickListener, itemOrViewMoreListener, router);
        mOnItemClickAtIndex = itemOrViewMoreListener;
        mGroup = feedGroup;
        mFeedItems = feedGroup.getActivities();
        mView.setViewModel(new GroupFeedItemMiniList.ViewModel() {

            @Override
            public String getTitle(int index) {
                try {
                    Print.log("Index", index, mFeedItems.get(index).getObject().getObjectTitle(mView.getContext()));
                    return mFeedItems.get(index).getObject().getObjectTitle(mView.getContext());
                }
                catch(NullPointerException e) {
                    Print.exception(e);
                    return "";
                }
            }

            @Override
            public String getSubtitle(int index) {
                try {
                    return FeedUtil.getStars(mView.getContext(), mFeedItems.get(index));
                }
                catch(NullPointerException e) {
                    Print.exception(e);
                    return "";
                }
            }

            @Override
            public Pair<String, Integer> getImageUrlErrorResIdPair(int index) {
                String url = null;
                try {
                    url = mFeedItems.get(index).getObject().getObjectImageUrl();
                }
                catch(NullPointerException e) {
                    Print.exception(e);
                }
                return Pair.create(url, R.drawable.user_placeholder);
            }

            @Override
            public boolean playButtonVisible(int index) {
                return false;
            }

        });

        mView.loadItems(mFeedItems.size(), this, ITEM_CLICK, IMAGE_CLICK);
    }

    @Override
    public void onItemClick(int type, int index, Bundle bundle) {
        Print.log("Click", index, "item?", type == ITEM_CLICK, "image?", type == IMAGE_CLICK);

        switch(type) {
            case ITEM_CLICK:
                if(mOnItemClickAtIndex != null)
                    mOnItemClickAtIndex.onItemClick(mGroup, getAdapterPosition(), index);
                break;
            case IMAGE_CLICK:
                try {
                    mView.getContext().startActivity(mGroup.getActivities().get(index).getObject().buildOnClickIntent(mView.getContext()));
                }
                catch(Exception e) {
                    Print.log(e.toString());
                }
                break;
        }
    }

}
