package com.cashbook.cashbook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cashbook.cashbook.account.AccountFragment;
import com.cashbook.cashbook.finance.FinanceFragment;
import com.cashbook.cashbook.flow.FlowFragment;
import com.cashbook.cashbook.load.LoadFragment;
import com.cashbook.cashbook.more.MoreFragment;
import com.cashbook.cashbook.my.MyFragment;

import java.util.List;

import static android.support.design.widget.TabLayout.MODE_FIXED;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private FragmentTransaction mTransaction;
    private FragmentManager mFragmentManager;
    private TabLayout mBottomTabLayout;
    private Fragment mCurrentFragment;
    private int mTabPosition;
    private long firstExitTime;
    private int[] mBottomTabResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(MainActivity.this);
        initView();
        initResource();
        initTab();
        initFragment();
        initListener();
    }

    private void initListener() {
        mBottomTabLayout.addOnTabSelectedListener(this);
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.add(getContentFragmentId(), createCurrentFragment(1), 1 + "");
        mTransaction.addToBackStack(1 + "");
        mTransaction.commitAllowingStateLoss();
        //需要置空，否则接下来会出现重复commit(),抛异常
        mTransaction = null;
    }

    private void initTab() {
        for (int i = 0; i < mBottomTabResource.length; i++) {
            TabLayout.Tab tab = mBottomTabLayout.newTab();
            tab.setCustomView(getTabView(i));
            mBottomTabLayout.addTab(tab);
        }
        /**
         * TabMode:布局中Tab的行为模式（behavior mode），有两种值：MODE_FIXED 和 MODE_SCROLLABLE。
         * MODE_FIXED:固定tabs，并同时显示所有的tabs。
         * MODE_SCROLLABLE：可滚动tabs，显示一部分tabs，在这个模式下能包含长标签和大量的tabs，最好用于用户不需要直接比较tabs。
         */
        mBottomTabLayout.setTabMode(MODE_FIXED);
        selectTab(1);
    }

    private View getTabView(int i) {
        View view = LayoutInflater.from(this).inflate(R.layout.home_tab_view, null);
        ImageView tabView = (ImageView) view.findViewById(R.id.home_tab_iv);
        tabView.setImageResource(R.drawable.home_tab_home_bg_selector);
        return view;
    }

    private void initResource() {
        mBottomTabResource = new int[]{
                R.drawable.home_tab_home_bg_selector,
                R.drawable.home_tab_home_bg_selector,
                R.drawable.home_tab_home_bg_selector,
                R.drawable.home_tab_home_bg_selector,
                R.drawable.home_tab_home_bg_selector,
                R.drawable.home_tab_home_bg_selector};
    }

    private void initView() {
        mBottomTabLayout = (TabLayout) findViewById(R.id.activity_tab);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (mTransaction == null) {
            mTransaction = mFragmentManager.beginTransaction();
        }
        mTabPosition = tab.getPosition();
        Fragment currentFragment = mFragmentManager.findFragmentByTag(mTabPosition + "");
        if (currentFragment != null) {
            mTransaction.attach(currentFragment);
        } else {
            currentFragment = createCurrentFragment(mTabPosition);
            mTransaction.add(getContentFragmentId(), currentFragment, mTabPosition + "");
        }
        if (currentFragment != mCurrentFragment) {
            if (mCurrentFragment != null) {
                mCurrentFragment.setMenuVisibility(false);
                mCurrentFragment.setUserVisibleHint(false);
                mTransaction.hide(mCurrentFragment);
            } else {
                //内存回收，但mFragmentManager会保存相关fragment
                List<Fragment> fragments = mFragmentManager.getFragments();
                if (fragments != null && fragments.size() > 0) {
                    for (Fragment fragment : fragments) {
                        if (fragment != null) {
                            fragment.setMenuVisibility(false);
                            fragment.setUserVisibleHint(false);
                            mTransaction.hide(fragment);
                        }
                    }
                }
            }
            if (currentFragment != null) {
                currentFragment.setMenuVisibility(true);
                currentFragment.setUserVisibleHint(true);
            }
            mCurrentFragment = currentFragment;
            mTransaction.show(mCurrentFragment);
            mTransaction.addToBackStack(mTabPosition + "");
            mTransaction.commitAllowingStateLoss();
            mTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBottomTabLayout.getSelectedTabPosition() == 1) {
                long secondExitTime = System.currentTimeMillis();
                if (secondExitTime - firstExitTime > 2000) {
                    Toast.makeText(MainActivity.this, R.string.exit_toast, Toast.LENGTH_SHORT).show();
                    firstExitTime = secondExitTime;
                } else {
                    MyApplication.getInstance().exitApp();
                }
                return true;
            } else {
                selectTab(1);
                if (mTransaction == null) {
                    mTransaction = mFragmentManager.beginTransaction();
                }
                mTransaction.replace(getContentFragmentId(), createCurrentFragment(1));
                mTransaction.commitAllowingStateLoss();
                mTransaction = null;
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 选择指定选项卡
     */
    public void selectTab(int position) {
        TabLayout.Tab tab = mBottomTabLayout.getTabAt(position);
        if (tab != null) {
            tab.select();
        }
    }

    private int getContentFragmentId() {
        return R.id.activity_content;
    }

    private Fragment createCurrentFragment(int mTabPosition) {
        Fragment fragment = null;
        switch (mTabPosition) {
            case 0:
                fragment = new MyFragment();
                break;
            case 1:
                fragment = new FlowFragment();
                break;
            case 2:
                fragment = new AccountFragment();
                break;
            case 3:
                fragment = new LoadFragment();
                break;
            case 4:
                fragment = new FinanceFragment();
                break;
            case 5:
                fragment = new MoreFragment();
                break;
        }
        return fragment;
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
