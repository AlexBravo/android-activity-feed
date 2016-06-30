package com.bandsintown.activityfeed;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.objects.SizeEstimate;
import com.bandsintown.activityfeed.util.FeedUtil;
import com.bandsintown.activityfeed.util.Logger;

public class FeedItemSingleMessageWithTaggedEvent extends AbsFeedItemSingleView {

	protected TextView mMessage;

	protected View mImageSection;
	protected ImageView mEventImageView;
	protected TextView mTitle;
	protected TextView mDescription;
	protected View mEventImageTextSection;

	protected SizeEstimate mEstimate;

	protected int mHeight;
	protected int mWidth;

	protected Point mGuess;

	protected static final int WIDTH_ERROR_MARGIN = 4;
	protected static final int HEIGHT_ERROR_MARGIN = 2;

	public FeedItemSingleMessageWithTaggedEvent(Context context, @Nullable SizeEstimate imageViewSize) {
		super(context);

		mEstimate = imageViewSize;

		if(mEstimate == null)
			mEstimate = new SizeEstimate() {

				@Override
				protected Point estimateSize() {
					return new Point(0,0);
				}

				@Override
				protected Point estimateTabletSize() {
					return new Point(0,0);
				}

				@Override
				protected boolean useTabletEstimate() {
					return false;
				}
			};
	}

	@Override
	protected void initLayout() {
		mImageSection = findViewById(R.id.fir_event_section);
		mEventImageView = (ImageView) findViewById(R.id.afibi_event_image);
		mTitle = (TextView) findViewById(R.id.afibi_title);
		mDescription = (TextView) findViewById(R.id.afibi_desc);
		mEventImageTextSection = findViewById(R.id.afibi_text_section);

		mMessage = (TextView) findViewById(R.id.fir_message);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.aaf_item_message_event;
	}

	public void setImage(final AppCompatActivity activity, final String url, final boolean isUserImage) {
		// this helps display the correct size image, but also make sure that it is only done once
		if(mHeight == 0 || mWidth == 0) {

			mGuess = mEstimate.getEstimate();

			if(mGuess.x > 0 && mGuess.y > 0) {
				ImageProvider.getInstance(getContext()).displayBigImage(url, mEventImageView, mGuess.x, mGuess.y,
						ImageProvider.activityFeedImageDisplayer(getContext()));
				mImageSection.setVisibility(VISIBLE);
			}

			mEventImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					mHeight = mEventImageView.getHeight();
					mWidth = mEventImageView.getWidth();
					mEventImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

					if(Math.abs(mGuess.y - mHeight) > HEIGHT_ERROR_MARGIN || Math.abs(mGuess.x - mWidth) > WIDTH_ERROR_MARGIN) {
						//depending on screen density the estimate can be slightly off but still acceptable
						Logger.log("Guesses", mGuess.y, mGuess.x, "Actual", mHeight, mWidth, url);
						setImage(activity, url, isUserImage);
					}
				}

			});
		}
		else {
			ImageProvider.getInstance(getContext()).displayBigImage(url, mEventImageView, mWidth, mHeight,
					ImageProvider.activityFeedImageDisplayer(getContext()));
			mImageSection.setVisibility(VISIBLE);
		}
	}

	public void setObjectTitle(String title) {
		if(FeedUtil.stringHasContent(title)) {
			mTitle.setText(title);
			mImageSection.setVisibility(VISIBLE);
		}
	}

	public void setObjectDescription(String description) {
		if(FeedUtil.stringHasContent(description)) {
			mDescription.setText(description);
			mImageSection.setVisibility(VISIBLE);
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
		mEventImageView.setOnClickListener(listener);
	}

	public void setDefaultImage(int resId) {
		mEventImageView.setImageResource(resId);
		mEventImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	}

	public void hideImageSection() {
		mImageSection.setVisibility(GONE);
	}

	public void hideTextSection() {
		mEventImageTextSection.setVisibility(GONE);
	}

	public void showTextSection() {
		mEventImageTextSection.setVisibility(VISIBLE);
	}

	public void setMessageLinksClickable(boolean clickable) {
		if(clickable) {
			Linkify.addLinks(mMessage, Linkify.ALL);
		}
	}

}
