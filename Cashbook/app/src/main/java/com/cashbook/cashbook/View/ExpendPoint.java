package com.cashbook.cashbook.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cashbook.cashbook.R;

public class ExpendPoint extends View {
    private Paint mPaint = new Paint();
    private float mPercent;
    private float mBigPointRadius;
    private float mSmallPointRadius;

    public ExpendPoint(Context context) {
        super(context);
    }

    public ExpendPoint(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpendPoint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, defStyleAttr);
    }

    private void init(Context context, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(defStyleAttr, R.styleable.expendPoint);
        mBigPointRadius = a.getFloat(R.styleable.expendPoint_bigPointRadius, 15);
        mSmallPointRadius = a.getFloat(R.styleable.expendPoint_smallPointRadius, 5);
        mPaint.setColor(a.getColor(R.styleable.expendPoint_pointColor, Color.GRAY));
    }

    public void setPercent(float percent) {
        this.mPercent = percent;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaint.setAlpha((int) mPercent);
        if (mPercent < 0.5) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBigPointRadius * mPercent, mPaint);
        } else {
            float afterPercent = (mPercent - 0.5f) / 0.5f;
            float radius = mBigPointRadius - mBigPointRadius / 2 * afterPercent;
           canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
           canvas.drawCircle(getWidth() / 2 - afterPercent * mBigPointRadius, getHeight() / 2, mSmallPointRadius, mPaint);
           canvas.drawCircle(getWidth() / 2 + afterPercent * mBigPointRadius, getHeight() / 2, mSmallPointRadius, mPaint);
        }
    }
}
