package wang.yuchao.android.library.util.custom;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 固定速度的Scroller
 */
public class FixedSpeedScroller extends Scroller {

    private int mDuration = 500;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public int getDurationFixed() {
        return mDuration;
    }

    public void setDurationFixed(int duration) {
        this.mDuration = duration;
    }

}