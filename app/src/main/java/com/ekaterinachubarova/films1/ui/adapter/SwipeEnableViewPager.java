package com.ekaterinachubarova.films1.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ekaterinachubarova on 26.09.16.
 */

public class SwipeEnableViewPager extends ViewPager {

    @Setter
    @Getter
    private boolean swipeEnabled = true;

    public SwipeEnableViewPager(Context context) {
        this(context, null);
    }

    public SwipeEnableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        try {
            return swipeEnabled && super.onInterceptTouchEvent(arg0);
        } catch (Exception e) {
            return swipeEnabled;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        try {
            return swipeEnabled && super.onTouchEvent(arg0);
        } catch (Exception e) {
            return swipeEnabled;
        }
    }
}
