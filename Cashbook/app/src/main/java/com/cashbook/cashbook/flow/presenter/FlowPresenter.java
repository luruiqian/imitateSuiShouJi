package com.cashbook.cashbook.flow.presenter;

import android.content.Context;

import com.cashbook.cashbook.database.CashbookDatabaseManager;
import com.cashbook.cashbook.database.CashbookSQLiteOpenHelper;
import com.cashbook.cashbook.flow.view.FlowView;
import com.cashbook.cashbook.mvp.BasePresenterImpl;

/**
 * Created by luruiqian on 2018/7/11.
 */

public class FlowPresenter extends BasePresenterImpl<FlowView> {
    private Context mContext;
    private CashbookSQLiteOpenHelper mCashbookSQLiteOpenHelper;
    private CashbookDatabaseManager mCashbookDatabaseManager;

    public void initData(Context context) {
        mContext = context;
        if (mCashbookDatabaseManager == null && mCashbookSQLiteOpenHelper == null) {
            mCashbookSQLiteOpenHelper = new CashbookSQLiteOpenHelper(mContext);
            mCashbookDatabaseManager = new CashbookDatabaseManager(mCashbookSQLiteOpenHelper);
        }
    }

}
