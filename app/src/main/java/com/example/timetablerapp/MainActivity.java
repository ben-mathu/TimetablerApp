package com.example.timetablerapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.login.LoginActivity;
import com.example.timetablerapp.settings.dialog.ShowExplanationDialog;
import com.example.timetablerapp.signup.SignUpActivity;

public class MainActivity extends AppCompatActivity implements MainView{
    private MainPresenter presenter;

    private Button btnGotoLogin;
    private Spinner spinnerUserRole;

    private String role = "";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkIfRoleAlreadySelected();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                DialogFragment dialog = ShowExplanationDialog.newFragment("Hi, the application requires internet access and I need your permission first.", MainApplication.INTERNET_ACCESS_REQUEST_CODE);
                dialog.show(getSupportFragmentManager(), "Explanation dialog");
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MainApplication.INTERNET_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MainApplication.INTERNET_ACCESS_REQUEST_CODE:
                DialogFragment dialog = ShowExplanationDialog.newFragment("Hi, the application requires internet access and I need your permission first.", MainApplication.INTERNET_ACCESS_REQUEST_CODE);
                dialog.show(getSupportFragmentManager(), "Explanation dialog");
                break;
        }
    }

    private void checkIfRoleAlreadySelected() {
        role = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");

        if (role != null && !role.isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkIfRoleAlreadySelected();

        presenter = new MainPresenter(MainApplication.getStudentRepository(), this);

        spinnerUserRole = findViewById(R.id.spinner_role);
        ArrayAdapter<CharSequence> arrayAdapter =  ArrayAdapter.createFromResource(this,
                    R.array.roles, android.R.layout.simple_spinner_dropdown_item
                );
        spinnerUserRole.setAdapter(arrayAdapter);

        btnGotoLogin = findViewById(R.id.button_goto_login);
        btnGotoLogin.setOnClickListener(v -> {
            role = spinnerUserRole.getSelectedItem().toString();
            presenter.sendUserRole(role);
        });
    }

    @Override
    public void setSalt(String salt) {
        MainApplication.getSharedPreferences().edit()
                .putString(Constants.ROLE, role)
                .apply();
        MainApplication.getSharedPreferences().edit()
                .putString(Constants.SALT, salt)
                .apply();

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, "Message: " + message, Toast.LENGTH_SHORT).show();
    }
}
