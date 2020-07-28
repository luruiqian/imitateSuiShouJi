package com.cashbook.cashbook.flow.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.View.OptionsPickerView;
import com.cashbook.cashbook.builder.OptionsPickerBuilder;
import com.cashbook.cashbook.listener.OnOptionsSelectChangeListener;
import com.cashbook.cashbook.listener.OnOptionsSelectListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddRecordDaifuFragment extends Fragment implements View.OnClickListener {
    private OptionsPickerView mOptionsPickerView;
    private static String pageName;
    private TextView mTextView;

    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();


    private static AddRecordDaifuFragment mTemplateFragment;

    public AddRecordDaifuFragment() {
    }

    public static AddRecordDaifuFragment newInstance(String name) {
        pageName = name;

        if (mTemplateFragment == null) {
            mTemplateFragment = new AddRecordDaifuFragment();
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
        return inflater.inflate(R.layout.fragment_add_record_daifu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        initOptionData();
        initOptions();

//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url("").build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
    }

    private void initOptionData() {

        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */

        //选项1
        options1Items.add("最近使用");
        options1Items.add("职业收入");
        options1Items.add("其他收入");

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("工资收入");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("工资收入");
        options2Items_02.add("利息收入");
        options2Items_02.add("加班收入");
        options2Items_02.add("奖金收入");
        options2Items_02.add("投资收入");
        options2Items_02.add("兼职收入");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("礼金收入");
        options2Items_03.add("中奖收入");
        options2Items_03.add("意外来钱");
        options2Items_03.add("经营所得");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        /*--------数据源添加完毕---------*/
    }

    private void initOptions() {
        mOptionsPickerView = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1)
                        + options2Items.get(options1).get(options2)
                       /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/;
                mTextView.setText(tx);
            }
        })
                //设置滚轮文字大小
                .setContentTextSize(20)
                //设置分割线的颜色
                .setDividerColor(Color.LTGRAY)
                //默认选中项
                .setSelectOptions(0, 1)
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.BLACK)
                //未选中项的字体颜色
                .setTextColorOut(Color.BLACK)
                //切换时是否还原，设置默认选中第一项
                .isRestoreItem(true)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //设置外部遮罩颜色
                .setBackgroundId(0x00000000)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String tx = options1Items.get(options1)
                                + options2Items.get(options1).get(options2);
                        mTextView.setText(tx);
                    }
                })
                .build();
        //二级选择器
        mOptionsPickerView.setPicker(options1Items, options2Items);

    }

    private void initListener() {
        mTextView.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_record_tab_tv) {
            mOptionsPickerView.show();
        }
    }
}
