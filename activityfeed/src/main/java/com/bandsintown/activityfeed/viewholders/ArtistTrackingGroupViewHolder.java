package com.bandsintown.activityfeed.viewholders;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.FeedViewOptions;
import com.bandsintown.activityfeed.objects.FeedItemInterface;

import java.util.ArrayList;

public class ArtistTrackingGroupViewHolder extends AbsImageGroupViewHolder {

	public ArtistTrackingGroupViewHolder(AppCompatActivity activity, FeedViewOptions options, View itemView) {
		super(activity, options, itemView);
	}

	@Override
	protected ArrayList<String> getImageUrls() {
		ArrayList<String> urls = new ArrayList<>();
		for(FeedItemInterface item : mGroup.getActivities()) {
			if(item.getObject().getArtistStub() != null)
				urls.add(String.format(FeedValues.THUMB_URL, item.getObject().getArtistStub().getImageId()));
			else
				urls.add(null);
		}

		return urls;
	}

}
