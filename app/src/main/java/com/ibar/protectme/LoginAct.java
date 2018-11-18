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

    private long userId;
    private String password;

    @BindView(R.id.input_email) EditText _userIdText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);

        _loginButton = findViewById(R.id.btn_login);
        _loginButton.setOnClickListener(v -> login());

        _userIdText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);

        this.userId = getIntent().getLongExtra("userId", 0);
        if(userId != 0)
            _userIdText.setText(String.valueOf(userId));

        this.password = getIntent().getStringExtra("password");
        if(password != null) {
            Log.d("BRUHH pass: ", String.valueOf(userId));
            _passwordText.setText(password);
        }


        if(userId > 0 && password != null && !password.isEmpty())
            login();
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginAct.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

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
        cb = findViewById(R.id.checkbox);
        if(cb.isChecked()) {
            DbManager.setRow(userId, password);
        }
        _loginButton.setEnabled(true);
        Intent i = new Intent(LoginAct.this, AlertsMenu.class);
        startActivity(i);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        this.userId = Long.parseLong(_userIdText.getText().toString());
        this.password = _passwordText.getText().toString();

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
