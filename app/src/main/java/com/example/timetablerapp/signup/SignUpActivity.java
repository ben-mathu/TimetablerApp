package com.example.timetablerapp.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.timetablerapp.MainActivity;
import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.login.LoginActivity;

/**
 * 08/05/19 -bernard
 */
public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    private Toolbar toolbar;

    private String role = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        toolbar = findViewById(R.id.toolbar_view_details);
        toolbar.setTitle(role + "Sign-Up");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);

        role = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");

        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container_user);

        handleFragments(fragment);
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
                startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }

    private void handleFragments(Fragment fragment) {
        if (role.equalsIgnoreCase("student")) {
            if (toolbar != null) {
                toolbar.setTitle(role + " Sign-up");
            }

            if (fragment == null) {
                fragment = new SignUpFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container_user, fragment)
                        .commit();
            }
        }

        if (role.equalsIgnoreCase("lecturer")) {
            if (toolbar != null) {
                toolbar.setTitle(role + " Sign-up");
            }


            if (fragment == null) {
                fragment = new SignUpLecturerFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container_user, fragment)
                        .commit();
            }

        }

        if (role.equalsIgnoreCase("admin")) {
            if (toolbar != null) {
                toolbar.setTitle(role + " Sign-up");
            }

            if (fragment == null) {
                fragment = new SignUpAdminFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container_user, fragment)
                        .commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
