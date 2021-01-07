package com.cashbook.cashbook.flow.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

class ZoomImageView extends ClipZoomImageView {
    private GestureDetector mGestureDetector;
    public ZoomImageView(Context context) {
        super(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGestureDetector = new GestureDetector(null);
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(),"长按",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
