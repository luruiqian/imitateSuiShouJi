package com.cashbook.cashbook.flow.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.adapter.MyBrowseItemAdapter;
import com.cashbook.cashbook.flow.bean.MyBrowseRequest;
import com.cashbook.cashbook.flow.bean.MyFootPrintBean;
import com.cashbook.cashbook.retrofit.RequestService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddRecordShouruFragment extends Fragment {
    private ListView mIncomeLv;
    private ArrayList<MyFootPrintBean> mBrowseList = new ArrayList<>();
    private MyBrowseItemAdapter mMyBrowseItemAdapter;

    private static AddRecordShouruFragment mTemplateFragment;

    public AddRecordShouruFragment() {
    }

    public static AddRecordShouruFragment newInstance(String name) {
        if (mTemplateFragment == null) {
            mTemplateFragment = new AddRecordShouruFragment();
        }
        return mTemplateFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_record_shouru, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        getData();
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mobile.gome.com.cn/") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();
        RequestService service = retrofit.create(RequestService.class);
        MyBrowseRequest body = new MyBrowseRequest();
        body.areaCode = "1001";
        body.pageSize = 20;
        body.currentPage = 1;
        Call<MyFootPrintBean> call = service.getCall();
        call.enqueue(new Callback<MyFootPrintBean>() {
            @Override
            public void onResponse(Call<MyFootPrintBean> call, Response<MyFootPrintBean> response) {
                Log.d("收入", "我的足迹接口请求成功");
                if (response != null && response.body() != null && response.body().footPrints != null && response.body().footPrints.size() > 0) {
                    mBrowseList.addAll(response.body().footPrints);
                    mMyBrowseItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MyFootPrintBean> call, Throwable t) {
                Log.d("收入", "我的足迹接口请求失败");
            }
        });
    }

    private void initView(View view) {
        mIncomeLv = (ListView) view.findViewById(R.id.add_record_income_lv);
        mMyBrowseItemAdapter = new MyBrowseItemAdapter(getActivity(), mBrowseList);
        mIncomeLv.setAdapter(mMyBrowseItemAdapter);
        View footerView = LayoutInflater.from(getActivity()).inflate(R.layout.footerview,null);
        mIncomeLv.addFooterView(footerView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
