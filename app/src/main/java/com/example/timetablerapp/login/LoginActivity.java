package com.example.timetablerapp.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.timetablerapp.MainActivity;
import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;

/**
 * 06/05/19 -bernard
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter loginPresenter;

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize LoginPresenter
        loginPresenter = new LoginPresenter(MainApplication.getUserRepository(), this);

        edtUsername = findViewById(R.id.edit_username);
        edtPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.button_login);

        btnLogin.setOnClickListener(v -> loginPresenter.login());
    }

    @Override
    public boolean startProgressDialog() {
        return false;
    }

    @Override
    public void showUsernameError(int s) {
        edtUsername.setError(getString(R.string.username_error));
    }

    @Override
    public String getUsername() {
        return edtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return edtPassword.getText().toString();
    }

    @Override
    public void showPasswordError(int s) {
        edtPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void startMainActivity() {
        startActivity(MainActivity.newIntent(this));
    }

    @Override
    public String getUserRole() {
        return null;
    }
}
