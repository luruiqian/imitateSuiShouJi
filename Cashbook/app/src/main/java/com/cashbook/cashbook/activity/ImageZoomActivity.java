package com.cashbook.cashbook.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cashbook.cashbook.R;
import com.cashbook.cashbook.flow.view.ClipZoomImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageZoomActivity extends Activity {
    private ClipZoomImageView mClipZoomImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);
        init();
    }

    private void init() {
        mClipZoomImageView = findViewById(R.id.iv_clip_zoom);
        mClipZoomImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("longClick----",v.toString());
                return false;
            }
        });
//        Glide.with(this).load("").asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                mClipZoomImageView.setImageBitmap(resource);
//            }
//        });
//        ImageLoadUtils.imageLoadWithoutScale(mContext, mClipZoomImageView, "https://testjoylearning.bmw.com.cn/upload/img/b0d03dcf5240450bbf8e70be365f2154.jpg", R.drawable.icon_default_image);
//        mClipZoomImageView.setImageBitmap(returnBitmap("https://testjoylearning.bmw.com.cn/upload/img/b0d03dcf5240450bbf8e70be365f2154.jpg"));
        Glide.with(this)
                .load("https://testjoylearning.bmw.com.cn/upload/img/b0d03dcf5240450bbf8e70be365f2154.jpg")
                .fitCenter()
                .into(mClipZoomImageView);
    }

//    @Override
//    public boolean onLongClick(View v) {
////        DialogUtil.showBottomDialog(mContext, new AvatarDialogConfig(ImageZoomActivity.this));
//Log.i("longClick----",v.toString());
//        return true;
//    }

    public Bitmap returnBitmap(final String url) {
        Bitmap[] bitmap = new Bitmap[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap[0] = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Log.i("bitmap--------", bitmap[0].toString());
        return bitmap[0];
    }

//    public class AvatarDialogConfig extends DialogUtil.CommonDialogConfig {
//
//        Activity mactivity;
//
//        AvatarDialogConfig(Activity activity) {
//            mactivity = activity;
//        }
//
//        @Override
//        public int getDialogLayout() {
//            return R.layout.dialog_image_zoom;
//        }
//
//
//        @OnClick({R.id.tv_save_image, R.id.cancel})
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.tv_save_image:
////                    FileUtils.saveBitmap()
//                    String APPDIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/bwm/cache/";
//                    mClipZoomImageView.setDrawingCacheEnabled(true);
//                    Bitmap bitmap = mClipZoomImageView.getDrawingCache();
//                    mClipZoomImageView.setDrawingCacheEnabled(false);
//                    String output_path = APPDIR + System.currentTimeMillis() + ".jpg";
//
//                    ImageUtils.savePhotoToSDCard(bitmap, output_path);
//
//                    break;
//            }
//            dismissDialog();
//        }
//    }
}