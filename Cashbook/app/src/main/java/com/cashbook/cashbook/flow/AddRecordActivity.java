package com.cashbook.cashbook.flow;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.View.DragGridView;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private TextView mSaveTv;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private RelativeLayout mDropRl;
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
        mDropRl.setOnClickListener(this);
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
        initData();
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

    private void initData() {
        //模板
        CashbookInfo cashbookInfo0 = new CashbookInfo();
        cashbookInfo0.money = "0.00";
        cashbookInfo0.name = "模板";
        cashbookInfo0.beizhu = 1;
        cashbookInfo0.isTemplate = 0;
        cashbookInfo0.type = "指出";
        mCashbookDatabaseManager.addAccountData(cashbookInfo0);

        //支出
        CashbookInfo cashbookInfo1 = new CashbookInfo();
        cashbookInfo1.money = "0.00";
        cashbookInfo1.name = "支出";
        cashbookInfo1.beizhu = 1;
        cashbookInfo1.isTemplate = 1;
        cashbookInfo1.type = "指出";
        mCashbookDatabaseManager.addAccountData(cashbookInfo1);

        //收入
        CashbookInfo cashbookInfo2 = new CashbookInfo();
        cashbookInfo2.money = "0.00";
        cashbookInfo2.name = "收入";
        cashbookInfo2.beizhu = 1;
        cashbookInfo2.isTemplate = 1;
        cashbookInfo2.type = "指出";
        mCashbookDatabaseManager.addAccountData(cashbookInfo2);

        //收入
        CashbookInfo cashbookInfo3 = new CashbookInfo();
        cashbookInfo3.money = "0.00";
        cashbookInfo3.name = "转账";
        cashbookInfo3.beizhu = 1;
        cashbookInfo3.isTemplate = 1;
        cashbookInfo3.type = "指出";
        mCashbookDatabaseManager.addAccountData(cashbookInfo3);

        //余额
        CashbookInfo cashbookInfo4 = new CashbookInfo();
        cashbookInfo4.money = "0.00";
        cashbookInfo4.name = "余额";
        cashbookInfo4.beizhu = 1;
        cashbookInfo4.isTemplate = 0;
        cashbookInfo4.type = "指出";
        mCashbookDatabaseManager.addAccountData(cashbookInfo4);

        //代付
        CashbookInfo cashbookInfo5 = new CashbookInfo();
        cashbookInfo5.money = "0.00";
        cashbookInfo5.name = "代付";
        cashbookInfo5.beizhu = 1;
        cashbookInfo5.isTemplate = 0;
        cashbookInfo5.type = "指出";
        mCashbookDatabaseManager.addAccountData(cashbookInfo5);

        //报销
        CashbookInfo cashbookInfo6 = new CashbookInfo();
        cashbookInfo6.money = "0.00";
        cashbookInfo6.name = "报销";
        cashbookInfo6.beizhu = 1;
        cashbookInfo6.isTemplate = 0;
        cashbookInfo6.type = "指出";
        mCashbookDatabaseManager.addAccountData(cashbookInfo6);

        //退款
        CashbookInfo cashbookInfo7 = new CashbookInfo();
        cashbookInfo7.money = "0.00";
        cashbookInfo7.name = "退款";
        cashbookInfo7.beizhu = 1;
        cashbookInfo7.isTemplate = 0;
        cashbookInfo7.type = "指出";
        mCashbookDatabaseManager.addAccountData(cashbookInfo7);

        //借贷
        CashbookInfo cashbookInfo8 = new CashbookInfo();
        cashbookInfo8.money = "0.00";
        cashbookInfo8.name = "借贷";
        cashbookInfo8.beizhu = 1;
        cashbookInfo8.isTemplate = 0;
        cashbookInfo8.type = "指出";
        mCashbookDatabaseManager.addAccountData(cashbookInfo8);
    }

    private void addIndicators() {
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

    private void initView() {
        mBackIv = (ImageView) findViewById(R.id.add_record_iv);
        mViewPager = (ViewPager) findViewById(R.id.add_record_vp);
        mBottonSaveTv = (TextView) findViewById(R.id.add_save_tv);
        mSaveTv = (TextView) findViewById(R.id.add_record_save_tv);
        mTabLayout = (TabLayout) findViewById(R.id.add_record_tab);
        mDropRl = (RelativeLayout) findViewById(R.id.add_record_drop_rl);
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

        } else if (v.getId() == R.id.add_record_drop_rl) {
            final List<HashMap<String, Object>> dataSourceList = new ArrayList<>();
            View root = LayoutInflater.from(AddRecordActivity.this).inflate(R.layout.add_record_drop_popup, null);
            PopupWindow popupWindow = new PopupWindow(root, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.showAsDropDown(mTabLayout);
            popupWindow.setFocusable(false);

            DragGridView dragGridView = (DragGridView) root.findViewById(R.id.add_record_drop_dgv);
            HashMap<String, Object> itemHashMap1 = new HashMap<>();
            itemHashMap1.put("item_text", "模板");
            dataSourceList.add(itemHashMap1);
            HashMap<String, Object> itemHashMap2 = new HashMap<>();
            itemHashMap2.put("item_text", "支出");
            dataSourceList.add(itemHashMap2);
            HashMap<String, Object> itemHashMap3 = new HashMap<>();
            itemHashMap3.put("item_text", "收入");
            dataSourceList.add(itemHashMap3);
            HashMap<String, Object> itemHashMap4 = new HashMap<>();
            itemHashMap4.put("item_text", "转账");
            dataSourceList.add(itemHashMap4);
            HashMap<String, Object> itemHashMap5 = new HashMap<>();
            itemHashMap5.put("item_text", "余额");
            dataSourceList.add(itemHashMap5);
            HashMap<String, Object> itemHashMap6 = new HashMap<>();
            itemHashMap6.put("item_text", "代付");
            dataSourceList.add(itemHashMap6);
            HashMap<String, Object> itemHashMap7 = new HashMap<>();
            itemHashMap7.put("item_text", "报销");
            dataSourceList.add(itemHashMap7);
            HashMap<String, Object> itemHashMap8 = new HashMap<>();
            itemHashMap8.put("item_text", "退款");
            dataSourceList.add(itemHashMap8);
            HashMap<String, Object> itemHashMap9 = new HashMap<>();
            itemHashMap9.put("item_text", "借贷");
            dataSourceList.add(itemHashMap9);
            final SimpleAdapter simpleAdapter = new SimpleAdapter(AddRecordActivity.this, dataSourceList,
                    R.layout.drag_grid_view_item, new String[]{"item_text"}, new int[]{R.id.item_text});
            dragGridView.setAdapter(simpleAdapter);
            dragGridView.setOnChangeListener(new DragGridView.OnChanageListener() {
                @Override
                public void onChange(int form, int to) {
                    HashMap<String, Object> temp = dataSourceList.get(form);
                    if (form < to) {
                        for (int i = form; i < to; i++) {
                            Collections.swap(dataSourceList, i, i + 1);
                        }
                    } else if (form > to) {
                        for (int i = form; i > to; i--) {
                            Collections.swap(dataSourceList, i, i - 1);
                        }
                    }
                    dataSourceList.set(to, temp);
                    simpleAdapter.notifyDataSetChanged();
                }
            });
            dragGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    parent.getChildAt(position).setSelected(true);
                }
            });
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
