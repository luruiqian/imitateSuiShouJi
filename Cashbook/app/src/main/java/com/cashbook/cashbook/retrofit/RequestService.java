package com.cashbook.cashbook.retrofit;

import com.cashbook.cashbook.retrofit.bean.TestRequest;

import retrofit2.Call;
import retrofit2.http.POST;

public interface RequestService {
    /**
     * 实验网络请求
     * @return
     */
    @POST("")
    Call<TestRequest> testRequest();
}
