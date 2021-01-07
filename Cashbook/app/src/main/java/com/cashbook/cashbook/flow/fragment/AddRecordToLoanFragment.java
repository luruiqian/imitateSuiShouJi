package com.cashbook.cashbook.flow.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.activity.ImageScaleActivity;
import com.cashbook.cashbook.activity.ImageZoomActivity;

public class AddRecordToLoanFragment extends Fragment {
    private static String pageName;
    private TextView mTextView;


    private static AddRecordToLoanFragment mTemplateFragment;

    public AddRecordToLoanFragment() {
    }

    public static AddRecordToLoanFragment newInstance(String name) {
        pageName = name;

        if (mTemplateFragment == null) {
            mTemplateFragment = new AddRecordToLoanFragment();
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
        return inflater.inflate(R.layout.fragment_add_record_toloan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mTextView = (TextView) view.findViewById(R.id.id_to_large);
        mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent();
//                intent.setClass(getActivity(), ImageZoomActivity.class);
                intent.setClass(getActivity(), ImageScaleActivity.class);
                startActivity(intent);
                return false;
            }
        }) ;
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
