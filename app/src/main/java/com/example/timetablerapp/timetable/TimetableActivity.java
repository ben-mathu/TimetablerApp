package com.example.timetablerapp.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.login.LoginActivity;
import com.example.timetablerapp.register_units.RegisterUnitsPresenter;
import com.example.timetablerapp.timetable.dialog.ScheduleRegistration;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class TimetableActivity extends AppCompatActivity implements ScheduleRegistration.OnClickListener,UnitView {
    private static final String TAG = TimetableActivity.class.getSimpleName();

    private UnitsPresenter presenter;
    private Toolbar toolbar;

    private String userType = "";

    @Override
    protected void onStart() {
        super.onStart();

        presenter = new UnitsPresenter(this, MainApplication.getUnitRepo());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        userType = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (userType.equalsIgnoreCase("admin")) {
            menu.add(0,0,0, R.string.schedule_registration);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
            case 0:
                startActivity(ScheduleRegistration.newIntent(this, this));
        }
        return true;
    }

    @Override
    public void onClick(String startDate, String deadline) {
        presenter.setDeadline(startDate, deadline);
    }

    @Override
    public void setUnits(List<Unit> units) {

    }

    @Override
    public void showTimetable(List<Timetable> timetablelist) {

    }

    @Override
    public void setSalt(String salt) {

    }

    @Override
    public void showMessage(String message) {

    }
}
