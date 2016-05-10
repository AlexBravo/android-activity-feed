package com.bandsintown.activityfeed;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.bandsintown.activityfeed.image.ImageProvider;

public class GroupTextPostView extends AbsFeedItemGroupView {

	private TextView mMessage;
	private ImageView mImage;

	public GroupTextPostView(Context context) {
		super(context);
	}

	public GroupTextPostView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GroupTextPostView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void initLayout() {
		mMessage = (TextView) findViewById(R.id.figtp_message);
		mImage = (ImageView) findViewById(R.id.figtp_image);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.feed_item_group_text_post;
	}

	public void setImage(Context context, String url) {
		if(context != null && url != null) {
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();

			ImageProvider.getInstance(context).displayImageDirect(url, mImage,
					ImageProvider.activityFeedUserPostDisplayer(getContext(), metrics.widthPixels), null);
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
