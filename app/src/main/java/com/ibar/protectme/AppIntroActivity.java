package com.ibar.protectme;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppIntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        //setContentView(R.layout.activity_app_intro);*/

        addSlide(AppIntroFragment.newInstance("Botón de pánico", "Utiliza este botón cuando temas por tu integridad física.\nSe enviará notificación y tu localización al tutor y se llamará a emergencias.",
                R.drawable.red_button, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("Botón naranja", "Este botón es si estás en peligro.\nSe notifica al tutor y envia tu posición.",
                R.drawable.orange_button_copia, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance("Botón verde", "Por si necesitas ayuda o consejos o hablar de forma anónima con un experto.",
                R.drawable.green_button_copia, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)));
    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent= new Intent(getApplicationContext(), AlertsMenu.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent=new Intent(getApplicationContext(), AlertsMenu.class);
        startActivity(intent);
        finish();
    }
}
