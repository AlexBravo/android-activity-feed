package com.bandsintown.activityfeed.adapters;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bandsintown.activityfeed.ApiListener;
import com.bandsintown.activityfeed.BandsintownApi;
import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.EventAnnouncementGroupView;
import com.bandsintown.activityfeed.FeedDatabase;
import com.bandsintown.activityfeed.FeedItemImageGroupView;
import com.bandsintown.activityfeed.FeedItemPlayMyCityView;
import com.bandsintown.activityfeed.FeedItemSingleLikedActivity;
import com.bandsintown.activityfeed.FeedItemSingleMessageWithTaggedEvent;
import com.bandsintown.activityfeed.FeedItemSingleMessageWithTaggedEventFlexibleHeight;
import com.bandsintown.activityfeed.FeedItemSingleUserProfile;
import com.bandsintown.activityfeed.FeedItemSingleWatchTrailer;
import com.bandsintown.activityfeed.GroupFeedItemMiniList;
import com.bandsintown.activityfeed.GroupTextPostView;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.audio.AudioStateItem;
import com.bandsintown.activityfeed.audio.AudioStateManager;
import com.bandsintown.activityfeed.audio.OnAudioStateChangeListener;
import com.bandsintown.activityfeed.interfaces.OnFeedMenuItemAdapterClickListener;
import com.bandsintown.activityfeed.interfaces.OnItemClickAtIndexAtSubIndex;
import com.bandsintown.activityfeed.interfaces.OnLikeClickedListener;
import com.bandsintown.activityfeed.objects.ActivityFeedEntry;
import com.bandsintown.activityfeed.objects.FeedGroupInterface;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.objects.FeedListItem;
import com.bandsintown.activityfeed.objects.IntentRouter;
import com.bandsintown.activityfeed.objects.SizeEstimate;
import com.bandsintown.activityfeed.util.Print;
import com.bandsintown.activityfeed.viewholders.AbsActivityFeedGroupViewHolder;
import com.bandsintown.activityfeed.viewholders.AbsActivityFeedSingleViewHolder;
import com.bandsintown.activityfeed.viewholders.ActivityViewBuilder;
import com.bandsintown.activityfeed.viewholders.ArtistTrackingGroupViewHolder;
import com.bandsintown.activityfeed.viewholders.EventAnnouncementGroupViewHolder;
import com.bandsintown.activityfeed.viewholders.FeedItemMessageWIthTaggedEventSingleViewHolder;
import com.bandsintown.activityfeed.viewholders.GroupListensFeedItemViewHolder;
import com.bandsintown.activityfeed.viewholders.GroupRatingsViewHolder;
import com.bandsintown.activityfeed.viewholders.GroupTextPostViewHolder;
import com.bandsintown.activityfeed.viewholders.GroupTrackUsersViewHolder;
import com.bandsintown.activityfeed.viewholders.GroupWatchTrailerViewHolder;
import com.bandsintown.activityfeed.viewholders.LikedActivityViewHolder;
import com.bandsintown.activityfeed.viewholders.LoadMoreViewHolder;
import com.bandsintown.activityfeed.viewholders.PlayMyCityActivityGroupViewHolder;
import com.bandsintown.activityfeed.viewholders.PostedPhotosGroupViewHolder;
import com.bandsintown.activityfeed.viewholders.PromoteGroupViewHolder;
import com.bandsintown.activityfeed.viewholders.RsvpGroupViewHolder;
import com.bandsintown.activityfeed.viewholders.SimpleViewHolder;
import com.bandsintown.activityfeed.viewholders.UserProfileFeedItemSingleViewHolder;
import com.bandsintown.activityfeed.viewholders.WatchTrailerFeedItemSingleViewHolder;
import com.google.gson.JsonObject;
import com.trello.navi.Event;
import com.trello.navi.Listener;
import com.trello.navi.NaviComponent;
import com.trello.navi.model.ActivityResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by rjaylward on 5/2/16 for Bandsintown
 */
public abstract class AbsFeedAdapter extends RecyclerView.Adapter implements OnAudioStateChangeListener {

    protected AppCompatActivity mActivity;
    protected RecyclerView mRecyclerView;
    protected Handler mHandler;
    protected IntentRouter mRouter;
    protected BandsintownApi mApi;
    protected FeedDatabase mFeedDatabase;

    protected ArrayList<FeedListItem> mItems = new ArrayList<>();
    protected HashSet<Integer> mIndexOfItemsWithMediaControls = new HashSet<>();

    protected OnLikeClickedListener<FeedItemInterface> mOnLikeClickListener;
    protected OnLikeClickedListener<FeedGroupInterface> mOnGroupLikeListener;

    protected FeedListItem mItemToDelete;

    protected int CHECK_LISTEN_ITEMS_ONLY = 4; //just some arbitrary object to add to the notifyItemChanged payload
    protected int CHECK_ALL_FOR_LISTEN_ITEMS = 5; //just some arbitrary object to add to the notifyItemChanged payload
    protected int UPDATE_LIKE_STATUS = 6;

    public AbsFeedAdapter(AppCompatActivity activity, NaviComponent component, RecyclerView recyclerView,
                          BandsintownApi api, FeedDatabase database, IntentRouter router) {
        mActivity = activity;
        mRecyclerView = recyclerView;
        mRouter = router;
        mApi = api;
        mFeedDatabase = database;

        mOnLikeClickListener = new OnLikeClickedListener<FeedItemInterface>() {

            @Override
            public void onLike(FeedItemInterface item, boolean isLiked) {
                makeLikePostApiRequest(item, isLiked);
            }

        };

        mOnGroupLikeListener = new OnLikeClickedListener<FeedGroupInterface>() {

            @Override
            public void onLike(FeedGroupInterface group, boolean isLiked) {
                makeGroupLikeApiRequest(group, isLiked);
            }

        };

        component.addListener(Event.START, new Listener<Void>() {

            @Override
            public void call(Void aVoid) {
                syncMediaControlStates(true);
                AudioStateManager.getInstance().addListener(AbsFeedAdapter.this, mActivity.getClass().getCanonicalName());
            }

        });

        component.addListener(Event.STOP, new Listener<Void>() {

            @Override
            public void call(Void aVoid) {
                AudioStateManager.getInstance().removeAllListeners(mActivity.getClass().getCanonicalName());
            }

        });

        component.addListener(Event.ACTIVITY_RESULT, new Listener<ActivityResult>() {
            //TODO check if this even works in nested fragments...

            @Override
            public void call(ActivityResult activityResult) {
                if(activityResult != null && activityResult.requestCode() == FeedValues.REQUEST_CODE_VIEW_GROUP_FEED && activityResult.resultCode() == Activity.RESULT_OK) {
                    if(activityResult.data() != null) {
                        FeedGroupInterface group = activityResult.data().getParcelableExtra(FeedValues.ACTIVITY_FEED_GROUP);
                        changeItem(group);
                    }
                }
            }

        });

        if(activity.getSupportMediaController() != null)
            activity.getSupportMediaController().registerCallback(new MediaControllerCompat.Callback() {

                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                    super.onPlaybackStateChanged(state);

                    //The current item should be set already, this will just set the correct state
                    try {
                        if(AudioStateManager.getInstance().getCurrent() != null)
                            AudioStateManager.getInstance().getCurrent().setState(state.getState(), true);
                    }
                    catch(Exception e) {
                        Print.exception(e, false);
                    }
                }
            });

        AudioStateManager.getInstance().addListener(this, mActivity.getClass().getCanonicalName());
    }

    public void syncMediaControlStates(boolean checkAll) {
        try {
//			Print.log("Syncing media control states", "size of list of media control items", mIndexOfItemsWithMediaControls.size());
            if(!checkAll) {
                for(Integer index : mIndexOfItemsWithMediaControls) {
                    notifyItemChanged(index, CHECK_LISTEN_ITEMS_ONLY);
                }
            }
            else {
                notifyItemRangeChanged(0, mItems.size(), CHECK_ALL_FOR_LISTEN_ITEMS);
            }
        }
        catch(Exception e) {
            Print.log(e.getMessage());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case FeedValues.VERB_CODE_ACTIVITY_FEED_LOADING :
                return new LoadMoreViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.view_load_more, parent, false));
            case FeedValues.VERB_CODE_ARTIST_TRACKING :
            case FeedValues.VERB_CODE_EVENT_ANNOUNCEMENT :
            case FeedValues.VERB_CODE_RSVP :
            case FeedValues.VERB_CODE_LISTEN :
            case FeedValues.VERB_CODE_REQUEST :
            case FeedValues.VERB_CODE_RATE :
            case FeedValues.VERB_CODE_PROMOTE :
            case FeedValues.VERB_CODE_MESSAGE_RSVPS :
                return new FeedItemMessageWIthTaggedEventSingleViewHolder(mActivity,
                        new FeedItemSingleMessageWithTaggedEvent(mActivity, getAverageFeedItemImageSizeEstimate()));
            case FeedValues.VERB_CODE_USER_POST :
            case FeedValues.VERB_CODE_ARTIST_POST :
                return new FeedItemMessageWIthTaggedEventSingleViewHolder(mActivity,
                        new FeedItemSingleMessageWithTaggedEventFlexibleHeight(mActivity, getAverageFeedItemImageSizeEstimate()));
            case FeedValues.VERB_CODE_USER_TRACKING :
                return new UserProfileFeedItemSingleViewHolder(mActivity, new FeedItemSingleUserProfile(mActivity));
            case FeedValues.VERB_CODE_WATCH_TRAILER :
            case FeedValues.VERB_CODE_POST_TRAILER :
                return new WatchTrailerFeedItemSingleViewHolder(mActivity,
                        new FeedItemSingleWatchTrailer(mActivity, getAverageFeedItemImageSizeEstimate()));
            //group view holders...
            case FeedValues.VERB_CODE_GROUP_USER_TRACKING :
                return new GroupTrackUsersViewHolder(mActivity, new GroupFeedItemMiniList(mActivity));
            case FeedValues.VERB_CODE_GROUP_RATE :
                return new GroupRatingsViewHolder(mActivity, new GroupFeedItemMiniList(mActivity));
            case FeedValues.VERB_CODE_GROUP_LISTEN :
                return new GroupListensFeedItemViewHolder(mActivity, new GroupFeedItemMiniList(mActivity));
            case FeedValues.VERB_CODE_GROUP_REQUEST :
                return new PlayMyCityActivityGroupViewHolder(mActivity, new FeedItemPlayMyCityView(mActivity, getAverageFeedItemImageSizeEstimate()));
            case FeedValues.VERB_CODE_GROUP_ARTIST_TRACKING :
                return new ArtistTrackingGroupViewHolder(mActivity, new FeedItemImageGroupView(mActivity));
            case FeedValues.VERB_CODE_GROUP_RSVP :
                return new RsvpGroupViewHolder(mActivity, new FeedItemImageGroupView(mActivity));
            case FeedValues.VERB_CODE_GROUP_EVENT_ANNOUNCEMENT :
                return new EventAnnouncementGroupViewHolder(mActivity, new EventAnnouncementGroupView(mActivity));
            case FeedValues.VERB_CODE_GROUP_ARTIST_POST_ALL_IMAGES :
            case FeedValues.VERB_CODE_GROUP_USER_POST_ALL_IMAGES :
                return new PostedPhotosGroupViewHolder(mActivity, new FeedItemImageGroupView(mActivity));
            case FeedValues.VERB_CODE_GROUP_USER_POST :
            case FeedValues.VERB_CODE_GROUP_ARTIST_POST :
            case FeedValues.VERB_CODE_GROUP_MESSAGE_RSVPS :
                return new GroupTextPostViewHolder(mActivity, new GroupTextPostView(mActivity));
            case FeedValues.VERB_CODE_GROUP_PROMOTE :
                return new PromoteGroupViewHolder(mActivity, new EventAnnouncementGroupView(mActivity));
            case FeedValues.VERB_CODE_GROUP_WATCH_TRAILER :
                return new GroupWatchTrailerViewHolder(mActivity, new FeedItemImageGroupView(mActivity));
            case FeedValues.VERB_CODE_LIKE :
                return new LikedActivityViewHolder(mActivity, new FeedItemSingleLikedActivity(mActivity),
                        new ActivityViewBuilder(mActivity, mApi, mFeedDatabase, getAverageFeedItemImageSizeEstimate()));
            default :
                if(FeedValues.IS_DEBUG_MODE)
                    throw new IllegalArgumentException("view type not found: " + viewType);
                else {
                    Print.exception(new IllegalArgumentException("view type not found: " + viewType));
                    return new SimpleViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.listitem_no_content, parent, false));
                }
        }
    }

    protected abstract SizeEstimate getAverageFeedItemImageSizeEstimate();

    protected OnFeedMenuItemAdapterClickListener mOnFeedMenuItemClickListener = new OnFeedMenuItemAdapterClickListener() {

        @Override
        public void onDeleteClick(int feedId, int position) {
            deletePostAtPosition(position);
        }

        @Override
        public void onReportClick(int feedId, int position) {
            openFlagFeedItemActivity(feedId);
        }

    };

    protected OnItemClickAtIndexAtSubIndex<FeedGroupInterface> mOnItemOrLoadMoreListener = new OnItemClickAtIndexAtSubIndex<FeedGroupInterface>() {

        @Override
        public void onItemClick(FeedGroupInterface item, int index, int subIndex) {
            mRouter.onGroupClicked(mActivity, item, index, subIndex, FeedValues.REQUEST_CODE_VIEW_GROUP_FEED);
        }

    };

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Some items, like loading items, won't have an entry. Just ignore them
        if(mItems.get(position).getEntry() == null)
            return;

        String verb = mItems.get(position).getEntry().getVerb();
        Print.log("item", mItems.get(position).getEntry().getType().name(), verb);

        if(mItems.get(position).getEntry().getType() == ActivityFeedEntry.Type.ITEM) {
            try {
                if(holder instanceof AbsActivityFeedSingleViewHolder)
                    ((AbsActivityFeedSingleViewHolder) holder).buildItem((FeedItemInterface) mItems.get(position).getEntry(),
                            position == mItems.size() - 1, mOnLikeClickListener, mOnFeedMenuItemClickListener, mRouter);
//				else
//					Print.exception(new Exception("holder not found for group item with verb: " + verb));
            } catch(Exception e) {
                Print.exception(e);
            }
        }
        else if(mItems.get(position).getEntry().getType() == ActivityFeedEntry.Type.GROUP) {
            try {
                if(holder instanceof AbsActivityFeedGroupViewHolder)
                    ((AbsActivityFeedGroupViewHolder) holder).buildItem((FeedGroupInterface) mItems.get(position).getEntry(),
                            position == mItems.size() - 1, mOnGroupLikeListener, mOnItemOrLoadMoreListener, mRouter);
                else
                    Print.exception(new Exception("holder not found for group item with verb: " + verb));

                if(holder instanceof GroupListensFeedItemViewHolder) {
                    mIndexOfItemsWithMediaControls.add(position);
                    ((GroupListensFeedItemViewHolder) holder).syncPlaybackState();
                }

            } catch(Exception e) {
                Print.exception(e);
            }
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

        if(holder instanceof GroupListensFeedItemViewHolder) {
            ((GroupListensFeedItemViewHolder) holder).recycle();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        if(!payloads.isEmpty()) {
            boolean checkAllForListens = payloads.contains(CHECK_ALL_FOR_LISTEN_ITEMS);
            boolean onlyCheckListens = payloads.contains(CHECK_LISTEN_ITEMS_ONLY);
            boolean updateLikeStatus = payloads.contains(UPDATE_LIKE_STATUS);


            if(checkAllForListens || onlyCheckListens) {
                if(holder instanceof GroupListensFeedItemViewHolder) {
                    ((GroupListensFeedItemViewHolder) holder).syncPlaybackState();
                    if(checkAllForListens)
                        mIndexOfItemsWithMediaControls.add(position); //somehow mIndexOfItemsWithMediaControls gets cleared and we have to add them all back
                }
                else if(onlyCheckListens)
                    mIndexOfItemsWithMediaControls.remove(position); //means the mIndexOfItemsWithMediaControls got out of sync
            }

            if(updateLikeStatus) {
                ActivityFeedEntry entry = mItems.get(position).getEntry();

                if(holder instanceof AbsActivityFeedGroupViewHolder && entry.getType() == ActivityFeedEntry.Type.GROUP)
                    ((AbsActivityFeedGroupViewHolder) holder).updateLikeStatus((FeedGroupInterface) entry);
                else if(holder instanceof AbsActivityFeedSingleViewHolder && entry.getType() == ActivityFeedEntry.Type.ITEM)
                    ((AbsActivityFeedSingleViewHolder) holder).updateLikeStatus((FeedItemInterface) entry, mRouter);
            }
        }
        else
            super.onBindViewHolder(holder, position, payloads); //just calls onBindViewHolder(holder, position)
    }

    public void deletePostAtPosition(final int position) {
        mItemToDelete = mItems.get(position);

        mItems.remove(position);
        notifyItemRemoved(position);

        HandlerThread handlerThread = new HandlerThread("delete_post_" + mItemToDelete.getEntry().getIdentifier());
        handlerThread.start();

        mHandler = new Handler(handlerThread.getLooper());

        //Make a snackbar which gives them a chance to undo
        final Snackbar snackbar = Snackbar.make(mRecyclerView, R.string.deleting_your_post, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.undo, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Print.log("cancel untrack job");
                cancelDeleteJob();
                snackbar.dismiss();
                mItems.add(position, mItemToDelete);
                notifyItemInserted(position);
            }

        }).setActionTextColor(ContextCompat.getColor(mActivity, R.color.bit_teal)).show();

        makeUntrackApiRequest();
    }

    private void makeUntrackApiRequest() {
        mHandler.postDelayed(mDeleteJob, FeedValues.SNACKBAR_SHORT_DURATION + 500);
    }

    private void cancelDeleteJob() {
        mHandler.removeCallbacks(mDeleteJob);
    }

    private Runnable mDeleteJob = new Runnable() {

        @Override
        public void run() {
            Print.log("running delete job");

            if(!(mItemToDelete.getEntry().getIdentifier() instanceof Integer))
                return;

            final int id = (int) mItemToDelete.getEntry().getIdentifier();

            mApi.deleteActivityFeedItem(id, new ApiListener() {

                @Override
                public void onResponse(Object response) {
                    mFeedDatabase.deleteActivityFeedItem(id);
                }

                @Override
                public void onErrorResponse(Exception error) {
                    Toast.makeText(mActivity, R.string.unfortunately_unable_to_delete_your_post_please_try_again_later, Toast.LENGTH_SHORT).show();
                }

            });
        }
    };

    private void openFlagFeedItemActivity(int feedId) {
        mRouter.onFlagFeedItem(feedId);
    }

    /**
     * Overrides the default like click listener
     */
    public void setOnLikeClickListener(OnLikeClickedListener<FeedItemInterface> onSingleLikeClickListener, OnLikeClickedListener<FeedGroupInterface> onGroupLikeClickedListener) {
        mOnLikeClickListener = onSingleLikeClickListener;
        mOnGroupLikeListener = onGroupLikeClickedListener;
    }

    public void makeLikePostApiRequest(final FeedItemInterface feedItem, final boolean isLiked) {
        mApi.updatedLikeStatus(feedItem.getId(), isLiked, new ApiListener<JsonObject>() {

            @Override
            public void onResponse(JsonObject response) {
                feedItem.setIsLikedByUser(isLiked);
                feedItem.incrementLikeCountByAmount(isLiked ? 1 : -1);

                mFeedDatabase.updateActivityFeedLikeStatus(feedItem, true);
            }

            @Override
            public void onErrorResponse(Exception error) {
                // do nothing because the database shouldn't change
                Print.log("Make like request error ---------->", error.getMessage());
            }

        });
    }

    public void makeGroupLikeApiRequest(final FeedGroupInterface group, final boolean isLiked) {
        mApi.updateGroupLikeStatus(group.getActivities(), isLiked, new ApiListener<JsonObject>() {

            @Override
            public void onResponse(JsonObject response) {
                for(FeedItemInterface feedItem : group.getActivities()) {
                    //if liked state is the same as the group state do nothing, otherwise like or unlike the item
                    if(feedItem.isLikedByUser() != isLiked) {
                        feedItem.setIsLikedByUser(isLiked);
                        feedItem.incrementLikeCountByAmount(isLiked ? 1 : -1);
                    }
                }

                mFeedDatabase.updateActivityGroupLikeStatus(group, true);
            }

            @Override
            public void onErrorResponse(Exception error) {
                // do nothing because the database shouldn't change
                Print.log("Make like request error ---------->", error.getMessage());
            }

        });
    }

    @Override
    public void onAudioStateChanged(AudioStateItem previousItem, AudioStateItem currentItem) {
        syncMediaControlStates(false);
    }

    protected abstract FeedListItem convertGroupToFeedListItem(FeedGroupInterface group);

    public void changeItem(FeedGroupInterface group) {

        FeedListItem item = convertGroupToFeedListItem(group);

        int indexOfGroup = -1;
        for(int i = 0; i < mItems.size(); i++) {
            if(mItems.get(i).isSameItem(item)) {
                indexOfGroup = i;
                break;
            }
        }

        if(indexOfGroup >= 0)
            changeItemAtIndex(indexOfGroup, item);
    }

    public void changeItemAtIndex(int index, FeedListItem item) {
        Print.log("Changing group", item.getEntry().getIdentifier(), "at index", index);
        if(index < mItems.size()) {
            mItems.remove(index);
            mItems.add(index, item);

            notifyItemChanged(index);
        }
    }

    public void notifyLikesChanged() {
        Print.log("Feed Adapter", "received notify likes status changed");
        try {
            if(mItems != null)
                notifyItemRangeChanged(0, mItems.size(), UPDATE_LIKE_STATUS);
        }
        catch(Exception e) {
            Print.log("Error notifying likes changed", e.getMessage());
        }
    }
}
