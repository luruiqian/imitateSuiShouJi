package com.cashbook.cashbook.flow.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cashbook.cashbook.R;

public class AddRecordBaoxiaoFragment extends Fragment {
    private static String pageName;
    private TextView mTextView;


    private static AddRecordBaoxiaoFragment mTemplateFragment;

    public AddRecordBaoxiaoFragment() {
    }

    public static AddRecordBaoxiaoFragment newInstance(String name) {
        pageName = name;

        if (mTemplateFragment == null) {
            mTemplateFragment = new AddRecordBaoxiaoFragment();
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
        return inflater.inflate(R.layout.fragment_add_record_baoxiao, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mTextView = (TextView) view.findViewById(R.id.add_record_tab_tv);
        mTextView.setText(pageName);
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
