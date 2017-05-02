package com.bandsintown.activityfeed.util;

import android.content.Context;
import android.graphics.Point;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bandsintown.activityfeed.image.ImageProvider;

/**
 * Created by rjaylward on 12/8/16
 */

public class FeedImageLoader {

    public static final int WIDTH_ERROR_MARGIN = 4;
    public static final int HEIGHT_ERROR_MARGIN = 2;

    public static void load(final Context context, final ImageView imageView, final String url, final int mWidth, final int mHeight,
                            final Point mGuess, final OnHeightAndWidthSetListener listener) {
        if(mHeight == 0 && mWidth == 0) {

            if(mGuess.x > 0 && mGuess.y > 0)
                ImageProvider.getInstance(context).displayBigImage(url, imageView, mGuess.x, mGuess.y,
                        ImageProvider.activityFeedImageDisplayer(context));

            imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    int ivHeight = imageView.getHeight();
                    int ivWidth = imageView.getWidth();

                    if(listener != null)
                        listener.onWidthAndHeightChanged(ivWidth, ivHeight);

                    imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    if(Math.abs(mGuess.y - mHeight) > HEIGHT_ERROR_MARGIN || Math.abs(mGuess.x - mWidth) > WIDTH_ERROR_MARGIN) {
                        //depending on screen density the estimate can be slightly off but still acceptable
                        Logger.log("Guesses", mGuess.y, mGuess.x, "Actual", mHeight, mWidth, url);

                        load(context, imageView, url, ivWidth, ivHeight, mGuess, listener);
                    }
                }

            });
        }
        else
            ImageProvider.getInstance(context).displayBigImage(url, imageView, mWidth, mHeight,
                    ImageProvider.activityFeedImageDisplayer(context));
    }

    public interface OnHeightAndWidthSetListener {
        void onWidthAndHeightChanged(int width, int height);
    }
}
