package com.ibar.protectme;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginAct extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private CheckBox cb;

    @BindView(R.id.input_email) EditText _userIdText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);

        findViewById(R.id.next).setOnClickListener(view ->  goToAlertsMenu());

        _loginButton = findViewById(R.id.btn_login);
        _loginButton.setOnClickListener(v -> login());

        long userId = getIntent().getLongExtra("userId", 0);
        if(userId != 0)
            _userIdText.setText(String.valueOf(userId));

        String password = getIntent().getStringExtra("password");
        if(password != null)
            _passwordText.setText(password);

        Log.d("Login ", String.valueOf(userId));
        if(userId > 0 && password != null && !password.isEmpty())
            login();

    }

    private void goToAlertsMenu() {
        Intent intent = new Intent(this, AlertsMenu.class);
        startActivity(intent);
    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginAct.this,
                R.style.AppTheme_Dark);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        cb = findViewById(R.id.checkbox);
        if(cb.isChecked()) {

        }

        new android.os.Handler().postDelayed(
                () -> {
                    // On complete call either onLoginSuccess or onLoginFailed
                    onLoginSuccess();
                    // onLoginFailed();
                    progressDialog.dismiss();
                }, 3000);
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        long userId = Long.parseLong(_userIdText.getText().toString());
        String password = _passwordText.getText().toString();

        if (userId <= 0) {
            _userIdText.setError("enter a valid user id");
            valid = false;
        } else {
            _userIdText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 45) {
            _passwordText.setError("between 8 and 45 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
