package com.cashbook.cashbook.flow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.flow.adapter.FlowRecordAdapter;
import com.cashbook.cashbook.flow.bean.AccountInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FlowFragment extends Fragment implements View.OnClickListener {
    private FlowRecordAdapter flowRecordAdapter;
    private List<AccountInfo> accountList;
    private ListView mRecordListView;
    private TextView mTitleYear;
    private TextView mTitleMonth;
    private TextView mMonthIncome;
    private TextView mMonthPay;
    private TextView mMonthBudget;
    private ImageView mEyesIv;
    private int year;
    private int month;
    private boolean isOpenEye = true;

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
        setDate();
        flowRecordAdapter = new FlowRecordAdapter(getActivity(), accountList);
        mRecordListView.setAdapter(flowRecordAdapter);
        mEyesIv.setOnClickListener(this);
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        mTitleMonth.setText(month + "");
        mTitleYear.setText(year + "");
    }

    private void initData() {
        accountList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.time = "本周";
            accountInfo.expend = "50.00";
            accountInfo.income = "100.00";
            accountInfo.recordSubscribe = "这是本月的财务情况";
            accountInfo.isShowMoney = true;
            accountList.add(accountInfo);
        }
    }

    private void initView(View view) {
        mRecordListView = (ListView) view.findViewById(R.id.flow_record_lv);
        mTitleMonth = (TextView) view.findViewById(R.id.flow_month);
        mTitleYear = (TextView) view.findViewById(R.id.flow_year);
        mMonthIncome = (TextView) view.findViewById(R.id.flow_this_month_income);
        mMonthPay = (TextView) view.findViewById(R.id.flow_this_month_output);
        mMonthBudget = (TextView) view.findViewById(R.id.flow_budget_tv);
        mEyesIv = (ImageView) view.findViewById(R.id.flow_open_eye_iv);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.flow_open_eye_iv) {
            isOpenEye = !isOpenEye;
            hideWords(isOpenEye);
            mEyesIv.setImageResource(isOpenEye ? R.drawable.flow_eye_open : R.drawable.flow_eye_close);
            //更新数据
            for (int i = 0; i < accountList.size(); i++) {
                accountList.get(i).isShowMoney = isOpenEye;
            }
            flowRecordAdapter.notifyDataSetChanged();
        }
    }

    private void hideWords(boolean isOpenEye) {
        // TODO: 2018/3/28  设置具体数据（50,60,70 
        mMonthIncome.setText(isOpenEye ? "70" : getString(R.string.hideWords));
        mMonthPay.setText(isOpenEye ? "60" : getString(R.string.hideWords));
        mMonthBudget.setText(isOpenEye ? "50" : getString(R.string.hideWords));
    }
}
