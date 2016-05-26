package com.bandsintown.activityfeed;

import android.content.Context;
import android.graphics.Point;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bandsintown.activityfeed.objects.SizeEstimate;
import com.bandsintown.activityfeed.util.FeedUtil;
import com.bandsintown.activityfeed.util.Print;
import com.bandsintown.kahlo.image.provider.Kahlo;

public class FeedItemPlayMyCityView extends AbsFeedItemGroupView {

	private ImageView mMapImage;

	protected int mHeight;
	protected int mWidth;

	protected Point mGuess;
	protected SizeEstimate mEstimate;

	protected static final int WIDTH_ERROR_MARGIN = 4;
	protected static final int HEIGHT_ERROR_MARGIN = 2;

	public FeedItemPlayMyCityView(Context context, SizeEstimate imageViewSize) {
		super(context);
		mEstimate = imageViewSize;
	}

//	protected Point calculateImageViewSizeGuess() {
//		float fraction = getResources().getFraction(R.fraction.sixteen_by_nine, 1, 1);
//		float horizontalMargin = getResources().getDimension(R.dimen.activity_feed_card_horizontal_margin);
//
//		//this assumes feed fills the screen, if we change the layout we need to change this
//		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//		Display display = wm.getDefaultDisplay();
//		Point size = new Point();
//		display.getSize(size);
//
//		int mWidthGuess = (int) (size.x - 2 * horizontalMargin);
//		int mHeightGuess = (int) (mWidthGuess / fraction);
//
//		return new Point(mWidthGuess, mHeightGuess);
//	}
//
//	protected Point calculateTableImageViewSizGuess() {
//		float fraction = getResources().getFraction(R.fraction.sixteen_by_nine, 1, 1);
//		float horizontalMargin = getResources().getDimension(R.dimen.activity_feed_card_horizontal_margin);
//
//		int mTableMainFeedWidthGuess = (int) (getResources().getDimension(R.dimen.feed_width) - horizontalMargin * 2);
//		int mTableMainFeedHeightGuess = (int) (mTableMainFeedWidthGuess / fraction);
//
//		return new Point(mTableMainFeedWidthGuess, mTableMainFeedHeightGuess);
//	}
//
//	protected boolean useTabletSizeEstimate() {
//		return getResources().getBoolean(R.bool.isLandscape) && getResources().getBoolean(R.bool.isTablet);
//	}

	@Override
	protected void initLayout() {
		mMapImage = (ImageView) findViewById(R.id.fipmc_map);
	}

	public void loadMap(final double lat, final double lng) {
		// this helps display the correct size image, but also make sure that it is only done once
		if(mHeight == 0 || mWidth == 0) {

			mGuess = mEstimate.getEstimate();

			if(mGuess.x > 0 && mGuess.y > 0) {
				String mapUrl = FeedUtil.buildStaticMapUrl(lat, lng, mGuess.x, mGuess.y);
				Kahlo.with(getContext()).source(mapUrl)
						.targetSize(mGuess.x, mGuess.y)
						.placeholderResId(R.drawable.transparent_box)
						.display(mMapImage);
			}

			mMapImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

				@Override
				public void onGlobalLayout() {
					mHeight = mMapImage.getHeight();
					mWidth = mMapImage.getWidth();
					mMapImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);

					if(Math.abs(mGuess.y - mHeight) > HEIGHT_ERROR_MARGIN || Math.abs(mGuess.y - mWidth) > WIDTH_ERROR_MARGIN) {
						//depending on screen density the estimate can be slightly off but still acceptable
						Print.log("Guesses", mGuess.y, mGuess.x, "Actual", mHeight, mWidth);
						loadMap(lat, lng);
					}
				}

			});
		}
		else {
			String mapUrl = FeedUtil.buildStaticMapUrl(lat, lng, mWidth, mHeight);
			Kahlo.with(getContext()).source(mapUrl)
					.targetSize(mWidth, mHeight)
					.placeholderResId(R.drawable.transparent_box)
					.display(mMapImage);
		}
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.aaf_item_play_my_city;
	}

}
