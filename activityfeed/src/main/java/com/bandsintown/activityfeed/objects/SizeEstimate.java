package com.bandsintown.activityfeed.objects;

import android.graphics.Point;

/**
 * Created by rjaylward on 5/9/16 for Bandsintown
 */
public abstract class SizeEstimate {

    private Point mEstimate;
    private Point mTabletEstimate;

    public SizeEstimate() {
        mEstimate = estimateSize();
        mTabletEstimate = estimateTabletSize();

        if(mEstimate == null)
            mEstimate = new Point(0, 0);
        if(mTabletEstimate == null)
            mTabletEstimate = mEstimate;
    }

    protected abstract Point estimateSize();
    protected abstract Point estimateTabletSize();
    protected abstract boolean useTabletEstimate();

    public Point getEstimate() {
        return useTabletEstimate() ? mTabletEstimate : mEstimate;
    }

}
