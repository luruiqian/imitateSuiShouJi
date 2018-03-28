package com.cashbook.cashbook.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by luruiqian on 2018/3/28.
 */

public class RecordAccountDao {
    private CashbookSQLiteOpenHelper helper;

    public void addAccountHistory() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

    }
}
