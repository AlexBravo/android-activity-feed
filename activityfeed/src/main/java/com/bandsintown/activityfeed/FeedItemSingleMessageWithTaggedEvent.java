package com.bandsintown.activityfeed;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.objects.SizeEstimate;
import com.bandsintown.activityfeed.util.FeedUtil;
import com.bandsintown.activityfeed.util.Print;

public class FeedItemSingleMessageWithTaggedEvent extends AbsFeedItemSingleView {

	protected TextView mMessage;

	protected View mEventSection;
	protected ImageView mBigImage;
	protected TextView mTitle;
	protected TextView mDescription;
	protected View mBigImageTextSection;

	protected SizeEstimate mEstimate;

	protected int mHeight;
	protected int mWidth;

	protected Point mGuess;

	protected static final int WIDTH_ERROR_MARGIN = 4;
	protected static final int HEIGHT_ERROR_MARGIN = 2;

	public FeedItemSingleMessageWithTaggedEvent(Context context, @Nullable SizeEstimate imageViewSize) {
		super(context);
		mEstimate = imageViewSize;
	}

//	private void calculateImageViewSizeGuess() {
//		float fraction = getResources().getFraction(R.fraction.sixteen_by_nine, 1, 1);
//		float horizontalMargin = getResources().getDimension(R.dimen.activity_feed_card_horizontal_margin);
//
//		if(getResources().getBoolean(R.bool.isTablet)) {
//			mTableMainFeedWidthGuess = (int) (getResources().getDimension(R.dimen.feed_width) - horizontalMargin * 2);
//			mTableMainFeedHeightGuess = (int) (mTableMainFeedWidthGuess / fraction);
//		}
//
//		//this assumes feed fills the screen, if we change the layout we need to change this
//		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//		Display display = wm.getDefaultDisplay();
//		Point size = new Point();
//		display.getSize(size);
//
//		mWidthGuess = (int) (size.x - 2 * horizontalMargin);
//		mHeightGuess = (int) (mWidthGuess / fraction);
//	}

	@Override
	protected void initLayout() {
		mEventSection = findViewById(R.id.fir_event_section);
		mBigImage = (ImageView) findViewById(R.id.afibi_image);
		mTitle = (TextView) findViewById(R.id.afibi_title);
		mDescription = (TextView) findViewById(R.id.afibi_desc);
		mBigImageTextSection = findViewById(R.id.afibi_text_section);

		mMessage = (TextView) findViewById(R.id.fir_message);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.feed_item_message_event;
	}

	public void setImage(final AppCompatActivity activity, final String url, final boolean isUserImage) {
		// this helps display the correct size image, but also make sure that it is only done once
		if(mHeight == 0 || mWidth == 0) {

			mGuess = mEstimate.getEstimate();

			if(mGuess.x > 0 && mGuess.y > 0) {
				ImageProvider.getInstance(getContext()).displayBigImage(url, mBigImage, mGuess.x, mGuess.y,
						ImageProvider.activityFeedImageDisplayer(getContext()));
				mEventSection.setVisibility(VISIBLE);
			}

			mBigImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					mHeight = mBigImage.getHeight();
					mWidth = mBigImage.getWidth();
					mBigImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);

					if(Math.abs(mGuess.y - mHeight) > HEIGHT_ERROR_MARGIN || Math.abs(mGuess.x - mWidth) > WIDTH_ERROR_MARGIN) {
						//depending on screen density the estimate can be slightly off but still acceptable
						Print.log("Guesses", mGuess.y, mGuess.x, "Actual", mHeight, mWidth, url);
						setImage(activity, url, isUserImage);
					}
				}

			});
		}
		else {
			ImageProvider.getInstance(getContext()).displayBigImage(url, mBigImage, mWidth, mHeight,
					ImageProvider.activityFeedImageDisplayer(getContext()));
			mEventSection.setVisibility(VISIBLE);
		}
	}

	public void setObjectTitle(String title) {
		if(FeedUtil.stringHasContent(title)) {
			mTitle.setText(title);
			mEventSection.setVisibility(VISIBLE);
		}
	}

	public void setObjectDescription(String description) {
		if(FeedUtil.stringHasContent(description)) {
			mDescription.setText(description);
			mEventSection.setVisibility(VISIBLE);
		}
	}

	public void setUserMessage(String message) {
		mMessage.setText(message);
		mMessage.setVisibility(VISIBLE);
	}

	public void hideUserMessageView() {
		mMessage.setVisibility(GONE);
	}

	public void setOnImageClickListener(View.OnClickListener listener) {
		mBigImage.setOnClickListener(listener);
	}

	public void setDefaultImage(int resId) {
		mBigImage.setImageResource(resId);
		mBigImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
	}

	public void hideEventSection() {
		mEventSection.setVisibility(GONE);
	}

	public void hideTextSection() {
		mBigImageTextSection.setVisibility(GONE);
	}

	public void showTextSection() {
		mBigImageTextSection.setVisibility(VISIBLE);
	}

}
