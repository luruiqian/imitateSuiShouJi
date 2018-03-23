package com.cashbook.cashbook.flow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashbook.cashbook.R;

public class FlowFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * onCreateView是创建的时候调用，onViewCreated是在onCreateView后被触发的事件
     **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flow, container, false);
    }
}
