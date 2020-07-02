package com.cashbook.cashbook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by luruiqian on 2018/3/22.
 */

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getName();
    public static ArrayList<Activity> activityList;
    public static MyApplication myApplication = null;

    //要保证每个Activity中引用的都是同一个application，因此用到了单例模式
    public static MyApplication getInstance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
            activityList = new ArrayList<>();
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
        Fresco.initialize(this);
        initUMengSdk();
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//
//    }

    private void initUMengSdk() {
        //友盟统计（崩溃）
        UMConfigure.init(this, "5efc5135dbc2ec078c8134ca", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        //友盟统计push
        UMConfigure.init(this, "5efc5135dbc2ec078c8134ca", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "8b027260319dda507dce77063e1e555f");

        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG,"注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG,"注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
    }

    //添加到数组中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /*退出应用，结束所有activity*/
    public void exitApp() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
