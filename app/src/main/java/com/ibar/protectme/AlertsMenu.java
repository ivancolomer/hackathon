package com.ibar.protectme;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;

public class AlertsMenu extends AppCompatActivity {
    private DrawerLayout drawer;
    double latitude;
    double longitude;
    static Coordinates coord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_menu);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        findViewById(R.id.redButton).setOnClickListener(view -> redButtonMethod());
        findViewById(R.id.orangeButton).setOnClickListener(view -> orangeButtonMethod());

        getSupportActionBar().setTitle(null);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void redButtonMethod() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Atención!");
        builder.setMessage("¿Realmente está en juego tu integridad física?");
        builder.setCancelable(false);
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getLocation(true);
                //onCall();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void orangeButtonMethod() {
        getLocation(false);
        Toast.makeText(this, "Coordenadas enviadas: {"+String.valueOf(latitude)+", "+String.valueOf(longitude)+"}", Toast.LENGTH_SHORT).show();
    }

    private void getLocation(boolean redButton) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Integer.parseInt("124"));
        } else {
            GPSTracker tracker = new GPSTracker(this);
            if (!tracker.canGetLocation()) {
                tracker.showSettingsAlert();
            } else {
                latitude = tracker.getLatitude();
                longitude = tracker.getLongitude();
                AlertsMenu.coord = new Coordinates(latitude, longitude);
                int type = redButton? 0 : 1;
                storeData(type);
                Toast.makeText(this, "Coordenadas enviadas: " + AlertsMenu.coord.toString(), Toast.LENGTH_LONG).show();

            }
            if (redButton)
                onCall();
        }


    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    Integer.parseInt("123"));
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:123456789")));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            case 124:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLocation(true);
                } else {
                    Log.d("TAG", "Location Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.logout:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logOut() {
        DbManager.removeFromDataBase();
        finish();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    public void storeData(int typeAlert) {
        double latitude = AlertsMenu.coord.latitude;
        double longitude = AlertsMenu.coord.longitude;

        HashMap<String, String> map = new HashMap<>();
        Config.putLogInParameters(map);
        map.put("alert_type", String.valueOf(typeAlert));
        map.put("alert_lat", String.valueOf(latitude));
        map.put("alert_lon", String.valueOf(longitude));
        HttpPostAsyncTask2 task = new HttpPostAsyncTask2(map);
        task.execute("internal/createalert");

    }

}
