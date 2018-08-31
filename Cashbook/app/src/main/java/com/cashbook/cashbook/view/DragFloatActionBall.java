package com.cashbook.cashbook.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.bumptech.glide.Glide;

/**
 * @author  luruiqian on 2018/7/25.
 */

public class DragFloatActionBall extends FloatingActionButton {
    private int parentHeight;
    private int parentWidth;
    //小球停靠边 0：左  1：右
    private int mBallStopSide = 0;

    private Context mContext;

    public DragFloatActionBall(Context context) {
        super(context);
        init(context);
    }

    public DragFloatActionBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragFloatActionBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        this.setScaleType(ScaleType.CENTER);
        setImageUrl();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                autoHideAnim();
            }
        }, 1500);
    }

    private void setImageUrl() {
        Glide.with(mContext).load("http://img10.gomein.net.cn/image/prodimg/gicon/cat10000049.png").into(this);
    }

    private int lastX;
    private int lastY;

    private boolean isDrag;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                setPressed(true);
                isDrag = false;
                lastX = rawX;
                lastY = rawY;
                ViewGroup parent;
                if (getParent() != null) {
                    parent = (ViewGroup) getParent();
                    parentHeight = parent.getHeight();
                    parentWidth = parent.getWidth();
                }
                getParent().requestDisallowInterceptTouchEvent(false);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (parentHeight <= 0 || parentWidth == 0) {
                    isDrag = false;
                    break;
                } else {
                    isDrag = true;
                }
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                //这里修复一些华为手机无法触发点击事件
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                if (distance == 0) {
                    isDrag = false;
                    break;
                }
                float x = getX() + dx;
                float y = getY() + dy;
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > parentWidth - getWidth() ? parentWidth - getWidth() : x;
                y = getY() < 0 ? 0 : getY() + getHeight() > parentHeight ? parentHeight - getHeight() : y;
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;
                if (x > 0 && x < parentWidth - getWidth()) {
                    this.setAlpha(1.0f);
                }
                Log.i("aa", "isDrag=" + isDrag + "getX=" + getX() + ";getY=" + getY() + ";parentWidth=" + parentWidth);
                return true;
            case MotionEvent.ACTION_UP:
                if (!isNotDrag()) {
                    //恢复按压效果
                    setPressed(false);
                    if (rawX >= parentWidth / 2 && rawX < parentWidth - 10) {
                        //靠右吸附
                        animate().setInterpolator(new DecelerateInterpolator())
                                .setDuration(500)
                                .xBy(parentWidth - getWidth() - getX())
                                .start();
                        mBallStopSide = 1;
                    } else if (rawX >= parentWidth - 10) {
                        rightHideHalf();
                    } else if (rawX <= 10) {
                        leftHideHalf();
                    } else if (rawX > 10 && rawX < parentWidth / 2) {
                        //靠左吸附
                        ObjectAnimator oa = ObjectAnimator.ofFloat(this, "translationX", getX(), 0);
                        oa.setInterpolator(new DecelerateInterpolator());
                        oa.setDuration(500);
                        oa.start();
                        mBallStopSide = 0;
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        autoHideAnim();
                    }
                }, 1000);
                return true;
            default:
                break;
        }
        //如果是拖拽则消s耗事件，否则正常传递即可。
        return !isNotDrag() || super.onTouchEvent(event);
    }

    private void autoHideAnim() {
        if (mBallStopSide == 1) {
            rightHideHalf();
        } else if (mBallStopSide == 0) {
            leftHideHalf();
        }
    }

    private boolean isNotDrag() {
        return !isDrag && (getX() == 0
                || (getX() == parentWidth - getWidth()));
    }

    /**
     * 向右消失的动画
     */
    public void rightHideHalf() {
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(this, "translationX", getX(), parentWidth - getWidth() / 2);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(this, "alpha", 1, 0.5f);

        AnimatorSet aniSet = new AnimatorSet();
        aniSet.setDuration(300);
        aniSet.setInterpolator(new BounceInterpolator());
        aniSet.playTogether(oa1,
                oa2);// 同时启动2个动画
        aniSet.start();
    }

    /**
     * 向左消失的动画
     */
    public void leftHideHalf() {
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(this, "translationX", getX(), -getWidth() / 2);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(this, "alpha", 1, 0.5f);

        AnimatorSet aniSet = new AnimatorSet();
        aniSet.setDuration(300);
        aniSet.setInterpolator(new BounceInterpolator());
        aniSet.playTogether(oa1,
                oa2);// 同时启动2个动画
        aniSet.start();
    }

    private void recoverLight() {
        ObjectAnimator oa = ObjectAnimator.ofFloat(this, "alpha", 0.5f, 1);
        oa.start();
    }

}
