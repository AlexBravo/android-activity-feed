package com.bandsintown.activityfeed.viewholders;

import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.objects.FeedItemInterface;

import java.util.ArrayList;

public class GroupWatchTrailerViewHolder extends AbsImageGroupViewHolder {

	public GroupWatchTrailerViewHolder(AppCompatActivity context, View itemView) {
		super(context, itemView);

		PercentRelativeLayout bodyContainer = (PercentRelativeLayout) itemView.findViewById(R.id.fiigs_body_container);

		ImageView playButton = new ImageView(context);
		playButton.setImageResource(R.drawable.play_button);

		int playButtonSize = context.getResources().getDimensionPixelSize(R.dimen.play_button_size);

		PercentRelativeLayout.LayoutParams params = new PercentRelativeLayout.LayoutParams(playButtonSize, playButtonSize);
		params.addRule(PercentRelativeLayout.CENTER_IN_PARENT);

		playButton.setLayoutParams(params);

		bodyContainer.addView(playButton);
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
