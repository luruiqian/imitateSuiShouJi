package com.cashbook.cashbook.flow.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.adapter.AddRecordOutPayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddRecordZhichuFragment extends Fragment {
    private static String pageName;

    private RecyclerView mAddRecordOutPayRy;
    private List<String> mList = new ArrayList<>();

    private static AddRecordZhichuFragment mTemplateFragment;

    public AddRecordZhichuFragment() {
    }

    public static AddRecordZhichuFragment newInstance(String name) {
        pageName = name;

        if (mTemplateFragment == null) {
            mTemplateFragment = new AddRecordZhichuFragment();
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
        return inflater.inflate(R.layout.fragment_add_record_zhichu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        setData();
    }

    private void setData() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false) {

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

        };
        //设置
        mAddRecordOutPayRy.setLayoutManager(manager);
        //实例化适配器
        AddRecordOutPayAdapter addRecordOutPayAdapter = new AddRecordOutPayAdapter(getActivity(), mList);
        //设置适配器
        mAddRecordOutPayRy.setAdapter(addRecordOutPayAdapter);
    }

    private void initData() {
        for (int i = 0; i < 15; i++) {
            mList.add(getRandomStr("数据"));
        }
    }

    //将文本长度改变
    private String getRandomStr(String str) {
        Random random = new Random();
        int num = random.nextInt(10) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            builder.append(str + "");
        }
        return builder.toString();
    }

    private void initView(View view) {
        mAddRecordOutPayRy = (RecyclerView) view.findViewById(R.id.add_record_out_pay_ry);
        //设置不可滑动
        mAddRecordOutPayRy.setNestedScrollingEnabled(false);
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
