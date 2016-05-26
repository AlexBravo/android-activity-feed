package com.bandsintown.activityfeed;

import android.content.Context;
import android.support.v8.renderscript.RenderScript;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bandsintown.activityfeed.image.ImageProvider;

public class FeedItemSingleUserProfile extends AbsFeedItemSingleView {

	private ImageView mInsetProfileImage;
	private ImageView mBackgroundProfileImage;
	private TextView mName;
	private TextView mLocation;

	private int mHeight;
	private int mWidth;

	public FeedItemSingleUserProfile(Context context) {
		super(context);
	}

	@Override
	protected void initLayout() {
		mInsetProfileImage = (ImageView) findViewById(R.id.wup_inset_image);
		mBackgroundProfileImage = (ImageView) findViewById(R.id.wup_big_image);
		mName = (TextView) findViewById(R.id.toolbar_anchored_title);
		mLocation = (TextView) findViewById(R.id.wup_location);

		ViewGroup.LayoutParams params = mBackgroundProfileImage.getLayoutParams();
		params.height = (int) getResources().getDimension(R.dimen.activity_feed_big_image_height);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.aaf_item_user_profile;
	}

	public void setLocation(String location) {
		mLocation.setText(location);
	}

	public void setName(String name) {
		mName.setText(name);
	}

	public void setImages(final RenderScript renderScript, final String imageUri, float size) {
		if(imageUri != null && mHeight == 0 && mWidth == 0) {
			mBackgroundProfileImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					mHeight = mBackgroundProfileImage.getHeight();
					mWidth = mBackgroundProfileImage.getWidth();

					ImageProvider.getInstance(getContext()).displayBlurImageInActivityFeed(imageUri, mBackgroundProfileImage, renderScript, mWidth, mHeight);
					mBackgroundProfileImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}

			});
			ImageProvider.getInstance(getContext()).displayPersonImage(imageUri, mInsetProfileImage, size, size);
		}
		else if(imageUri != null) {
			ImageProvider.getInstance(getContext()).displayBlurImageInActivityFeed(imageUri, mBackgroundProfileImage, renderScript, mWidth, mHeight);
			ImageProvider.getInstance(getContext()).displayPersonImage(imageUri, mInsetProfileImage, size, size);
		}
		else {
			mBackgroundProfileImage.setImageResource(R.drawable.placeholder_big_image);
			mBackgroundProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
			ImageProvider.getInstance(getContext()).systemDisplayPersonImage(R.drawable.user_placeholder, mInsetProfileImage);
		}
	}

	public void setImagesClickListener(OnClickListener listener) {
		mBackgroundProfileImage.setOnClickListener(listener);
		mInsetProfileImage.setOnClickListener(listener);
	}

}
