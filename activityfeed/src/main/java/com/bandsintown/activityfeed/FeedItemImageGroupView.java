package com.bandsintown.activityfeed;

import android.content.Context;
import android.graphics.Point;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bandsintown.activityfeed.image.ImageProvider;
import com.bandsintown.activityfeed.interfaces.OnItemClickOfTypeAtIndex;

import java.util.ArrayList;
import java.util.Collections;

public class FeedItemImageGroupView extends AbsFeedItemGroupView {

	private ImageView[] mImageViews;
	private View mMoreImageGroup;
	private TextView mMoreCount;
	private TextView mMessage;

	private static final float FULL = 1f;
	private static final float HALF = .5f;
	private static final float THIRD = .33333333f;

	public FeedItemImageGroupView(Context context) {
		super(context);
	}

	public FeedItemImageGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FeedItemImageGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void initLayout() {
		mMessage = (TextView) findViewById(R.id.fiigs_message);

		mImageViews = new ImageView[6];

		mImageViews[0] = (ImageView) findViewById(R.id.one);
		mImageViews[1] = (ImageView) findViewById(R.id.two);
		mImageViews[2] = (ImageView) findViewById(R.id.three);
		mImageViews[3] = (ImageView) findViewById(R.id.four);
		mImageViews[4] = (ImageView) findViewById(R.id.five);
		mImageViews[5] = (ImageView) findViewById(R.id.six);

		mMoreImageGroup = findViewById(R.id.more_image_group);
		mMoreCount = (TextView) findViewById(R.id.more_count);
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.feed_item_image_group_six;
	}

	public void loadImages(Context activity, ArrayList<String> urls, final OnItemClickOfTypeAtIndex listener) {
		for(ImageView iv : mImageViews)
			iv.setVisibility(GONE);

		mMoreImageGroup.setVisibility(GONE);

		//TODO maybe do a clever for loop to remove the ones that don't have urls instead of setting
		//TODO them all to gone first like i did above, idk if that makes anything faster

		//Since the mapping of all image views don't match URLs, we do a new array with mapping
		final ArrayList<ImageView> usedImageViews = new ArrayList<>();
		boolean usedMoreImage = false;

		switch(urls.size()) {
			case 1 :
				//TODO shouldn't happen
				break;
			case 2 :
				usedImageViews.add(mImageViews[0]);
				usedImageViews.add(mImageViews[1]);

				setupTwoImages(usedImageViews);
				break;
			case 3 :
				usedImageViews.add(mImageViews[0]);
				usedImageViews.add(mImageViews[3]);
				usedImageViews.add(mImageViews[4]);

				setupThreeImages(usedImageViews);
				break;
			case 4 :
				usedImageViews.add(mImageViews[0]);
				usedImageViews.add(mImageViews[1]);
				usedImageViews.add(mImageViews[3]);
				usedImageViews.add(mImageViews[4]);

				setupFourImages(usedImageViews);
				break;
			case 5 :
				usedImageViews.add(mImageViews[0]);
				usedImageViews.add(mImageViews[1]);
				usedImageViews.add(mImageViews[3]);
				usedImageViews.add(mImageViews[4]);
				usedImageViews.add(mImageViews[5]);

				setupFiveImages(usedImageViews);
				break;
			case 6 :
				Collections.addAll(usedImageViews, mImageViews);

				setupSixImages(usedImageViews);
				break;
			default :
				usedImageViews.add(mImageViews[0]);
				usedImageViews.add(mImageViews[1]);
				usedImageViews.add(mImageViews[2]);
				usedImageViews.add(mImageViews[3]);
				usedImageViews.add(mImageViews[4]);

				setUpSevenOrMoreImages(usedImageViews, urls.size());
				usedMoreImage = true;
				break;
		}

		//Make the used image views visible, and set click listener
		for(final ImageView iv : usedImageViews) {
			iv.setVisibility(VISIBLE);
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(listener != null)
						listener.onItemClick(0, usedImageViews.indexOf(iv), null);
				}

			});
		}

		if(usedMoreImage)
			mMoreImageGroup.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(listener != null && !usedImageViews.isEmpty())
						listener.onItemClick(0, usedImageViews.size(), null);
				}

			});

		requestLayout();

		ArrayList<Point> sizes = calculateSizes(activity, urls.size());

		//Add the images and check for null
		for(int i = 0; i < urls.size() && i < usedImageViews.size(); i++) {
			if(urls.get(i) != null)
				ImageProvider.getInstance(getContext()).displayListImage(urls.get(i), usedImageViews.get(i), sizes.get(i).x, sizes.get(i).y, R.drawable.placeholder_artist_small);
			else
				usedImageViews.get(i).setImageResource(R.drawable.placeholder_artist_small);
		}
	}

	private void setUpSevenOrMoreImages(ArrayList<ImageView> usedImageViews, int totalCount) {
		setupSixImages(usedImageViews);

		mMoreImageGroup.setVisibility(VISIBLE);
		mMoreCount.setText(String.format("+ %s", totalCount - usedImageViews.size()));
	}

	private void setupSixImages(ArrayList<ImageView> usedImageViews) {
		for(ImageView iv : usedImageViews) {
			PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) iv.getLayoutParams();
			params.getPercentLayoutInfo().widthPercent = THIRD;
			params.getPercentLayoutInfo().heightPercent = HALF;
		}
	}

	private void setupFiveImages(ArrayList<ImageView> usedImageViews) {
		for(int i = 0; i < usedImageViews.size(); i++) {
			PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) usedImageViews.get(i).getLayoutParams();
			if(i < 2)
				params.getPercentLayoutInfo().widthPercent = HALF;
			else
				params.getPercentLayoutInfo().widthPercent = THIRD;

			params.getPercentLayoutInfo().heightPercent = HALF;
		}
	}

	private void setupFourImages(ArrayList<ImageView> usedImageViews) {
		for(ImageView iv : usedImageViews) {
			PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) iv.getLayoutParams();
			params.getPercentLayoutInfo().widthPercent = HALF;
			params.getPercentLayoutInfo().heightPercent = HALF;
		}
	}

	private void setupThreeImages(ArrayList<ImageView> usedImageViews) {
		for(int i = 0; i < usedImageViews.size(); i++) {
			PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) usedImageViews.get(i).getLayoutParams();
			if(i == 0)
				params.getPercentLayoutInfo().widthPercent = FULL;
			else
				params.getPercentLayoutInfo().widthPercent = HALF;

			params.getPercentLayoutInfo().heightPercent = HALF;
		}
	}

	private void setupTwoImages(ArrayList<ImageView> usedImageViews) {
		for(ImageView iv : usedImageViews) {
			PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) iv.getLayoutParams();
			params.getPercentLayoutInfo().widthPercent = HALF;
			params.getPercentLayoutInfo().heightPercent = FULL;
		}
	}

	private ArrayList<Point> calculateSizes(Context context, int numItems) {

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int availableWidth = metrics.widthPixels - (getResources().getDimensionPixelSize(R.dimen.recylcer_view_margin_adjustment) * 2);
		int availableHeight = (int) (availableWidth / getResources().getFraction(R.fraction.group_image_post_ratio, 1, 1)); //Enforced 16:9 ratio

		ArrayList<Point> sizes = new ArrayList<>();

		switch(numItems) {
			case 1 :
				//Shouldn't happen
				break;
			case 2 :
				Point splitVertical = new Point(availableWidth / 2, availableHeight);

				sizes.add(splitVertical);
				sizes.add(splitVertical);
				break;
			case 3 : {
					Point topRow = new Point(availableWidth, availableHeight / 2);
					Point bottomRow = new Point(availableWidth / 2, availableHeight / 2);

					sizes.add(topRow);
					sizes.add(bottomRow);
					sizes.add(bottomRow);
				}
				break;
			case 4 :
				Point quarters = new Point(availableWidth / 2, availableHeight / 2);

				sizes.add(quarters);
				sizes.add(quarters);
				sizes.add(quarters);
				sizes.add(quarters);
				break;
			case 5 : {
					Point topRow = new Point(availableWidth / 2, availableHeight / 2);
					Point bottomRow = new Point(availableWidth / 3, availableHeight / 2);

					sizes.add(topRow);
					sizes.add(topRow);
					sizes.add(bottomRow);
					sizes.add(bottomRow);
					sizes.add(bottomRow);
				}
				break;
			default : //Six or more
				Point six = new Point(availableWidth / 3, availableHeight / 2);

				sizes.add(six);
				sizes.add(six);
				sizes.add(six);
				sizes.add(six);
				sizes.add(six);
				sizes.add(six);
				break;
		}

		return sizes;
	}

	public void setMessage(String message) {
		mMessage.setVisibility(VISIBLE);
		mMessage.setText(message);
	}

	public void setMessageGone() {
		mMessage.setVisibility(GONE);
	}

}
