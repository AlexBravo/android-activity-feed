package com.bandsintown.activityfeed;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.objects.SizeEstimate;
import com.bandsintown.activityfeed.util.Print;

/**
 * Created by rjaylward on 12/10/15 for Bandsintown
 */
public class FeedItemSingleWatchTrailer extends AbsFeedItemSingleView {

    private ImageView mTourImage;
    private ImageView mPlayButton;
    private TextView mTitle;
    private TextView mDescription;

    private int mHeight;
    private int mWidth;

    private Point mGuess;
    private SizeEstimate mEstimate;

    protected static final int WIDTH_ERROR_MARGIN = 4;
    protected static final int HEIGHT_ERROR_MARGIN = 2;

    public FeedItemSingleWatchTrailer(Context context, SizeEstimate imageSizeEstimate) {
        super(context);

        mEstimate = imageSizeEstimate;
    }

//    protected Point calculateImageViewSizeGuess() {
//        float fraction = getResources().getFraction(R.fraction.sixteen_by_nine, 1, 1);
//        float horizontalMargin = getResources().getDimension(R.dimen.activity_feed_card_horizontal_margin);
//
//        //this assumes feed fills the screen, if we change the layout we need to change this
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//
//        int mWidthGuess = (int) (size.x - 2 * horizontalMargin);
//        int mHeightGuess = (int) (mWidthGuess / fraction);
//
//        return new Point(mWidthGuess, mHeightGuess);
//    }
//
//    protected Point tabletImageViewSizeGuess() {
//        float fraction = getResources().getFraction(R.fraction.sixteen_by_nine, 1, 1);
//        float horizontalMargin = getResources().getDimension(R.dimen.activity_feed_card_horizontal_margin);
//
//        int mTableMainFeedWidthGuess = 0;
//        int mTableMainFeedHeightGuess = 0;
//
//        if(getResources().getBoolean(R.bool.isTablet)) {
//            mTableMainFeedWidthGuess = (int) (getResources().getDimension(R.dimen.feed_width) - horizontalMargin * 2);
//            mTableMainFeedHeightGuess = (int) (mTableMainFeedWidthGuess / fraction);
//        }
//
//        return new Point(mTableMainFeedWidthGuess, mTableMainFeedHeightGuess);
//    }

    @Override
    protected void initLayout() {
        mTourImage = (ImageView) findViewById(R.id.wttfi_image);
        mTitle = (TextView) findViewById(R.id.wttfi_title);
        mDescription = (TextView) findViewById(R.id.wttfi_desc);
        mPlayButton = (ImageView) findViewById(R.id.wttfi_play_button);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aaf_item_tour_trailer;
    }

    public void setImage(final String url) {
        // this helps display the correct size image, but also make sure that it is only done once

        if(mHeight == 0 && mWidth == 0) {

            mGuess = mEstimate.getEstimate();

            if(mGuess.x > 0 && mGuess.y > 0)
                ImageProvider.getInstance(getContext()).displayBigImage(url, mTourImage, mGuess.x, mGuess.y,
                        ImageProvider.activityFeedImageDisplayer(getContext()));

            mTourImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    mHeight = mTourImage.getHeight();
                    mWidth = mTourImage.getWidth();
                    mTourImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    if(Math.abs(mGuess.y - mHeight) > HEIGHT_ERROR_MARGIN || Math.abs(mGuess.x - mWidth) > WIDTH_ERROR_MARGIN) {
                        //depending on screen density the estimate can be slightly off but still acceptable
                        Print.log("Guesses", mGuess.y, mGuess.x, "Actual", mHeight, mWidth, url);
                        setImage(url);
                    }
                }

            });
        }
        else
            ImageProvider.getInstance(getContext()).displayBigImage(url, mTourImage, mWidth, mHeight,
                    ImageProvider.activityFeedImageDisplayer(getContext()));
    }

    public void setObjectTitle(String title) {
        mTitle.setText(title);
    }

    public void setObjectDescription(String description) {
        mDescription.setText(description);
    }

    public void setOnImageClickListener(View.OnClickListener listener) {
        mTourImage.setOnClickListener(listener);
    }

    public void setOnPlayButtonClickListener(View.OnClickListener listener) {
        mPlayButton.setOnClickListener(listener);
    }

    public void setDefaultImage(int resId) {
        mTourImage.setImageResource(resId);
        mTourImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

}
