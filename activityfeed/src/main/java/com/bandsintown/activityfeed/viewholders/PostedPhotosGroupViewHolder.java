package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.objects.FeedItemInterface;
import com.bandsintown.activityfeed.util.Print;

import java.util.ArrayList;

/**
 * Created by rjaylward on 5/4/16 for Bandsintown
 */
public class PostedPhotosGroupViewHolder extends AbsImageGroupViewHolder {

    public PostedPhotosGroupViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
        super(activity, options, itemView);
    }

    @Override
    protected ArrayList<String> getImageUrls() {
        ArrayList<String> urls = new ArrayList<>();
        for(FeedItemInterface item : mGroup.getActivities()) {
            try {
                if(item.getObject().getPost().getMediaId() > 0)
                    urls.add(String.format(FeedValues.THUMB_URL, item.getObject().getPost().getMediaId()));
                else
                    urls.add(null);
            } catch(Exception e) {
                Print.exception(e);
                urls.add(null);
            }
        }

        return urls;
    }
}
