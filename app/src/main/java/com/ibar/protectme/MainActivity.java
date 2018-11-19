package com.ibar.protectme;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        long name = 2;
        String password = null;
        boolean firstTime = true;

        Cursor cursor = DbManager.getRow();
        if (cursor.moveToNext()) {
            name = cursor.getLong(cursor.getColumnIndexOrThrow("user_id"));
            password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            firstTime = cursor.getInt(cursor.getColumnIndexOrThrow("first_time")) == 0? false : true;
        }


        Intent intent = new Intent(context, LoginAct.class);

        if (password != null && name != 0) {
            intent.putExtra("userId", name);
            intent.putExtra("password", password);
        }
        intent.putExtra("firstTime", firstTime);
        startActivity(intent);
    }


}
