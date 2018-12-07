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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.view.SketchView;

public class AddRecordTransferAccountsFragment extends Fragment implements SketchView.OnDrawChangedListener{
    private SketchView mSketchView;
    private Bitmap mBitmap;
    private Bitmap mUpBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private float mStartX;
    private float mStartY;
    private int mScreenWidth;
    private int mScreenHeight;

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
//        initDraw();
//        initGesture();
    }

    private void initView(View view) {
        mSketchView = (SketchView) view.findViewById(R.id.add_record_trans_account_sv);

        mSketchView.setOnDrawChangedListener(this);
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

    @Override
    public void onDrawChanged() {
// Undo
        if (mSketchView.getPaths().size() > 0){

//            setAlpha(undo, 1f);
        }
//        else
//            setAlpha(undo, 0.4f);
//        // Redo
//        if (mSketchView.getUndoneCount() > 0)
//            setAlpha(redo, 1f);
//        else
//            setAlpha(redo, 0.4f);
    }
}
