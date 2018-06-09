package com.cashbook.cashbook.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luruiqian on 2018/6/4.
 */

public class CashbookSQLiteOpenHelper extends SQLiteOpenHelper {
    /**
     * 创建数据库
     */
    private static final String DATABASE_NAME = "cash_book.db";
    /**
     * 数据库版本号
     */
    private static final int DATABASE_VERSION = 2;
    /**
     * 创建表
     */
    public final String CREATE_TABLE_TEMPLATE = "create table templateTable ("
            + "id integer primary key autoincrement,"
            + "type text,"
            + "money text,"
            + "itemName text,"
            + "itemDesc text,"
            + "beizhu int,"
            + "name text)";
    public final String CREATE_TABLE_ACCOUNT = "create table accountTable ("
            + "id integer primary key autoincrement,"
            + "type text,"
            + "money text,"
            + "itemName text,"
            + "itemDesc text,"
            + "beizhu int,"
            + "name text)";

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
        db.execSQL(CREATE_TABLE_TEMPLATE);
        db.execSQL(CREATE_TABLE_ACCOUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("alter table templateTable add colume type text");
                db.execSQL("alter table templateTable add colume money text");
                db.execSQL("alter table templateTable add colume itemName text");
                db.execSQL("alter table templateTable add colume itemDesc text");
                db.execSQL("alter table templateTable add colume beizhu text");
            default:
        }
    }
}
