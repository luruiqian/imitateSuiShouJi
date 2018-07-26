package com.cashbook.cashbook.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by luruiqian on 2018/7/26.
 */

public class StateLine extends View {
    private Paint mProgressBarPaint;//画进度条画笔
    private Paint mLinePaint;//线画笔
    private Paint mCirclePaint;
    private String[] data = {"已发货", "运输中", "派件中", "已签收"};
    private int pointPosition;//最新的点的位置
    private String startPositionText;//出发地
    private String arrivePositionText;//目的地
    private int selectedCircleColor = Color.RED;
    private int selectedLineColor = Color.RED;

    public StateLine(Context context) {
        super(context);
        init();
    }

    public StateLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StateLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //线画笔
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(2);
        //进度条画笔
        mProgressBarPaint = new Paint();
        mProgressBarPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (MeasureSpec.EXACTLY == widthMode) {
            width = widthSize;
        } else {
            width = dpToPx(200);
            if (MeasureSpec.AT_MOST == widthMode) {
                width = Math.min(width, widthSize);
            }
        }
        if (MeasureSpec.EXACTLY == heightMode) {
            height = heightSize;
        } else {
            height = dpToPx(45);
            if (MeasureSpec.AT_MOST == heightMode) {
                height = Math.min(height, heightSize);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth() / data.length;
        //绘制圆圈和线
        for (int i = 0; i < data.length; i++) {
            mLinePaint.setColor(selectedLineColor);
                canvas.drawLine(15 + getPaddingLeft() + i * width, 5 + getPaddingTop(),
                        getPaddingLeft() + (i + 1) * width - 5, 5 + getPaddingTop(), mLinePaint);
        }
        //重置画布状态(恢复到上次save时的状态,即没有translate时的状态)
        canvas.restore();
        canvas.save();
        //再向下平移55px,即在圆圈和线的下方55px处绘制底部文字
        canvas.translate(0, 55);
        //绘制底部文字
//        drawBottomText(canvas, data.length, width);
        //再次把画布的状态重置
        canvas.restore();
    }

    /**
     * dp转px
     */
    public int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dp, getContext().getResources().getDisplayMetrics());
    }
}
