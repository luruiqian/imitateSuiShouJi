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

    public CashbookDatabaseManager(CashbookSQLiteOpenHelper cashbookSQLiteOpenHelper) {
        mCashbookSQLiteOpenHelper = cashbookSQLiteOpenHelper;
    }

    public void addTemplateData() {
        SQLiteDatabase templateDatabase = mCashbookSQLiteOpenHelper.getWritableDatabase();
        String sql = "select * from accountTable where beizhu=1";
        Cursor cursor = templateDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            CashbookTemplate cashbookTemplate = new CashbookTemplate();
            if (cursor.getInt(5) == 1) {
                ContentValues cv = new ContentValues();
                cv.put("name", cashbookTemplate.name);
                cv.put("type", cashbookTemplate.type);
                cv.put("money", cashbookTemplate.money);
                cv.put("beizhu", cashbookTemplate.beizhu);
                cv.put("itemName", cashbookTemplate.itemName);
                cv.put("itemDesc", cashbookTemplate.itemDesc);
                templateDatabase.insert(mCashbookSQLiteOpenHelper.CREATE_TABLE_TEMPLATE, null, cv);
            }
        }
        cursor.close();
        templateDatabase.close();

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

    public List<String> queryAccountTitle() {
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

    public List<CashbookTemplate> queryTemplateList() {
        List<CashbookTemplate> templateItemList = new ArrayList<>();
        SQLiteDatabase templateDatabase = mCashbookSQLiteOpenHelper.getReadableDatabase();
        String sql = "select * from templateTable";
        Cursor cursor = templateDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            CashbookTemplate templateItem = new CashbookTemplate();
            templateItemList.add(templateItem);
        }
        cursor.close();
        templateDatabase.close();
        return templateItemList;
    }
}
