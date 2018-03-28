package com.cashbook.cashbook.flow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.flow.adapter.FlowRecordAdapter;
import com.cashbook.cashbook.flow.bean.AccountInfo;

import java.util.ArrayList;
import java.util.List;

public class FlowFragment extends Fragment {
    private List<AccountInfo> accountList;
    private ListView mRecordListView;
    private FlowRecordAdapter flowRecordAdapter;

    /**
     * onCreateView是创建的时候调用，onViewCreated是在onCreateView后被触发的事件
     **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flow, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
        setData();
    }

    private void setData() {
        flowRecordAdapter = new FlowRecordAdapter(getActivity(), accountList);
        mRecordListView.setAdapter(flowRecordAdapter);
    }

    private void initData() {
        accountList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.time = "本周";
            accountInfo.expend = "50.00";
            accountInfo.income = "100.00";
            accountInfo.recordSubscribe = "这是本月的财务情况";
            accountList.add(accountInfo);
        }
    }

    private void initView(View view) {
        mRecordListView = (ListView) view.findViewById(R.id.flow_record_lv);
    }
}
