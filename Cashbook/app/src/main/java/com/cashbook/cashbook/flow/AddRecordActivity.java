package com.cashbook.cashbook.flow;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.flow.adapter.AddRecordPagerAdapter;
import com.cashbook.cashbook.flow.fragment.TabContentFragment;

import java.util.ArrayList;
import java.util.List;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<String> mTabIndicators;
    private List<Fragment> mRecordFragments;
    private AddRecordPagerAdapter mAddRecordPagerAdapter;
    private FragmentManager manager;
    private TextView mSaveTv;
    private TextView mBottonSaveTv;
    private TextView mSaveTemplete;
    private TextView mRecordOneMore;
    private ImageView mBackIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        initView();
        initContent();
        initTab();
        initListener();
    }

    private void initListener() {
        mSaveTv.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
        mBottonSaveTv.setOnClickListener(this);
        mSaveTemplete.setOnClickListener(this);
        mRecordOneMore.setOnClickListener(this);
        mTabLayout.addOnTabSelectedListener(this);
    }

    private void initContent() {
        mRecordFragments = new ArrayList<>();
        mTabIndicators = new ArrayList<>();
        mTabIndicators.add("模板");
        mTabIndicators.add("代付");
        mTabIndicators.add("支出");
        mTabIndicators.add("收入");
        mTabIndicators.add("转账");
        mTabIndicators.add("余额");
        mTabIndicators.add("借贷");
        mTabIndicators.add("报销");
        mTabIndicators.add("退款");
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mRecordFragments.add(TabContentFragment.newInstance(mTabIndicators.get(i)));
        }
        manager = getSupportFragmentManager();
        mAddRecordPagerAdapter = new AddRecordPagerAdapter(manager, mRecordFragments, mTabIndicators);
    }

    private void initTab() {
        //需要先添加tabitem，否则报空指针
        for (int i = 0; i < mTabIndicators.size(); i++) {
            TabLayout.Tab itemTab = mTabLayout.newTab();
            if (itemTab != null) {
                itemTab.setCustomView(R.layout.add_record_tab);
                TextView tabText = (TextView) itemTab.getCustomView().findViewById(R.id.add_record_tab_tv);
                tabText.setText(mTabIndicators.get(i));
                mTabLayout.addTab(itemTab);
            }
        }
//        selectTab(1);
        mTabLayout.getTabAt(1).getCustomView().setSelected(true);
        setSaveBtnStatus(mTabLayout.getTabAt(1));
        mViewPager.setAdapter(mAddRecordPagerAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //把viewpager和tablayout联系起来
        mTabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * 选择指定选项卡
     */
    public void selectTab(int position) {
        TabLayout.Tab tab = mTabLayout.getTabAt(position);
        if (tab != null) {
            tab.select();
        }
        mTabIndicators.get(position);
    }

    private void initView() {
        mSaveTemplete = (TextView) findViewById(R.id.add_save_template_tv);
        mRecordOneMore = (TextView) findViewById(R.id.add_one_more_tv);
        mTabLayout = (TabLayout) findViewById(R.id.add_record_tab);
        mSaveTv = (TextView) findViewById(R.id.add_record_save_tv);
        mBottonSaveTv = (TextView) findViewById(R.id.add_save_tv);
        mViewPager = (ViewPager) findViewById(R.id.add_record_vp);
        mBackIv = (ImageView) findViewById(R.id.add_record_iv);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_save_tv) {

        } else if (v.getId() == R.id.add_save_template_tv) {

        } else if (v.getId() == R.id.add_one_more_tv) {

        } else if (v.getId() == R.id.add_record_iv) {
            AddRecordActivity.this.finish();
        } else if (v.getId() == R.id.add_record_save_tv) {

        }
    }

    private void setSaveBtnStatus(TabLayout.Tab tab) {
        if (mTabIndicators.get(tab.getPosition()).equals("模板")) {
            mBottonSaveTv.setVisibility(View.GONE);
        } else {
            mBottonSaveTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setSaveBtnStatus(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
