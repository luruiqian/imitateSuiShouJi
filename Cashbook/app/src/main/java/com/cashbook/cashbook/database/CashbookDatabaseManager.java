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
    private List<Long> accountList = new ArrayList<>();
    private SQLiteDatabase accountDatabase;

    public CashbookDatabaseManager(CashbookSQLiteOpenHelper cashbookSQLiteOpenHelper) {
        mCashbookSQLiteOpenHelper = cashbookSQLiteOpenHelper;
    }

    /**
     * 添加模板
     */
    public void addTemplateData() {
        SQLiteDatabase templateDatabase = mCashbookSQLiteOpenHelper.getWritableDatabase();
        //先清除表中的数据，然后重新查找添加
        templateDatabase.execSQL("DELETE FROM " + "templateTable");
        String sql = "select * from accountTable where isTemplate=1";
        Cursor cursor = templateDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ContentValues cv = new ContentValues();
            cv.put("type", cursor.getString(1));
            cv.put("money", cursor.getString(2));
            cv.put("itemName", cursor.getString(3));
            cv.put("itemDesc", cursor.getString(4));
            cv.put("beizhu", cursor.getString(5));
            cv.put("name", cursor.getString(6));
            cv.put("isTemplate", cursor.getString(7));
            templateDatabase.insert("templateTable", null, cv);
        }
        cursor.close();
        templateDatabase.close();
    }

    /**
     * 查找模板数据
     */
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
            templateItem.isTemplate = cursor.getInt(7);
            templateItemList.add(templateItem);
        }
        cursor.close();
        templateDatabase.close();
        return templateItemList;
    }

    /**
     * 添加记录数据
     */
    public List<Long> addAccountData(CashbookInfo cashbookInfo) {
        accountDatabase = mCashbookSQLiteOpenHelper.getWritableDatabase();
//        ContentValues subCV = new ContentValues();
//        subCV.put("itemName", cashbookInfo.accountItemList.get(0).itemName);
//        subCV.put("itemDesc", cashbookInfo.accountItemList.get(0).itemDesc);
        ContentValues cv = new ContentValues();
        cv.put("money", cashbookInfo.money);
        cv.put("beizhu", cashbookInfo.beizhu);
        cv.put("name", cashbookInfo.name);
        cv.put("isTemplate", cashbookInfo.isTemplate);
        cv.put("type", cashbookInfo.type);
//        cv.putAll(subCV);
        long rowid = accountDatabase.insert("accountTable", null, cv);
        accountList.add(rowid);
        accountDatabase.close();
        return accountList;
    }

    /**
     * 查询tablayout数据
     */
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

    /**
     * 清除记录数据
     */
    public void cleanAccountData() {
        SQLiteDatabase cleanAccountDatabase = mCashbookSQLiteOpenHelper.getReadableDatabase();
        cleanAccountDatabase.execSQL("DELETE FROM " + "accountTable");
        cleanAccountDatabase.close();
    }
}
