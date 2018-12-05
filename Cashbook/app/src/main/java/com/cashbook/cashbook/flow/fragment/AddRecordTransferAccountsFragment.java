package com.cashbook.cashbook.flow.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cashbook.cashbook.R;

public class AddRecordTransferAccountsFragment extends Fragment {
    private ImageView mImageView;
    private Bitmap mBitmap;
    private Bitmap mUpBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private float mStartX;
    private float mStartY;

    private static AddRecordTransferAccountsFragment mTemplateFragment;

    public AddRecordTransferAccountsFragment() {
    }

    public static AddRecordTransferAccountsFragment newInstance(String name) {

        if (mTemplateFragment == null) {
            mTemplateFragment = new AddRecordTransferAccountsFragment();
        }
        return mTemplateFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_record_transfer_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initDraw();
        initGesture();
    }

    private void initView(View view) {
        mImageView = (ImageView) view.findViewById(R.id.add_record_transaccount_iv);
    }

    private void initDraw() {
        //加载原图
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.av);
        //创建白纸，宽、高、图片参数
        mUpBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
        //创建画板
        mCanvas = new Canvas(mUpBitmap);
        //创建画笔
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        //在纸上作画
        mCanvas.drawBitmap(mBitmap, new Matrix(), mPaint);
    }

    private void initGesture() {
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float currentX;
                float currentY;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("event", "手指按下");
                        //获取手指按下的位置
                        mStartX = event.getRawX() - v.getTranslationX();
                        mStartY = event.getRawY() - v.getTranslationY();
                        Log.i("event", "currentX = " + event.getX());
                        Log.i("event", "currentY = " + event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("event", "手指移动");
                        currentX = event.getRawX() - v.getTranslationX();
                        currentY = event.getRawY() - v.getTranslationY();
                        Log.i("event", "currentX = " + event.getRawX());
                        Log.i("event", "currentY = " + event.getRawY());
                        //在背景图上作画
                        mCanvas.drawLine(mStartX, mStartY, currentX, currentY, mPaint);
                        mStartX = currentX;
                        mStartY = currentY;
                        mImageView.setImageBitmap(mUpBitmap);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("event", "手指抬起");
                        break;
                }
                return true;
            }
        });
    }


    //点击改变颜色（红色）
    public void red(View view) {
        mPaint.setColor(Color.RED);
    }

    //点击改变颜色（绿色）
    public void green(View view) {
        mPaint.setColor(Color.GREEN);
    }

    //点击刷子（及变粗）
    public void brush(View view) {
        mPaint.setStrokeWidth(10);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
