package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bandsintown.activityfeed.view.FeedItemSingleWatchTrailer;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.interfaces.OnFeedMenuItemAdapterClickListener;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.IntentRouter;

/**
 * Created by rjaylward on 12/10/15 for Bandsintown
 */
public class WatchTrailerFeedItemSingleViewHolder extends AbsActivityFeedSingleViewHolder {

    private FeedItemSingleWatchTrailer mItem;

    public WatchTrailerFeedItemSingleViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
        super(activity, options, itemView);

        if(itemView instanceof FeedItemSingleWatchTrailer)
            mItem = (FeedItemSingleWatchTrailer) itemView;
    }

    @Override
    public void buildItem(final FeedItemInterface feedItem, boolean lastItem, OnLikeClickedListener<FeedItemInterface> onLikeClickListener,
                          OnFeedMenuItemAdapterClickListener feedMenuItemClickListener, final IntentRouter router) {
        super.buildItem(feedItem, lastItem, onLikeClickListener, feedMenuItemClickListener, router);

        if(mItem != null) {
            String imageUrl = feedItem.getObject().getObjectImageUrl();
            if(imageUrl != null)
                mItem.setImage(imageUrl);

            if(feedItem.getObject().hasImageOverlayTitleAndDesc()) {
                mItem.setObjectTitle(feedItem.getObject().getObjectTitle(mActivity));
                mItem.setObjectDescription(feedItem.getObject().getObjectDescription(mActivity));

                if(imageUrl == null)
                    mItem.setDefaultImage(R.drawable.placeholder_big_image);
            }

            mItem.setOnImageClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //TODO do we have 2 separate click actions here, 1 for the artist and another for the video or should both go to the video?
                    router.onObjectClicked(feedItem);
                }

            });

            mItem.setOnPlayButtonClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    router.onPlayTrailerClicked(feedItem);
                }

            });
        }
    }
}
