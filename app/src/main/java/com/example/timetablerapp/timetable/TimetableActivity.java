package com.example.timetablerapp.timetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.timetablerapp.R;

/**
 * 08/05/19 -bernard
 */
public class TimetableActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        toolbar = findViewById(R.id.toolbar_user_details);
        toolbar.setTitle("Timetable");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.timetable_fragment_container);

//        if (fragment == null) {
//            fragment = ListUnitsFragment.newInstance();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.timetable_fragment_container, fragment)
//                    .commit();
//        }

        if (fragment == null) {
            fragment = ShowTimetableFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.timetable_fragment_container, fragment)
                    .commit();
        }
    }
}
