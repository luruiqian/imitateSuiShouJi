package com.cashbook.cashbook.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {



    @Override
    public void onCreate() {
        Log.i("servicetest","onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i("servicetest","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("servicetest","onBind");

        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("servicetest","onUnbind");

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("servicetest","onDestroy");
        super.onDestroy();

    }
    public void method(){
        Log.i("servicetest","method");

    }
}
