package com.cashbook.cashbook.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class BookContentProvider extends ContentProvider {
    private static final String TAG = "BookContentProvider";
    private static UriMatcher uriMatcher = null;
    ContentProviderDBHelper providerDBHelper;
    SQLiteDatabase sqLiteDatabase;
    private static final int BOOKS = 1;
    private static final int BOOK = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(IProviderMetaData.AUTHORITY,
                IProviderMetaData.BookTableMetaData.TABLE_NAME, BOOKS);
        uriMatcher.addURI(IProviderMetaData.AUTHORITY,
                IProviderMetaData.BookTableMetaData.TABLE_NAME + "/#",
                BOOK);
    }

    @Override
    public boolean onCreate() {
        providerDBHelper = new ContentProviderDBHelper(getContext(),null,null,1);
        return (providerDBHelper == null) ? false : true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        sqLiteDatabase = providerDBHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                return sqLiteDatabase.query(IProviderMetaData.BookTableMetaData.TABLE_NAME,
                        projection, selection, selectionArgs, null, null,
                        sortOrder);
            case BOOK:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return sqLiteDatabase.query(IProviderMetaData.BookTableMetaData.TABLE_NAME,
                        projection, where, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("This is a unKnow Uri"
                        + uri.toString());
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                return IProviderMetaData.BookTableMetaData.CONTENT_LIST;
            case BOOK:
                return IProviderMetaData.BookTableMetaData.CONTENT_ITEM;
            default:
                throw new IllegalArgumentException("This is a unKnow Uri"
                        + uri.toString());
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                sqLiteDatabase = providerDBHelper.getWritableDatabase();
                long rowId = sqLiteDatabase.insert(
                        IProviderMetaData.BookTableMetaData.TABLE_NAME,
                        IProviderMetaData.BookTableMetaData.BOOK_ID,
                        values);
                Uri insertUri = Uri.withAppendedPath(uri, "/" + rowId);
                Log.i(TAG, "insertUri:" + insertUri.toString());
                getContext().getContentResolver().notifyChange(uri, null);
                return insertUri;
            case BOOK:

            default:
                throw new IllegalArgumentException("This is a unKnow Uri"
                        + uri.toString());
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        sqLiteDatabase = providerDBHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                return sqLiteDatabase.delete(IProviderMetaData.BookTableMetaData.TABLE_NAME,
                        selection, selectionArgs);
            case BOOK:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return sqLiteDatabase.delete(IProviderMetaData.BookTableMetaData.TABLE_NAME,
                        selection, selectionArgs);
            default:
                throw new IllegalArgumentException("This is a unKnow Uri"
                        + uri.toString());
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        sqLiteDatabase = providerDBHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOKS:
                return sqLiteDatabase.update(IProviderMetaData.BookTableMetaData.TABLE_NAME,
                        values, null, null);
            case BOOK:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return sqLiteDatabase.update(IProviderMetaData.BookTableMetaData.TABLE_NAME,
                        values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("This is a unKnow Uri"
                        + uri.toString());
        }
    }
}
