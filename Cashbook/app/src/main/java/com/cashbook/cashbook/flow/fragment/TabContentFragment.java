package com.cashbook.cashbook.flow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cashbook.cashbook.R;

/**
 * Created by luruiqian on 2018/3/30.
 */

public class TabContentFragment extends Fragment {
    private static String pageName;
    private TextView mTextView;

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
        mTextView.setText(pageName);
    }

    private void initView(View view) {
        mTextView = (TextView) view.findViewById(R.id.add_record_tab_tv);
    }
}
