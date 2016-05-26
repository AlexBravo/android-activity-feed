package com.bandsintown.activityfeed;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bandsintown.activityfeed.image.ImageProvider;

public class FeedItemSinglePost extends AbsFeedItemSingleView {

	private ImageView mImage;
	private TextView mMessage;

	public FeedItemSinglePost(Context context) {
		super(context);
	}

	public FeedItemSinglePost(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FeedItemSinglePost(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void initLayout() {
		mImage = (ImageView) findViewById(R.id.fir_image);
		mMessage = (TextView) findViewById(R.id.fir_message);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.aaf_item_user_post;
	}

	public void setImage(String url) {
		if(url != null) {
			ImageProvider.getInstance(getContext()).displayImageDirect(url, mImage,
					ImageProvider.activityFeedUserPostDisplayer(getContext(), getResources().getDisplayMetrics().widthPixels), null);
			mImage.setVisibility(VISIBLE);
		}
	}

	public void setImageGone() {
		mImage.setVisibility(GONE);
	}

	public void setMessage(String message) {
		if(message != null) {
			mMessage.setText(message);
			mMessage.setVisibility(VISIBLE);
		}
		else
			mMessage.setVisibility(GONE);
	}

}
