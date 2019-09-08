package com.cashbook.cashbook.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.utils.GsonHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MoreFragmentcccc extends Fragment {
    static final int str = 20;
    private Map<Integer,String> map = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e("skdjfaksfdask------", str + "");
        asyGetData();
    }

    //异步get请求
    private void asyGetData() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(500, TimeUnit.SECONDS).build();
//        LoginBean loginBean = new LoginBean();
//        loginBean.deviceId = "de701fa86c145d32b27930039879d921";
//        loginBean.jpushId = "140fe1da9e83f2fc047";
//        loginBean.password = "7a47a7dad08a1de638664619c92b9365";
//        loginBean.phone = "18500061603";
//        loginBean.umengId = "abc123";

        Map<String,String> params = new HashMap<>();
        params.put("deviceId","de701fa86c145d32b27930039879d921");
        params.put("jpushId","140fe1da9e83f2fc047");
        params.put("password","7a47a7dad08a1de638664619c92b9365");
        params.put("phone","18500061603");
        params.put("umengId","abc123");

        Gson gson = new Gson();
        String json = GsonHelper.toString(params);
        //请求body
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Headers headers = new Headers.Builder()
                .add("Content-Length", "144")
                .add("Accept-Encoding", "gzip")
                .add("UserAgent", "okhttp/3.11.0")
                .add("Connection", "keep-alive")
                .build();
        Request request = new Request.Builder()
                .url("http://www.reebo.cn/train-student/0100/login")
                .post(body)
                .headers(headers)
                .post(body).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("okhttp", "000000000000000000000");

            }

            @Override
            public void onResponse(Call call, Response response){
                Log.i("okhttp", "11111111111111111111");

                Log.i("okhttp", response.body().toString());

                if (response.isSuccessful()) {
                }
            }
        });

    }







    }

