package com.ibar.protectme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class AlertsSectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_section);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton infoButton = findViewById(R.id.infoButton);

        infoButton.setOnClickListener(view -> {
            Snackbar snackbarInfoMessage = Snackbar.make(view, "Replace with your own action\n hola\n hola\n hola\n hola", Snackbar.LENGTH_INDEFINITE);
            snackbarInfoMessage.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbarInfoMessage.dismiss();
                };
            });
        });

    }
}
