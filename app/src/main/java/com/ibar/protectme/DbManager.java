package com.ibar.protectme;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class DbManager extends ContentProvider {
    public static DbConector dbHelper;


    public boolean onCreate() {
        dbHelper = new DbConector(getContext());
        return true;
    }

    public static Cursor getRow() {
        String sql = "SELECT * FROM user_info;";
        return dbHelper.getReadableDatabase().rawQuery(sql, new String[] {});
    }

    public static void setRow(long userId, String password) {
        String sql = "UPDATE user_info SET user_id = ?, password = '" + password + "';";
        Log.d("BRUH DbMan26: ", password);
        dbHelper.getReadableDatabase().execSQL(sql, new String[]{String.valueOf(userId)});
    }

    public static void setNotFirstTime() {
        dbHelper.getReadableDatabase().execSQL("UPDATE user_info SET first_time = ?", new String[]{String.valueOf(0)});

    public static void removeFromDataBase(){
        String sql = "UPDATE user_info SET user_id = null, password = null;";
        dbHelper.getReadableDatabase().execSQL(sql, new String[] {});

    }
    
    @Override
    public Cursor query(@NonNull Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        return null;
    }

    
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    
    @Override
    public Uri insert(@NonNull Uri uri,  ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}
