package com.cashbook.cashbook.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luruiqian on 2018/6/4.
 */

public class CashbookDatabaseManager {
    private CashbookSQLiteOpenHelper mCashbookSQLiteOpenHelper;
    private List<String> mNameList = new ArrayList<>();

    public CashbookDatabaseManager(SQLiteDatabase sQLiteDatabase, CashbookSQLiteOpenHelper cashbookSQLiteOpenHelper) {
        mCashbookSQLiteOpenHelper = cashbookSQLiteOpenHelper;
    }

    public void addTemplateData(CashbookTemplate cashbookTemplate) {
        SQLiteDatabase templateDatabase = mCashbookSQLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", cashbookTemplate.name);
        templateDatabase.insert(mCashbookSQLiteOpenHelper.CREATE_TABLE_TEMPLATE, null, cv);
    }

    public List<Long> addAccountData(CashbookInfo cashbookInfo) {
        List<Long> accountList = new ArrayList<>();
        SQLiteDatabase accountDatabase = mCashbookSQLiteOpenHelper.getWritableDatabase();
//        ContentValues subCV = new ContentValues();
//        subCV.put("itemName", cashbookInfo.accountItemList.get(0).itemName);
//        subCV.put("itemDesc", cashbookInfo.accountItemList.get(0).itemDesc);
        ContentValues cv = new ContentValues();
        cv.put("money", cashbookInfo.money);
        cv.put("beizhu", cashbookInfo.beizhu);
        cv.put("name", cashbookInfo.name);
//        cv.putAll(subCV);
        long rowid = accountDatabase.insert("accountTable", null, cv);
        accountList.add(rowid);
        accountDatabase.close();
        return accountList;
    }

    public List<String> qurryAccountTitle() {
        SQLiteDatabase accountDatabase = mCashbookSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = accountDatabase.query("accountTable", null, null, null, null, null, null);
        //判断游标是否为空
        while (cursor.moveToNext()) {
//            for (int i = 0; i < cursor.getCount(); i++) {
            String name = cursor.getString(6);
            mNameList.add(name);
//            }
        }
        cursor.close();
        accountDatabase.close();
        return mNameList;
    }
}
