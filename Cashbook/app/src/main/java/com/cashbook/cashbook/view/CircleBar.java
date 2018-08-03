package com.cashbook.cashbook.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.cashbook.cashbook.R;


public class CircleBar extends View {
    private static final String TAG = "CircleBar";

    private RectF mColorWheelRectangle = new RectF();//圆圈的矩形范围
    private Paint mDefaultWheelPaint;////绘制底部灰色圆圈的画笔
    private Paint mColorWheelPaint;//绘制蓝色扇形的画笔
    private Paint textDesPaint; //标题（第1讲）
    private Paint mEllipsisPaint;//描述超过6个字以后的省略
    private TextPaint textPaint;////详情的描述
    private float mColorWheelRadius;
    private float circleStrokeWidth;//圆圈的线条粗细
    private float pressExtraStrokeWidth = 0.0f;
    private int mTextColor = getResources().getColor(R.color.black_gray_color);//默认文字颜色
    private int mWheelColor = getResources().getColor(R.color.color_333333);//默认圆环颜色

    private String mTitle = "第4讲";//文字标题（第1讲）
    private String mTextDes = "一元一次方程哈哈";//标题描述
    private String mEllipsisText = "......";//标题描述超过6个字以后省略号
    private int mTextDesSize;//描述文字的大小
    private int mCount;//为了做动画
    private float mSweepAnglePer;//扇形弧度百分比
    private float mSweepAngle;//扇形弧度
    private int mTextSize;//文字大小
    private int mDistance;// 上下文字的距离

    public CircleBar(Context context) {
        super(context);
        init(null, 0);
    }

    public CircleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    public void setmSweepAnglePer(float mSweepAnglePer) {
        this.mSweepAnglePer = mSweepAnglePer;
        postInvalidate();
    }

    private void init(AttributeSet attrs, int defStyle) {
        //初始化一些值
        circleStrokeWidth = dip2px(getContext(), 11);
        //pressExtraStrokeWidth = dip2px(getContext(), 2);
        mTextSize = dip2px(getContext(), 20);
        mTextDesSize = dip2px(getContext(), 26);
        mDistance = dip2px(getContext(), 30);//文字距离
        //外圆环的画笔
        mColorWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorWheelPaint.setColor(mWheelColor);
        mColorWheelPaint.setStyle(Paint.Style.STROKE);
        mColorWheelPaint.setStrokeWidth(circleStrokeWidth);//圆圈的线条粗细
        mColorWheelPaint.setStrokeCap(Paint.Cap.ROUND);//开启显示边缘为圆形
        //默认圆的画笔
        mDefaultWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultWheelPaint.setColor(getResources().getColor(R.color.color_333333));
        mDefaultWheelPaint.setStyle(Paint.Style.STROKE);
        mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);//圆圈的线条粗细
        mDefaultWheelPaint.setStrokeCap(Paint.Cap.ROUND);//开启显示边缘为圆形
        //标题（第1讲）
        textDesPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textDesPaint.setColor(getResources().getColor(R.color.black_gray_color));
        textDesPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textDesPaint.setTextSize(mTextDesSize);
        textDesPaint.setTextAlign(Paint.Align.LEFT);
        //详情的描述
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setColor(mTextColor);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(mTextSize);
        //......
        mEllipsisPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        mEllipsisPaint.setColor(mTextColor);
        mEllipsisPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mEllipsisPaint.setTextAlign(Paint.Align.LEFT);
        mEllipsisPaint.setTextSize(mTextSize);

        mSweepAngle = 80;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mColorWheelRectangle, -240, 300, false, mDefaultWheelPaint);//画外接的圆环
        canvas.drawArc(mColorWheelRectangle, -240, mSweepAnglePer, false, mColorWheelPaint);//画圆环
        String tempTextDes = mTextDes.length() > 6 ? mTextDes.substring(0, 6) : mTextDes;
        if (mTextDes.length() > 6) {
            mTextDes = mTextDes.substring(0, 6) + "......";
        }
        Rect bounds = new Rect();
        textPaint.getTextBounds(mTextDes, 0, 0, bounds);

        // drawText各个属性的意思(文字,x坐标,y坐标,画笔)
        //标题的描述
        canvas.drawText(mTitle,
                (mColorWheelRectangle.centerX())
                        - (textDesPaint.measureText(mTitle) / 2),
                mColorWheelRectangle.centerY() + bounds.height() / 2 - mDistance + 10
                , textDesPaint);
        //详情的描述
//        StaticLayout sl = new StaticLayout(mTextDes, textPaint, tempTextDes.length() * dip2px(getContext(),30), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        StaticLayout sl = new StaticLayout(mTextDes, textPaint, tempTextDes.length() * sp2px(getContext(), 20), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate((mColorWheelRectangle.centerX())
                        - (textPaint.measureText(tempTextDes) / 2),
                mColorWheelRectangle.centerY() + bounds.height() / 2);
        sl.draw(canvas);
        canvas.restore();
//        if (mTextDes.length() > 6) {
//            canvas.drawText(
//                    mEllipsisText,
//                    (mColorWheelRectangle.centerX()) - (textPaint.measureText(mEllipsisText) / 2),
//                    mColorWheelRectangle.centerY() + bounds.height() / 2 + mDistance,
//                    mEllipsisPaint
//            );
//        }
//        StaticLayout layout = new StaticLayout(mTextDes, textPaint, 208, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
//        canvas.save();
//        canvas.translate((mColorWheelRectangle.centerX()) - (textPaint.measureText(mTextDes) / 2) + 30,
//                mColorWheelRectangle.centerY() + bounds.height() / 2 - 10);
//        layout.draw(canvas);
//        canvas.restore();//别忘了restore
//        textDesPaint.getTextBounds(mTitle, 0, mTitle.length(), bounds);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(getSuggestedMinimumHeight(),
                heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        mColorWheelRadius = min - circleStrokeWidth;

        mColorWheelRectangle.set(circleStrokeWidth, circleStrokeWidth + pressExtraStrokeWidth,
                mColorWheelRadius + 8, mColorWheelRadius);
    }


//    @Override
//    public void setPressed(boolean pressed) {
//        //Toast.makeText(getContext(), mText, Toast.LENGTH_SHORT).show();
//    }


    public void setText(String text) {
        if (text.length() > 6) {
            mTextDes = text.substring(0, 6) + " ......";
        } else {
            mTextDes = text;
        }
        postInvalidate();
    }

    public void setDesText(String text) {
        mTitle = text;
        postInvalidate();
    }

    public void setTextColor(int color) {
        mTextColor = color;
        textPaint.setColor(mTextColor);
    }

    public void setSweepAngle(float sweepAngle) {
        mSweepAngle = sweepAngle;
    }

    public void setWheelColor(int color) {
        this.mColorWheelPaint.setColor(color);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * sp转换成px
     */
    public int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}