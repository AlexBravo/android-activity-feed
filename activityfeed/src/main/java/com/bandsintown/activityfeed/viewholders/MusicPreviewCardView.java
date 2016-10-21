package com.bandsintown.activityfeed.viewholders;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.audio.MediaControls;
import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.interfaces.OnItemClickOfTypeAtIndex;

/**
 * Created by rjaylward on 10/19/16
 */

public class MusicPreviewCardView extends CardView implements MediaControls {

    public MusicPreviewCardView(Context context) {
        this(context, null);
        init();
    }

    public MusicPreviewCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private ImageView mArtistImage;
    private Button mPlay;
    private TextView mTitle;
    private TextView mSubtitle;
    private ProgressBar mProgressBar;

    private OnItemClickOfTypeAtIndex mListener;
    private int mMainClick;
    private int mImageClick;
    private int mIndex = 0;

    public static final String MEDIA_CONTROL_STATE = "media_control_state";
    private int mCurrentMediaState = -1;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.aaf_view_music_preview_card, this, true);

        mArtistImage = (ImageView) findViewById(R.id.mpcv_artist_image);
        mPlay = (Button) findViewById(R.id.mpcv_play_button);
        mTitle = (TextView) findViewById(R.id.mpcv_title);
        mSubtitle = (TextView) findViewById(R.id.mpcv_subtitle);
        mProgressBar = (ProgressBar) findViewById(R.id.mpcv_progress);

        setBackgroundResource(R.drawable.clickable_listitem);
        setClickable(true);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            setElevation(getResources().getDimension(R.dimen.list_item_elevation));

        mPlay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.onItemClick(mImageClick, mIndex, getMediaInfoBundle());
            }

        });

        mArtistImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.onItemClick(mImageClick, mIndex, null);
            }

        });

        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.onItemClick(mMainClick, mIndex, null);
            }

        });
    }

    private Bundle getMediaInfoBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(MEDIA_CONTROL_STATE, mCurrentMediaState);
        return bundle;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public void setImage(ImageProvider provider, String imageUrl, int errorImageResId) {
        if(imageUrl != null && provider != null)
            provider.displayListImage(imageUrl, mArtistImage, errorImageResId);
        else
            mArtistImage.setImageResource(errorImageResId);
    }

    public void setText(String title, String subtitle) {
        mTitle.setText(title);
        mSubtitle.setText(subtitle);
    }

    public void setPlayButtonVisibility(int visibility) {
        mPlay.setVisibility(visibility);
    }

    /**
     * Allows you to add a single listener and listen for both event types
     *
     * @param listener
     * @param mainClickType return type for main body clicks
     * @param imageClickType return type for image body clicks or play button clicks
     */
    public void setOnClickOfTypeAtListener(OnItemClickOfTypeAtIndex listener, int mainClickType, int imageClickType) {
        mListener = listener;
        mMainClick = mainClickType;
        mImageClick = imageClickType;
    }

    @Override
    public void setMediaControlsState(int state) {
        mCurrentMediaState = state;

        switch(state) {
            case PlaybackStateCompat.STATE_PLAYING :
                mPlay.setVisibility(VISIBLE);
                mPlay.setBackgroundResource(android.R.drawable.ic_media_pause);
                mProgressBar.setVisibility(GONE);
                break;
            case PlaybackStateCompat.STATE_BUFFERING :
            case PlaybackStateCompat.STATE_CONNECTING :
                mPlay.setVisibility(INVISIBLE);
                mProgressBar.setVisibility(VISIBLE);
                break;
            default:
                mPlay.setVisibility(VISIBLE);
                mPlay.setBackgroundResource(android.R.drawable.ic_media_play);
                mProgressBar.setVisibility(GONE);
                break;
        }
    }
}
