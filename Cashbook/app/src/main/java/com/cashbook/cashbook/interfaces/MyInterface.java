package com.cashbook.cashbook.interfaces;

import com.cashbook.cashbook.bean.MyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyInterface {
    @GET(".../...")
    Call<List<MyResponse>> getCall();
}
