package com.cashbook.cashbook;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by luruiqian on 2018/3/22.
 */

public class MyApplication extends Application {
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
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
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
