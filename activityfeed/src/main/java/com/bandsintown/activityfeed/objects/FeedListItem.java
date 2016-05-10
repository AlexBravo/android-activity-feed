package com.bandsintown.activityfeed.objects;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.util.FeedUtil;

/**
 * Created by rjaylward on 5/2/16 for Bandsintown
 */
public class FeedListItem {

    private ActivityFeedEntry mEntry;
    private int mType;

    public ActivityFeedEntry getEntry() {
        return mEntry;
    }

    public void setEntry(ActivityFeedEntry entry) {
        if(entry.getType() == ActivityFeedEntry.Type.GROUP) {
            FeedGroupInterface group = (FeedGroupInterface) entry;
            if(group.isSingleItem())
                mEntry = group.getActivities().get(0);
            else
                mEntry = entry;
        }
        else
            mEntry = entry;

        mType = FeedUtil.getTypeFromVerb(mEntry.getVerb(), mEntry.getType() == ActivityFeedEntry.Type.ITEM ? 1 : ((FeedGroupInterface) mEntry).getActivities().size());
        if(mType == FeedValues.VERB_CODE_GROUP_ARTIST_POST && mEntry.getType() == ActivityFeedEntry.Type.GROUP && doAllItemsHaveImage())
            mType = FeedValues.VERB_CODE_GROUP_ARTIST_POST_ALL_IMAGES;
        else if(mType == FeedValues.VERB_CODE_GROUP_USER_POST && mEntry.getType() == ActivityFeedEntry.Type.GROUP && doAllItemsHaveImage())
            mType = FeedValues.VERB_CODE_GROUP_USER_POST_ALL_IMAGES;
    }

    private boolean doAllItemsHaveImage() {
        for(FeedItemInterface item : ((FeedGroupInterface) mEntry).getActivities()) {
            if(item.getObject().getPost().getMediaId() <= 0)
                return false;
        }

        return true;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void setTypeLoading() {
        mType = FeedValues.VERB_CODE_ACTIVITY_FEED_LOADING;
    }

    public boolean isTypeLoading() {
        return mType == FeedValues.VERB_CODE_ACTIVITY_FEED_LOADING;
    }

    public boolean isSameItem(FeedListItem item) {
        try {
            return mEntry.getIdentifier().equals(item.getEntry().getIdentifier());
        }
        catch(Exception e) {
            return false;
        }
    }

}
