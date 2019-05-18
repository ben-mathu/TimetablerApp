package com.example.timetablerapp.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;

/**
 * 08/05/19 -bernard
 */
public class SignUpActivity extends AppCompatActivity {

    private String role = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();

        role = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");

        if (role.equalsIgnoreCase("student")) {
            if (actionBar != null) {
                actionBar.setTitle(role + " Sign-up");
            }

            SignUpFragment fragment = (SignUpFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container_student);

            if (fragment == null) {
                fragment = new SignUpFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container_student, fragment)
                        .commit();
            }
        } else if (role.equalsIgnoreCase("lecturer")) {
            if (actionBar != null) {
                actionBar.setTitle(role + " Sign-up");
            }

            SignUpLecturerFragment fragment = (SignUpLecturerFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container_lecturer);

            if (fragment == null) {
                fragment = new SignUpLecturerFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container_lecturer, fragment)
                        .commit();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_up_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_role:
                MainApplication.getSharedPreferences().edit()
                        .putString(Constants.ROLE, "")
                        .apply();
                Toast.makeText(this, "Press the back button", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}
