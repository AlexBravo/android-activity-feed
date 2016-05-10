package com.bandsintown.activityfeed.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bandsintown.activityfeed.R;

/**
 * Created by rjaylward on 8/26/15 for Bandsintown
 */
public class LoadMoreViewHolder extends RecyclerView.ViewHolder{

	private ProgressBar mProgressBar;
	private TextView mLoading;

	public LoadMoreViewHolder(View itemView) {
		super(itemView);

		mLoading = (TextView) itemView.findViewById(R.id.llm_searching_text_view);
		mProgressBar = (ProgressBar) itemView.findViewById(R.id.llm_progress);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	public void cantLoadMore() {
		mProgressBar.setVisibility(View.GONE);
		mLoading.setText(itemView.getResources().getString(R.string.error_try_again_later));
	}

}
