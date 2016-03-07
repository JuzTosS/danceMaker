package com.juztoss.dancemaker.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Kirill on 3/7/2016.
 */
public class SwipeLayout extends com.daimajia.swipe.SwipeLayout {

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getOpenStatus()==Status.Close && event.getActionMasked() == MotionEvent.ACTION_DOWN && event.getX() < getWidth() / 2)
            return false;
        else
            return super.onTouchEvent(event);
    }
}
