package com.ibar.protectme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        goToAlertsButtonListener();
    }

    private void goToAlertsButtonListener() {
        Button alertsButton = findViewById(R.id.buttonToAlerts);
        alertsButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, AlertsSectionActivity.class);
            startActivity(intent);
        });
    }
}
