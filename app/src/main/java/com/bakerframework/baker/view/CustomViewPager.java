package com.bakerframework.baker.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by appleapple on 10/21/15.
 */
public class CustomViewPager extends ViewPager {

    private GestureDetector gestureDetector;
    private int rightTouch, leftTouch;

    private boolean isSwipeEnabled = true;
    private boolean isEnabled = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        leftTouch = w / 5;
        rightTouch = w - leftTouch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (!isEnabled) {
            return false;
        }

        if (!isSwipeEnabled) {

            float rX = event.getX();
            if (rX >= 0 && rX <= leftTouch) {
                boolean b = super.onInterceptTouchEvent(event);
                if (b) {
                    return true;
                }
            }

            if (rX <= getWidth() && rX >= rightTouch) {
                boolean b = super.onInterceptTouchEvent(event);
                if (b) {
                    return true;
                }
            }
        }

        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(event);
        }

        return super.onInterceptTouchEvent(event);
    }

    public void setOnGestureListener(GestureDetector.OnGestureListener listener) {
        gestureDetector = new GestureDetector(getContext(), listener);
    }

    public boolean isSwipeEnabled() {
        return isSwipeEnabled;
    }

    public void setSwipeEnabled(boolean isSwipeEnabled) {
        this.isSwipeEnabled = isSwipeEnabled;
    }

    public void setScrollEnabled(boolean t) {
        isEnabled = t;
    }

    @Override
    protected boolean canScroll(View view, boolean checkV, int dx, int x, int y) {
        if (view instanceof CustomWebView) {
            return ((CustomWebView) view).canScrollHorizontal(-dx);
        } else {
            return super.canScroll(view, checkV, dx, x, y);
        }
    }

}
