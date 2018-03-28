package com.cashbook.cashbook.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luruiqian on 2018/3/28.
 */

public class CashbookSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cashbook_database";
    private static final int DATABASE_VERSION = 1;
    //创建数据库的表
    private static final String CREATE_TABLE = "create table cashBook ("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "datePeriod text,"
            + "income text,"
            + "pay text)";

    public CashbookSQLiteOpenHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public CashbookSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CashbookSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
