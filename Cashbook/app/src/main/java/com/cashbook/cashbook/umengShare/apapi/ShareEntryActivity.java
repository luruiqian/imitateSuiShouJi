package com.cashbook.cashbook.umengShare.apapi;

import android.os.Bundle;

import com.cashbook.cashbook.R;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class ShareEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
    }
}
