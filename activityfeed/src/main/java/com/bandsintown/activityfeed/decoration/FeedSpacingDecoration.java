package com.bandsintown.activityfeed.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class FeedSpacingDecoration extends RecyclerView.ItemDecoration {

    private int mHorizontalPadding;
    private int mVerticalPadding;

    public FeedSpacingDecoration(int horizontalPadding, int verticalPadding) {
        mHorizontalPadding = horizontalPadding;
        mVerticalPadding = verticalPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = mVerticalPadding;
        outRect.left = mHorizontalPadding;
        outRect.right = mHorizontalPadding;

        if(parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1)
            outRect.bottom = mVerticalPadding;
    }

    public int getHorizontalPadding() {
        return mHorizontalPadding;
    }

    public int getVerticalPadding() {
        return mVerticalPadding;
    }
}
