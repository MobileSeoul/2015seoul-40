package com.seoul.hanokmania.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by namudak on 2015-09-14.
 */
public class HanokProvider extends ContentProvider{

    private SQLiteDatabase db;

    // Use this field for initial database
    //private HanokUrlHelper mDbHelper;
    // Use this field for normal operation
    private HanokOpenHelper mDbHelper;

    private static String TABLE;

    public static final UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(HanokContract.AUTHORITY, HanokContract.TABLES[0], HanokContract.TASKS_LIST);
        uriMatcher.addURI(HanokContract.AUTHORITY, HanokContract.TABLES[0]+ "/#", HanokContract.TASKS_ITEM);
        uriMatcher.addURI(HanokContract.AUTHORITY, HanokContract.TABLES[1], HanokContract.TASKS_LIST);
        uriMatcher.addURI(HanokContract.AUTHORITY, HanokContract.TABLES[1]+ "/#", HanokContract.TASKS_ITEM);
        uriMatcher.addURI(HanokContract.AUTHORITY, HanokContract.TABLES[2], HanokContract.TASKS_LIST);
        uriMatcher.addURI(HanokContract.AUTHORITY, HanokContract.TABLES[2]+ "/#", HanokContract.TASKS_ITEM);
    }

    @Override
    public boolean onCreate() {

        boolean ret= true;
        mDbHelper = mDbHelper.getInstance(getContext());
        db= mDbHelper.getWritableDatabase();

        if (db== null) {
            ret= false;
        }

        if (db.isReadOnly()) {
            db.close();
            db= null;
            ret= false;
        }

        return ret;
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case HanokContract.TASKS_LIST:
                return HanokContract.CONTENT_TYPE;

            case HanokContract.TASKS_ITEM:
                return HanokContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
        qb.setTables(HanokContract.TABLE);

        switch (uriMatcher.match(uri)) {
            case HanokContract.TASKS_LIST:
                break;

            case HanokContract.TASKS_ITEM:
                qb.appendWhere(HanokContract.HanokCol._ID+ "= "+ uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: "+ uri);
        }

        Cursor cursor= qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int no= uriMatcher.match(uri);
        if (uriMatcher.match(uri) != HanokContract.TASKS_LIST) {
            throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        long id= db.insert(HanokContract.TABLE, null, contentValues);

        if (id>0) {
            return ContentUris.withAppendedId(uri, id);
        }
        throw new SQLException("Error inserting into table: "+ HanokContract.TABLE);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int deleted= 0;

        switch (uriMatcher.match(uri)) {
            case HanokContract.TASKS_LIST:
                db.delete(HanokContract.TABLE,selection,selectionArgs);
                break;

            case HanokContract.TASKS_ITEM:
                String where= HanokContract.HanokCol._ID+ "= "+ uri.getLastPathSegment();
                if (!selection.isEmpty()) {
                    where += " AND "+selection;
                }

                deleted= db.delete(HanokContract.TABLE, where, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        int updated= 0;

        switch (uriMatcher.match(uri)) {
            case HanokContract.TASKS_LIST:
                db.update(HanokContract.TABLE,contentValues,s,strings);
                break;

            case HanokContract.TASKS_ITEM:
                String where= HanokContract.HanokCol._ID+ "= "+ uri.getLastPathSegment();
                if (!s.isEmpty()) {
                    where += " AND "+s;
                }
                updated= db.update(HanokContract.TABLE, contentValues, where, strings);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        return updated;
    }
}
