package com.bandsintown.activityfeed;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bandsintown.activityfeed.objects.SizeEstimate;
import com.bandsintown.activityfeed.util.FeedImageLoader;

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

//        if(mHeight == 0 && mWidth == 0) {
//
//            mGuess = mEstimate.getEstimate();
//
//            if(mGuess.x > 0 && mGuess.y > 0)
//                ImageProvider.getInstance(getContext()).displayBigImage(url, mTourImage, mGuess.x, mGuess.y,
//                        ImageProvider.activityFeedImageDisplayer(getContext()));
//
//            mTourImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//                @Override
//                public void onGlobalLayout() {
//                    mHeight = mTourImage.getHeight();
//                    mWidth = mTourImage.getWidth();
//                    mTourImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//
//                    if(Math.abs(mGuess.y - mHeight) > HEIGHT_ERROR_MARGIN || Math.abs(mGuess.x - mWidth) > WIDTH_ERROR_MARGIN) {
//                        //depending on screen density the estimate can be slightly off but still acceptable
//                        Logger.log("Guesses", mGuess.y, mGuess.x, "Actual", mHeight, mWidth, url);
//                        setImage(url);
//                    }
//                }
//
//            });
//        }
//        else
//            ImageProvider.getInstance(getContext()).displayBigImage(url, mTourImage, mWidth, mHeight,
//                    ImageProvider.activityFeedImageDisplayer(getContext()));

        mGuess = mEstimate.getEstimate();
        FeedImageLoader.load(getContext(), mTourImage, url, mWidth, mHeight, mGuess, new FeedImageLoader.OnHeightAndWidthSetListener() {

            @Override
            public void onWidthAndHeightChanged(int width, int height) {
                //if setImage gets called again we will have these values already
                mHeight = height;
                mWidth = width;
            }

        });
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
