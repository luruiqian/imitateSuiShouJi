package com.cashbook.cashbook.flow.fragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.bean.AddRecordNewsBean;
import com.cashbook.cashbook.bean.AddRecordNewsList;
import com.cashbook.cashbook.flow.adapter.AddRecordBalanceAdapter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class AddRecordBalanceFragment extends Fragment {
    private static String pageName;
    private RecyclerView mRecyclerView;
    private List<AddRecordNewsList> mNewsList;
    private String data;


    private static AddRecordBalanceFragment mTemplateFragment;

    public AddRecordBalanceFragment() {
    }

    public static AddRecordBalanceFragment newInstance(String name) {
        pageName = name;

        if (mTemplateFragment == null) {
            mTemplateFragment = new AddRecordBalanceFragment();
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
        data = readAssertResource(getActivity(), "dates.txt");
        transToJson(data);
        initView(view);
    }

    private void transToJson(String s) {
        AddRecordNewsBean bean = new Gson().fromJson(s,AddRecordNewsBean.class);
        mNewsList = bean.data;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.add_record_template_rv);
        AddRecordBalanceAdapter adapter = new AddRecordBalanceAdapter(getActivity(), mNewsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
        new Handler().post(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    private String readAssertResource(Context context, String strAssertFileName) {
        AssetManager assetManager = context.getAssets();
        String strResponse = "";
        try {
            InputStream ims = assetManager.open(strAssertFileName);
            strResponse = getStringFromInputStream(ims);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    private String getStringFromInputStream(InputStream a_is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
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
