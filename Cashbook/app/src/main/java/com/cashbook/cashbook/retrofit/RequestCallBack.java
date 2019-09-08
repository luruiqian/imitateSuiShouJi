package com.cashbook.cashbook.retrofit;

import android.app.Activity;

import com.cashbook.cashbook.retrofit.constant.RetrofitConstant;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RequestCallBack<T extends BaseResponse> implements Callback<T> {
    public static final int SUCCESS_CODE = 0;
    private Activity mActivity;

    protected RequestCallBack(Activity activity) {
        this.mActivity = activity;
    }

    public abstract void success(T t);

    public abstract void failure(int statusCode);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            success(response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            //提示超时
            failure(RetrofitConstant.TIMEOUT);
        } else if (t instanceof ConnectException) {
            //连接错误
            failure(RetrofitConstant.CONNECT_EXCEPTION);
        } else if (t instanceof UnknownError) {
            //未找到主机
            failure(RetrofitConstant.UNKNOWN_ERROR);
        } else {
            //其他错误
            failure(RetrofitConstant.OTHER_ERROR);
        }
    }
    //封装后的网络请求调用方法
//ApiClient.getInstance().getApiService().searchRecommend().enqueue(new RequestCallback<RecommendMessage>(this){
//        @Override
//        public void success(RecommendMessage response) {
//            //处理网络请求成功
//            if (response.getStatus() == SUCCESS_CODE) {
//                Log.i(TAG, "onResponse: " + response.body().toString());
//            } else {
//                ToastUtil.showShortToast(this, baseResponse.getMessage());
//            }
//        }
//
//        @Override
//        public void failure(int statusCode, ApiErrorModel apiErrorModel) {
//            //处理网络连接错误
//        }
//
//}
}
