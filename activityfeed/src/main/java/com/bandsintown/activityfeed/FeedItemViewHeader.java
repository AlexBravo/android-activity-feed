package com.bandsintown.activityfeed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bandsintown.activityfeed.image.ImageProvider;

public class FeedItemViewHeader extends RelativeLayout {

	private ImageView mPosterImage;
	private TextView mName;
	private TextView mDescription;
	private TextView mTimestamp;
//	private TextView mRating;

	public FeedItemViewHeader(Context context) {
		this(context, null);
	}

	public FeedItemViewHeader(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FeedItemViewHeader(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		inflateLayout();
	}

	private void inflateLayout() {
		LayoutInflater.from(getContext()).inflate(R.layout.activity_feed_item_header, this, true);

		mPosterImage = (ImageView) findViewById(R.id.afih_image);
		mName = (TextView) findViewById(R.id.afih_name);
		mDescription = (TextView) findViewById(R.id.afih_desc);
		mTimestamp = (TextView) findViewById(R.id.afih_timestamp);
//		mRating = (TextView) findViewById(R.id.afih_rating);

		setId(R.id.feed_item_header);
	}

	public void setPosterImage(ImageProvider imageProvider, String url) {
		if(url != null) {
			float personDimens = getContext().getResources().getDimension(R.dimen.activity_feed_header_image_size);
			imageProvider.displayPersonImage(url, mPosterImage, personDimens, personDimens);
		}
		else
			imageProvider.systemDisplayPersonImage(R.drawable.user_placeholder, mPosterImage);
	}

	public void setName(String name) {
		mName.setText(name);
	}

	public void setDescription(String description) {
		if(mDescription != null)
			mDescription.setText(description);
	}

	public void setTimestamp(String timestamp) {
		mTimestamp.setText(timestamp);
	}

//	public void setRating(BaseActivity activity, FeedItemInterface item) {
//		String rating = HelpBot.getStars(activity, item);
//
//		mRating.setText(rating);
//		mRating.setVisibility(VISIBLE);
//	}

}
