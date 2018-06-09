package com.cashbook.cashbook.flow;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.database.CashbookDatabaseManager;
import com.cashbook.cashbook.database.CashbookInfo;
import com.cashbook.cashbook.database.CashbookSQLiteOpenHelper;
import com.cashbook.cashbook.database.CashbookTemplate;
import com.cashbook.cashbook.flow.adapter.AddRecordPagerAdapter;
import com.cashbook.cashbook.flow.fragment.AddRecordBalanceFragment;
import com.cashbook.cashbook.flow.fragment.AddRecordBaoxiaoFragment;
import com.cashbook.cashbook.flow.fragment.AddRecordDaifuFragment;
import com.cashbook.cashbook.flow.fragment.AddRecordRefundFragment;
import com.cashbook.cashbook.flow.fragment.AddRecordShouruFragment;
import com.cashbook.cashbook.flow.fragment.AddRecordTemplateFragment;
import com.cashbook.cashbook.flow.fragment.AddRecordToLoanFragment;
import com.cashbook.cashbook.flow.fragment.AddRecordTransferAccountsFragment;
import com.cashbook.cashbook.flow.fragment.AddRecordZhichuFragment;

import java.util.ArrayList;
import java.util.List;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView mSaveTv;
    private TextView mBottonSaveTv;
    private TextView mSaveTemplete;
    private TextView mRecordOneMore;
    private ImageView mBackIv;
    private LinearLayout mBottomSaveTemplateLy;

    private List<String> mTabIndicators;
    private List<Fragment> mRecordFragments;

    private FragmentManager manager;
    private AddRecordPagerAdapter mAddRecordPagerAdapter;
    private CashbookSQLiteOpenHelper mCashbookSQLiteOpenHelper;
    private CashbookDatabaseManager mCashbookDatabaseManager;

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
        List<CashbookTemplate> templateItemList = new ArrayList<>();
        if (mCashbookDatabaseManager == null && mCashbookSQLiteOpenHelper == null) {
            mCashbookSQLiteOpenHelper = new CashbookSQLiteOpenHelper(AddRecordActivity.this);
            mCashbookDatabaseManager = new CashbookDatabaseManager(mCashbookSQLiteOpenHelper);
        }
        mCashbookDatabaseManager.cleanAccountData();
        addIndicators();
        mCashbookDatabaseManager.addTemplateData();
        templateItemList = mCashbookDatabaseManager.queryTemplateList();

        mRecordFragments = new ArrayList<>();

        mRecordFragments.add(AddRecordTemplateFragment.newInstance());
        AddRecordTemplateFragment.getData(templateItemList);
        mRecordFragments.add(AddRecordDaifuFragment.newInstance("代付"));
        mRecordFragments.add(AddRecordZhichuFragment.newInstance("支出"));
        mRecordFragments.add(AddRecordShouruFragment.newInstance("收入"));
        mRecordFragments.add(AddRecordTransferAccountsFragment.newInstance("转账"));
        mRecordFragments.add(AddRecordBalanceFragment.newInstance("余额"));
        mRecordFragments.add(AddRecordToLoanFragment.newInstance("借贷"));
        mRecordFragments.add(AddRecordBaoxiaoFragment.newInstance("报销"));
        mRecordFragments.add(AddRecordRefundFragment.newInstance("退款"));
        manager = getSupportFragmentManager();
        mAddRecordPagerAdapter = new AddRecordPagerAdapter(manager, mRecordFragments, mTabIndicators);
    }

    private void addIndicators() {
        for (int i = 0; i < 9; i++) {
            CashbookInfo cashbookInfo = new CashbookInfo();
            cashbookInfo.money = "0.00";
            cashbookInfo.name = "代付" + i;
            cashbookInfo.beizhu = 1;
//            List<CashbookInfo.AccountItem> accountItemList = new ArrayList<>();
//            CashbookInfo.AccountItem accountItem1 = new CashbookInfo.AccountItem();
//            accountItem1.itemName = "借出账户";
//            accountItem1.itemDesc = "现金(CNY)";
//            CashbookInfo.AccountItem accountItem2 = new CashbookInfo.AccountItem();
//            accountItem2.itemName = "借贷人（借钱给谁）";
//            accountItem2.itemDesc = "亲戚";
//            accountItemList.add(accountItem1);
//            accountItemList.add(accountItem2);
//            cashbookInfo.accountItemList = accountItemList;
            mCashbookDatabaseManager.addAccountData(cashbookInfo);
        }
        mTabIndicators = mCashbookDatabaseManager.queryAccountTitle();
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
        mViewPager.setAdapter(mAddRecordPagerAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //把viewpager和tablayout联系起来
        mTabLayout.setupWithViewPager(mViewPager);
        //一下两个条件一起设置才可以使默认选中第二个选项
        mViewPager.setCurrentItem(1);
        mTabLayout.getTabAt(1).select();

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
        mBackIv = (ImageView) findViewById(R.id.add_record_iv);
        mViewPager = (ViewPager) findViewById(R.id.add_record_vp);
        mBottonSaveTv = (TextView) findViewById(R.id.add_save_tv);
        mSaveTv = (TextView) findViewById(R.id.add_record_save_tv);
        mTabLayout = (TabLayout) findViewById(R.id.add_record_tab);
        mRecordOneMore = (TextView) findViewById(R.id.add_one_more_tv);
        mSaveTemplete = (TextView) findViewById(R.id.add_save_template_tv);
        mBottomSaveTemplateLy = (LinearLayout) findViewById(R.id.add_record_bottom_ly);
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
            mSaveTv.setVisibility(View.GONE);
            mBottomSaveTemplateLy.setVisibility(View.GONE);
        } else {
            mSaveTv.setVisibility(View.VISIBLE);
            mBottomSaveTemplateLy.setVisibility(View.VISIBLE);
            //支出，收入，转账有存为模板按钮
            if (mTabIndicators.get(tab.getPosition()).equals("支出") ||
                    mTabIndicators.get(tab.getPosition()).equals("收入") ||
                    mTabIndicators.get(tab.getPosition()).equals("转账")) {
                mSaveTemplete.setVisibility(View.VISIBLE);
            } else {
                mSaveTemplete.setVisibility(View.GONE);
            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
