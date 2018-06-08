package com.cashbook.cashbook.flow.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.database.CashbookTemplate;
import com.cashbook.cashbook.flow.adapter.AddRecordTemplateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luruiqian
 */
public class AddRecordTemplateFragment extends Fragment {
    private ListView mTemplateLv;
    private static AddRecordTemplateFragment mTemplateFragment;
    private AddRecordTemplateAdapter mAddRecordTemplateAdapter;
    private static List<CashbookTemplate> mTemplateItemList = new ArrayList<>();

    public AddRecordTemplateFragment() {
    }

    public static AddRecordTemplateFragment newInstance() {
        if (mTemplateFragment == null) {
            mTemplateFragment = new AddRecordTemplateFragment();
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
        return inflater.inflate(R.layout.fragment_add_record_template, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    public static void getData(List<CashbookTemplate> templateItemList) {
        mTemplateItemList = templateItemList;
    }

    private void initView(View view) {
        mTemplateLv = (ListView) view.findViewById(R.id.add_record_template_lv);
        mAddRecordTemplateAdapter = new AddRecordTemplateAdapter(mTemplateItemList, getActivity());
        mTemplateLv.setAdapter(mAddRecordTemplateAdapter);
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
