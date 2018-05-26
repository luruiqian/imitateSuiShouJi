package com.cashbook.cashbook.flow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.flow.bean.AddDetail;
import com.cashbook.cashbook.flow.bean.AddRecordAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luruiqian on 2018/3/30.
 */

public class TabContentFragment extends Fragment {
    private TextView mTextView;
    private ListView mAddRecordLv;
    private LinearLayout mTitleMoneyLL;

    private AddRecordAccount addRecordAccount;
    private List<AddDetail> addDetailList;

    private AddRecordItemAdapter mAddRecordItemAdapter;

    private static String pageName;

    public static TabContentFragment newInstance(String name) {
        pageName = name;
        Bundle args = new Bundle();

        TabContentFragment fragment = new TabContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_record_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        bindData();
    }

    private void bindData() {
        mTextView.setText(pageName);
        mAddRecordItemAdapter = new AddRecordItemAdapter(getActivity(),addDetailList);
        mAddRecordLv.setAdapter(mAddRecordItemAdapter);
    }

    private void initData() {
        addRecordAccount = new AddRecordAccount();
        addRecordAccount.moneyNum = "2.00";
        addDetailList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            AddDetail addDetail = new AddDetail();
            addDetail.addName = "存入账户";
            addDetail.addType = "现金(CYN)";
            addDetailList.add(addDetail);
        }
        addRecordAccount.addDetailList = addDetailList;
    }

    private void initView(View view) {
        mTextView = (TextView) view.findViewById(R.id.add_record_tab_tv);
        mAddRecordLv = (ListView) view.findViewById(R.id.add_reecord_lv);
        mTitleMoneyLL = (LinearLayout) view.findViewById(R.id.add_record_money_ll);


    }
}
