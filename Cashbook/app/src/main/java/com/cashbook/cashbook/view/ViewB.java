package com.cashbook.cashbook.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class ViewB extends AppCompatTextView {
    public ViewB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("事件分发","ViewB----dispatchTouchEvent");

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("事件分发","ViewB----onTouchEvent");

        return super.onTouchEvent(event);
    }
}
