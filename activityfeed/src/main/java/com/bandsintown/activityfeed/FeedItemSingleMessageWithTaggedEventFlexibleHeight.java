package com.bandsintown.activityfeed;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.objects.SizeEstimate;
import com.bandsintown.activityfeed.util.Print;

/**
 * Created by rjaylward on 12/9/15 for Bandsintown
 */
public class FeedItemSingleMessageWithTaggedEventFlexibleHeight extends FeedItemSingleMessageWithTaggedEvent {

    private RelativeLayout mEventImageLayout;
    private ImageView mEventImageView;

    public FeedItemSingleMessageWithTaggedEventFlexibleHeight(Context context, SizeEstimate imageSizeEstimage) {
        super(context, imageSizeEstimage);
    }

    @Override
    protected void initLayout() {
        super.initLayout();

        mEventImageLayout = (RelativeLayout) findViewById(R.id.afibi_event_image_layout);
        mEventImageView = (ImageView) findViewById(R.id.afibi_event_image);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.feed_item_message_flexible_height_post;
    }

    @Override
    public void setOnImageClickListener(OnClickListener listener) {
        super.setOnImageClickListener(listener);
        mEventImageView.setOnClickListener(listener);
    }

    @Override
    public void setDefaultImage(int resId) {
        mEventImageLayout.setVisibility(VISIBLE);
        mBigImage.setVisibility(GONE);

        mEventImageView.setImageResource(resId);
        mEventImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public void setImage(final AppCompatActivity context, final String url, boolean isUserImage) {
        if(context != null && url != null) {
            if(isUserImage) {
                mEventImageLayout.setVisibility(GONE);
                mBigImage.setVisibility(VISIBLE);
                ImageProvider.getInstance(getContext()).displayImageDirect(url, mBigImage,
                        ImageProvider.activityFeedUserPostDisplayer(getContext(),
                                context.getResources().getDisplayMetrics().widthPixels), null);
            }
            else
                setEventImage(url);
        }
    }

    public void setEventImage(final String url) {
        mEventImageLayout.setVisibility(VISIBLE);
        mBigImage.setVisibility(GONE);

        if(mHeight == 0 && mWidth == 0) {

            mGuess = mEstimate.getEstimate();

            if(mGuess.x > 0 && mGuess.y > 0) {
                ImageProvider.getInstance(getContext()).displayBigImage(url, mEventImageView, mGuess.x, mGuess.y,
                        ImageProvider.activityFeedImageDisplayer(getContext()));
                mEventSection.setVisibility(VISIBLE);
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
                        Print.log("Guesses", mGuess.y, mGuess.x, "Actual", mHeight, mWidth, url);
                        setEventImage(url);
                    }
                }

            });
        }
        else {
            ImageProvider.getInstance(getContext()).displayBigImage(url, mEventImageView, mWidth, mHeight,
                    ImageProvider.activityFeedImageDisplayer(getContext()));
            mEventSection.setVisibility(VISIBLE);
        }

    }
}