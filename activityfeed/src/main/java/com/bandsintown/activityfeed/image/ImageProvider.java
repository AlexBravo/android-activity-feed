package com.bandsintown.activityfeed.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v8.renderscript.RenderScript;
import android.widget.ImageView;

import com.bandsintown.activityfeed.R;
import com.bandsintown.kahlo.BitmapHelper;
import com.bandsintown.kahlo.image.callback.BitImageCallback;
import com.bandsintown.kahlo.image.provider.BitImgProvider;
import com.bandsintown.kahlo.image.transformation.BitTransformation;
import com.bandsintown.kahlo.image.transformation.BlurTransformation;
import com.bandsintown.kahlo.image.transformation.MaxWidthTransformation;

/**
 * Created by rjaylward on 5/3/16 for Bandsintown
 */
public class ImageProvider {

    private Context mContext;
    private Point mScreenSize;

    private static ImageProvider instance;

    public static ImageProvider getInstance(Context context) {
        if(instance == null)
            instance = new ImageProvider(context.getApplicationContext());

        return instance;
    }

    //Do not add any callbacks to these without checking everything since ordering is important for those
    public static BitImgProvider.Displayer selfSizingDisplayer(Context context) {
        return BitImgProvider.with(context)
                .config(Bitmap.Config.RGB_565)
                .selfSize()
                .placeholderResId(R.drawable.transparent_box);
    }

    public static BitImgProvider.Displayer activityFeedUserPostDisplayer(Context context, int maxWidth) {
        return BitImgProvider.with(context)
                .config(Bitmap.Config.RGB_565)
                .placeholderResId(R.drawable.transparent_box)
                .addTransformation(new MaxWidthTransformation(context, maxWidth));
    }

    public static BitImgProvider.Displayer activityFeedImageDisplayer(Context context) {
        return BitImgProvider.with(context)
                .config(Bitmap.Config.RGB_565)
                .placeholderResId(R.drawable.transparent_box);
    }

    public static BitImgProvider.Displayer activityFeedBlurImageDisplayer(Context context, RenderScript rs, ImageView iv) {
        return BitImgProvider.with(context)
                .config(Bitmap.Config.ARGB_8888)
                .placeholderResId(R.drawable.transparent_box)
                .addTransformation(new BlurTransformation(context, rs, iv));
    }

    public static BitImgProvider.Displayer blurImageDisplayer(Context context, RenderScript rs, ImageView iv) {
        return BitImgProvider.with(context)
                .config(Bitmap.Config.ARGB_8888)
                .addTransformation(new BlurTransformation(context, rs, iv));
    }

    public static BitImgProvider.Displayer cloudCardImageDisplayer(Context context) {
        return BitImgProvider.with(context)
                .config(Bitmap.Config.RGB_565)
                .errorResId(R.drawable.placeholder_big_image);
    }

    public static BitImgProvider.Displayer listImageDisplayer(Context context) {
        return BitImgProvider.with(context)
                .config(Bitmap.Config.RGB_565)
                .placeholderResId(R.drawable.transparent_box);
    }

    public ImageProvider(Context context) {
        mContext = context;

        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;

        mScreenSize = new Point(width, height);
    }

    /**
     * Helper method for displaying a big image using UIL.
     *
     * Just give it the uri and the view and it does the rest with regards to displayer and listeners
     *
     * @param imageUri the uri of the image to display
     * @param iv the imageView
     */

    public void displayBigImage(String imageUri, ImageView iv, boolean isMultipaneLayout) {
        displayBigImage(imageUri, iv, isMultipaneLayout, null);
    }

    public void displayBigImage(String imageUri, ImageView iv, boolean isMultipaneLayout, BitImgProvider.Displayer displayer) {
        int width;
        if(isMultipaneLayout) {
            //Calculate width by taking right % of screen width

            //Seems complicated but is the way to do it right so changes to these values don't fuck this up
            float contentWeight = mContext.getResources().getInteger(R.integer.landscape_content_weight);
            float listWeight = mContext.getResources().getInteger(R.integer.landscape_list_weight);

            float scale = contentWeight / (contentWeight + listWeight);

            width = (int) ((mScreenSize.x) * scale);
        }
        else
            width = mScreenSize.x;

        float height = mContext.getResources().getDimension(R.dimen.toolbar_backdrop_height);

        displayBigImage(imageUri, iv, width, height, displayer);
    }

    public void displayBigImage(String imageUri, ImageView iv, float width, float height, BitImgProvider.Displayer displayer) {
        displayImage(imageUri, iv, width, height, null, new FadeInCallback(imageUri, iv), displayer);
    }

    /**
     * Helper class which uses the default person image size
     *
     * @param imageUri the uri of the image to display
     * @param iv the imageView
     */
    public void displayPersonImage(String imageUri, final ImageView iv) {
        displayPersonImage(imageUri, iv, iv.getLayoutParams().width, iv.getLayoutParams().height);
    }

    public void displayPersonImage(String imageUri, int placeHolderResId, ImageView iv) {
        displayPersonImage(imageUri, iv, iv.getLayoutParams().width, iv.getLayoutParams().height, placeHolderResId);
    }

    /**
     * Displays an image used inside a person object
     *
     * @param imageUri the uri of the image to display
     * @param iv the imageView
     * @param desiredWidth the width you want the matrix to use
     * @param desiredHeight the height you want the matrix to use
     */
    public void displayPersonImage(final String imageUri, final ImageView iv, final float desiredWidth,
                                   final float desiredHeight, final int placeHolderResId) {

        if(imageUri == null) {
            systemDisplayPersonImage(placeHolderResId, iv);
            return;
        }

        BitImgProvider.with(mContext)
                .source(imageUri)
                .targetSize((int) desiredWidth, (int) desiredHeight)
                .noDefaultAnimations()
                .circle()
                .placeholderResId(R.drawable.transparent_circle)
                .callback(new RoundedUserImageCallback(this, placeHolderResId, imageUri, iv))
                .display(iv);
    }

    public void displayPersonImage(String imageUri, final ImageView iv, final float desiredWidth, final float desiredHeight) {
        displayPersonImage(imageUri, iv, desiredWidth, desiredHeight, 0);
    }

    public void displayImage(String imageUri, ImageView iv) {
        displayImage(imageUri, iv, iv.getLayoutParams().width, iv.getLayoutParams().height);
    }

    public void displayImage(String imageUri, final ImageView iv, final float desiredWidth, final float desiredHeight) {
        displayImage(imageUri, iv, desiredWidth, desiredHeight, null, new FadeInCallback(imageUri, iv), null);
    }

    public void displayListImage(String imageUri, final ImageView iv) {
        displayImage(imageUri, iv, iv.getLayoutParams().width, iv.getLayoutParams().height, null,
                new FadeInCallback(imageUri, iv), ImageProvider.listImageDisplayer(mContext));
    }

    public void displayListImage(String imageUri, ImageView iv, @DrawableRes int errorResId) {
        displayImage(imageUri, iv, iv.getLayoutParams().width, iv.getLayoutParams().height, null, new FadeInCallback(imageUri, iv), errorResId > 0 ?
                ImageProvider.listImageDisplayer(mContext).errorResId(errorResId).errorMatrixCenterTop() : ImageProvider.listImageDisplayer(mContext));
    }

    public void displayListImage(String imageUri, ImageView iv, int desiredWidth, int desiredHeight, @DrawableRes int errorResId) {
        displayImage(imageUri, iv, desiredWidth > 0 ? desiredWidth : iv.getLayoutParams().width, desiredHeight > 0 ? desiredHeight : iv.getLayoutParams().height, null,
                new FadeInCallback(imageUri, iv), errorResId > 0 ?
                        ImageProvider.listImageDisplayer(mContext).errorResId(errorResId).errorMatrixCenterTop() : ImageProvider.listImageDisplayer(mContext));
    }

    /**
     * Helper method for displaying a big image using UIL.
     *
     * Just give it the uri and the view and it does the rest with regards to displayer and listeners.
     * This method allows you to specify a desired width and height as well.
     *
     * @param imageUri the uri of the image to display
     * @param iv the imageView
     * @param desiredWidth the width you want the matrix to use
     * @param desiredHeight the height you want the matrix to use
     * @param displayer the image displayer to use when loading, if null falls back to default
     */
    public void displayImage(final String imageUri, final ImageView iv, final float desiredWidth, final float desiredHeight,
                             final BitTransformation transformation, final BitImageCallback listener, BitImgProvider.Displayer displayer) {
        if(displayer == null)
            displayer = BitImgProvider.with(mContext).noDefaultAnimations().config(Bitmap.Config.RGB_565);

        displayer.source(imageUri)
                .targetSize((int) desiredWidth, (int) desiredHeight)
                .addTransformation(transformation)
                .noDefaultAnimations()
                .matrixCenterTop()
                .targetSize((int)(desiredWidth > 0 ? desiredWidth : mScreenSize.x), (int)(desiredHeight > 0 ? desiredHeight : mScreenSize.y))
                .callback(listener)
                .display(iv);
    }

    public void displayImageDirect(final String imageUri, final ImageView iv, @Nullable BitImgProvider.Displayer displayer,
                                   @Nullable final BitImageCallback listener) {
        if(displayer == null)
            displayer = BitImgProvider.with(mContext).noDefaultAnimations().config(Bitmap.Config.RGB_565);

        displayer.callback(listener).source(imageUri).display(iv);
    }

    public void displayBlurImageInActivityFeed(String imageUri, final ImageView iv, RenderScript renderScript, int width, int height) {
        displayImage(imageUri, iv, width, height, null, new FadeInCallback(imageUri, iv),
                activityFeedBlurImageDisplayer(iv.getContext(), renderScript, iv));
    }

    public void systemDisplayPersonImage(int resId, ImageView iv) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resId);
        iv.setImageBitmap(BitmapHelper.getRoundBitmap(bitmap));
    }
}
