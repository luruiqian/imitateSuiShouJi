package com.cashbook.cashbook.flow;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cashbook.cashbook.R;
import com.cashbook.cashbook.bean.MessageEvent;
import com.cashbook.cashbook.data.CashbookSQLiteOpenHelper;
import com.cashbook.cashbook.flow.adapter.FlowRecordAdapter;
import com.cashbook.cashbook.flow.adapter.FlowSpinnerAdapter;
import com.cashbook.cashbook.flow.bean.AccountInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FlowFragment extends Fragment implements View.OnClickListener {
    //数据库
    private CashbookSQLiteOpenHelper cashbookSQLiteOpenHelper;
    private SQLiteDatabase dataBase;

    private FlowRecordAdapter flowRecordAdapter;
    private FlowSpinnerAdapter mFlowSpinnerAdapter;
    private List<AccountInfo> accountList;
    private ListView mRecordListView;
    private TextView mTitleYear;
    private TextView mTitleMonth;
    private TextView mMonthIncome;
    private TextView mMonthPay;
    private TextView mMonthBudget;
    private ImageView mEyesIv;
    private ImageView mInfoIv;
    private ImageView mScan;

    private RelativeLayout mRecordAccountRl;
    private int year;
    private int month;
    private boolean isOpenEye = true;

    private static final int REQUEST_CAMERA_CODE = 1;

    /**
     * onCreateView是创建的时候调用，onViewCreated是在onCreateView后被触发的事件
     **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flow, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
        setData();
        initListener();
        EventBus.getDefault().register(this);

    }

    private void initListener() {
        mEyesIv.setOnClickListener(this);
        mInfoIv.setOnClickListener(this);
        mScan.setOnClickListener(this);
        mRecordAccountRl.setOnClickListener(this);
    }

    private void setData() {
        setDate();
        flowRecordAdapter = new FlowRecordAdapter(getActivity(), accountList);
        mRecordListView.setAdapter(flowRecordAdapter);
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        mTitleMonth.setText(month + "");
        mTitleYear.setText(year + "");
    }

    private void initData() {
        // TODO: 2018/3/28  创建数据库
        cashbookSQLiteOpenHelper = new CashbookSQLiteOpenHelper(getActivity());
        dataBase = cashbookSQLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time", System.currentTimeMillis());
        values.put("expend", "");
        values.put("income", "");

        //假数据
        accountList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.time = "本周";
            accountInfo.expend = "50.00";
            accountInfo.income = "100.00";
            accountInfo.recordSubscribe = "这是本月的财务情况";
            accountInfo.isShowMoney = true;
            accountList.add(accountInfo);
        }
    }

    private void initView(View view) {
        mRecordAccountRl = (RelativeLayout) view.findViewById(R.id.flow_record_rl);
        mMonthIncome = (TextView) view.findViewById(R.id.flow_this_month_income);
        mMonthPay = (TextView) view.findViewById(R.id.flow_this_month_output);
        mRecordListView = (ListView) view.findViewById(R.id.flow_record_lv);
        mMonthBudget = (TextView) view.findViewById(R.id.flow_budget_tv);
        mEyesIv = (ImageView) view.findViewById(R.id.flow_open_eye_iv);
        mInfoIv = (ImageView) view.findViewById(R.id.flow_info_iv);
        mScan = (ImageView) view.findViewById(R.id.flow_message_iv);
        mTitleMonth = (TextView) view.findViewById(R.id.flow_month);
        mTitleYear = (TextView) view.findViewById(R.id.flow_year);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.flow_open_eye_iv) {
            isOpenEye = !isOpenEye;
            hideWords(isOpenEye);
            mEyesIv.setImageResource(isOpenEye ? R.drawable.flow_eye_open : R.drawable.flow_eye_close);
            //更新数据
            for (int i = 0; i < accountList.size(); i++) {
                accountList.get(i).isShowMoney = isOpenEye;
            }
            flowRecordAdapter.notifyDataSetChanged();
        } else if (v.getId() == R.id.flow_record_rl) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), AddRecordActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.flow_info_iv) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View popupView = layoutInflater.inflate(R.layout.flow_info_spinner, null);
            ListView infoLv = (ListView) popupView.findViewById(R.id.flow_sinner_lv);
            //造数据
            List<String> infoList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                infoList.add("同步账本");
            }

            mFlowSpinnerAdapter = new FlowSpinnerAdapter(getActivity(), infoList);
            infoLv.setAdapter(mFlowSpinnerAdapter);

            PopupWindow window = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            // TODO: 2016/5/17 设置背景颜色
            window.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.pop_bg));
            // TODO: 2016/5/17 设置可以获取焦点
            window.setFocusable(true);
            // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
            window.setOutsideTouchable(true);
            //测量listview的宽度，将PopupWindow宽度设置为listview的宽度，否则会宽度铺满全屏
            infoLv.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            window.setWidth(infoLv.getMeasuredWidth());
            // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
//            window.showAsDropDown(mInfoIv,-200,20);
            window.showAtLocation(mInfoIv, Gravity.RIGHT, 20, -120);

        } else if (v.getId() == R.id.flow_message_iv) {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//                return ;
//            }
            //1.检测是否有权限
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            } else {
                //2.如果没有权限就请求权限
                Toast.makeText(getContext(), "无权限", Toast.LENGTH_SHORT).show();
                //requestPermission 是异步请求方法，调用它之后，Android会弹出系统权限授权对话框要求用户反馈。
                //为响应用户操作，还需要重写onRequestPermissionResult()响应方法，用户点击 允许 或 拒绝 按钮后，Android就会调用这个方法。
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
            }
        }
    }

    public static boolean hasPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /***
     * 3.重写onRequestPermissionResult()响应方法，用户点击 允许 或 拒绝 按钮后，Android就会调用这个方法。
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "已经获取权限，可以进行操作", Toast.LENGTH_SHORT).show();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[0])) {
                        //点击“禁止”
                        new AlertDialog.Builder(getActivity()).setMessage("该功能需要您开启摄像头权限")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
                                            }
                                        }
                                ).show();
                    } else {
                        //点击“拒绝并不再询问”
                        new AlertDialog.Builder(getActivity()).setMessage("该功能需要您开启摄像头权限，请去设置中统一摄像头权限")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                                intent.putExtra("uri", uri.toString());
                                                startActivityForResult(intent, 1);
                                            }
                                        }
                                ).show();
                    }
                    Toast.makeText(getContext(), "您拒绝了权限", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
        }
    }

    @Subscribe(sticky = false)
    public void makeEvent(MessageEvent messageEvent) {
        Toast.makeText(getActivity(), messageEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void hideWords(boolean isOpenEye) {
        // TODO: 2018/3/28  设置具体数据（50,60,70
        mMonthIncome.setText(isOpenEye ? "70" : getString(R.string.hideWords));
        mMonthPay.setText(isOpenEye ? "60" : getString(R.string.hideWords));
        mMonthBudget.setText(isOpenEye ? "50" : getString(R.string.hideWords));
    }
}
