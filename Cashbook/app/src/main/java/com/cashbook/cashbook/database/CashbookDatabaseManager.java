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
            ContentValues cv = new ContentValues();
            cv.put("type", cursor.getString(1));
            cv.put("money", cursor.getString(2));
            cv.put("itemName", cursor.getString(3));
            cv.put("itemDesc", cursor.getString(4));
            cv.put("beizhu", cursor.getString(5));
            cv.put("name", cursor.getString(6));
            templateDatabase.insert("templateTable", null, cv);
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
        Cursor cursor = templateDatabase.query("templateTable", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            CashbookTemplate templateItem = new CashbookTemplate();
            templateItem.type = cursor.getString(1);
            templateItem.money = cursor.getString(2);
            templateItem.itemName = cursor.getString(3);
            templateItem.itemDesc = cursor.getString(4);
            templateItem.beizhu = cursor.getInt(5);
            templateItem.name = cursor.getString(6);
            templateItemList.add(templateItem);
        }
        cursor.close();
        templateDatabase.close();
        return templateItemList;
    }
}
