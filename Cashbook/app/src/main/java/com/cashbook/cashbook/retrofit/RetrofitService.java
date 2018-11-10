package com.cashbook.cashbook.retrofit;

import retrofit2.Retrofit;

public class RetrofitService {
    public <T> T getService(Class<T> service){
        Retrofit.Builder builder = this.getRetrofitBuilder();
        builder.baseUrl(this.base_url);
        builder.client(OkHttpClientFactory.newHttpsV2());
        return builder.build().create(service);
    }
}
