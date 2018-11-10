package com.cashbook.cashbook.retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetRequestInterface {
    /**
     * 用于接收网络请求数据的方法
     */
    @GET("5bd2b1735bb17c4b7a456cc4/test")
    Call<Body> getCall();

    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型 除 {@link
     * okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part}
     * 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<Body> fileUpload(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    /**
     * 是一个表单形式的请求（Content-Type:application/x-www-form-urlencoded）
     * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
     */
    @POST("/form")
    @FormUrlEncoded
    Call<Body> formUrlEncode(@Field("userName") String name, @Field("age") int age);
}
