package com.example.timetablerapp.timetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.timetablerapp.R;

/**
 * 08/05/19 -bernard
 */
public class TimetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.timetable_fragment_container);

        if (fragment == null) {
            fragment = ListLecturerUnitsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.timetable_fragment_container, fragment)
                    .commit();
        }
    }
}
