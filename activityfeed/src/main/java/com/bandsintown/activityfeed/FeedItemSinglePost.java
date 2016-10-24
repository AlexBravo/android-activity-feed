package com.bandsintown.activityfeed;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.interfaces.OnLinkClickListener;
import com.bandsintown.activityfeed.objects.SizeEstimate;

import me.saket.bettermovementmethod.BetterLinkMovementMethod;

public class FeedItemSinglePost extends AbsFeedItemSingleView {

	private ImageView mImage;
	private TextView mMessage;
	protected SizeEstimate mEstimate;

	public FeedItemSinglePost(Context context, @Nullable SizeEstimate imageViewSize) {
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
		mImage = (ImageView) findViewById(R.id.fir_image);
		mMessage = (TextView) findViewById(R.id.fir_message);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.aaf_item_user_post;
	}

	public void setImage(String url) {
		if(url != null) {
			ImageProvider.getInstance(getContext()).displayImageDirect(url, mImage,
					ImageProvider.activityFeedUserPostDisplayer(getContext(), getResources().getDisplayMetrics().widthPixels), null);
			mImage.setVisibility(VISIBLE);

			final int width = mEstimate.getEstimate().x;
			mImage.setVisibility(VISIBLE);
			ImageProvider.activityFeedUserPostDisplayer(getContext(), width)
					.source(url)
					.display(mImage);
		}

	}

	public void setImageGone() {
		mImage.setVisibility(GONE);
	}

	public void setMessage(String message) {
		if(message != null) {
			mMessage.setText(message);
			mMessage.setVisibility(VISIBLE);
		}
		else
			mMessage.setVisibility(GONE);
	}

	public void setMessageLinksClickable(boolean clickable, @Nullable OnLinkClickListener listener) {
		if(clickable) {
			BetterLinkMovementMethod method = BetterLinkMovementMethod.newInstance();
			method.setOnLinkClickListener(listener);
			mMessage.setMovementMethod(method);
			Linkify.addLinks(mMessage, Linkify.ALL);
		}
	}

}
