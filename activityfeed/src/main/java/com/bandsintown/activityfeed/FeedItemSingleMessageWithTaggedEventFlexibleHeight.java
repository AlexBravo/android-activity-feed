package com.bandsintown.activityfeed;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.objects.SizeEstimate;
import com.bandsintown.activityfeed.util.Logger;

/**
 * Created by rjaylward on 12/9/15 for Bandsintown
 */
public class FeedItemSingleMessageWithTaggedEventFlexibleHeight extends FeedItemSingleMessageWithTaggedEvent {

    private ImageView mBigImageView;

    public FeedItemSingleMessageWithTaggedEventFlexibleHeight(Context context, SizeEstimate imageSizeEstimate) {
        super(context, imageSizeEstimate);
    }

    @Override
    protected void initLayout() {
        super.initLayout();
        mBigImageView = (ImageView) findViewById(R.id.afibi_big_image);
        mEventImageView = (ImageView) findViewById(R.id.afibi_event_image);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aaf_item_message_flexible_height_post;
    }

    @Override
    public void setOnImageClickListener(OnClickListener listener) {
        super.setOnImageClickListener(listener);
        mBigImageView.setOnClickListener(listener);
    }

    @Override
    public void setDefaultImage(int resId) {
        mEventImageView.setVisibility(VISIBLE);
        mBigImageView.setVisibility(GONE);

        mEventImageView.setImageResource(resId);
        mEventImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public void setImage(final AppCompatActivity context, final String url, boolean isUserImage) {
        if(context != null && url != null) {
            if(isUserImage) {
                mImageSection.setVisibility(VISIBLE);
                mEventImageView.setVisibility(GONE);
                mBigImageView.setVisibility(VISIBLE);
                final int width = mEstimate.getEstimate().x;
                Logger.log("Set Image Called", url);
                Logger.log(url, "Setting image");
                ImageProvider.activityFeedUserPostDisplayer(getContext(), width)
                        .source(url)
                        .display(mBigImageView);

            }
            else
                setEventImage(url);
        }
    }

    public void setEventImage(final String url) {
        mImageSection.setVisibility(VISIBLE);
        mEventImageView.setVisibility(VISIBLE);
        mBigImageView.setVisibility(GONE);

        if(mHeight == 0 && mWidth == 0) {

            mGuess = mEstimate.getEstimate();

            if(mGuess.x > 0 && mGuess.y > 0) {
                ImageProvider.getInstance(getContext()).displayBigImage(url, mEventImageView, mGuess.x, mGuess.y,
                        ImageProvider.activityFeedImageDisplayer(getContext()));
            }

            mEventImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    mEventImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mWidth = mEventImageView.getWidth();
                    mHeight = mEventImageView.getHeight();

                    mEventImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    if(Math.abs(mGuess.y - mHeight) > HEIGHT_ERROR_MARGIN || Math.abs(mGuess.x - mWidth) > WIDTH_ERROR_MARGIN) {
                        //depending on screen density the estimate can be slightly off but still acceptable
                        Logger.log("Guesses", mGuess.y, mGuess.x, "Actual", mHeight, mWidth, url);
                        setEventImage(url);
                    }
                }

            });
        }
        else {
            ImageProvider.getInstance(getContext()).displayBigImage(url, mEventImageView, mWidth, mHeight,
                    ImageProvider.activityFeedImageDisplayer(getContext()));
        }

    }

}