package com.ibar.protectme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbConector extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "User.db";

    public DbConector(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user_info ( " +
                "_id INTEGER PRIMARY KEY, " +
                "user_id INTEGER, " +
                "password TEXT, " +
                "first_time INTEGER);"
        );

        db.execSQL("INSERT INTO user_info VALUES(0, NULL, NULL, 1);");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
