package com.example.timetablerapp.timetable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.login.LoginActivity;
import com.example.timetablerapp.register_units.RegisterUnitsActivity;
import com.example.timetablerapp.settings.SettingsActivity;
import com.example.timetablerapp.show_units.RegisteredUnitsActivity;
import com.example.timetablerapp.timetable.chat.ChatSectionFragment;
import com.example.timetablerapp.timetable.dialog.ScheduleRegistration;
import com.example.timetablerapp.timetable.schedule.ScheduleTimerIntentService;
import com.example.timetablerapp.timetable.schedule.NotificationIntentService;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 08/05/19 -bernard
 */
public class TimetableActivity extends AppCompatActivity implements ScheduleRegistration.OnClickListener,UnitView {
    private static final String TAG = TimetableActivity.class.getSimpleName();

    private SimpleDateFormat sf;
    private Date date;
    private Timer timer;
    private UnitsPresenter presenter;
    private Bitmap bitmap;

    private Toolbar toolbar;
    private TextView txtUsername, txtUserType, txtUserId;
    private TextView txtTimer, txtTimetableTimer;
    private CircleImageView circleImageView;

    private FrameLayout frameTimetable;

    private Fragment fragment;

//    private boolean isJobScheduled = false;

    private String userType = "", username = "", userId = "";
    private String deadline = "", startDate = "";
    private String strTimer = "";
    private String fileName = "";

    private long timeRemaining = 0;

    private boolean isUnitRegistrationScheduled = false;
    private boolean isTimeAdded = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();
        isUnitRegistrationScheduled = MainApplication.getSharedPreferences().getBoolean(Constants.SCHEDULE, false);

        presenter = new UnitsPresenter(this, MainApplication.getUnitRepo());

        isTimeAdded = MainApplication.getSharedPreferences().getBoolean(Constants.IS_TIME_ADDED, false);

        startDate = MainApplication.getSharedPreferences().getString(Constants.START_DATE, "");
        deadline = MainApplication.getSharedPreferences().getString(Constants.END_DATE, "");

        sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        //check start date
        if (!startDate.isEmpty()) {
            try {
                date = sf.parse(startDate);

                if (date.getTime() > Calendar.getInstance().getTimeInMillis()) {
                    startService(
                            new Intent(this, NotificationIntentService.class)
                                    .putExtra("registration_start_timer", sf.format(date))
                    );
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // check deadline date
            if (!deadline.isEmpty()) {
                try {
                    date = sf.parse(deadline);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (isUnitRegistrationScheduled) {
                    try {
                        timeRemaining = sf.parse(deadline).getTime() - Calendar.getInstance().getTimeInMillis();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    startTimer();
                } //else {
//                    if (date.getTime() > Calendar.getInstance().getTimeInMillis()) {
//                        isUnitRegistrationScheduled = true;
//                        MainApplication.getSharedPreferences().edit()
//                                .putBoolean(Constants.SCHEDULE, isUnitRegistrationScheduled)
//                                .apply();
//
//                        startTimer();
//                    }
//                }
            }
        }

        if (timeRemaining == 0) {
            frameTimetable.setVisibility(View.VISIBLE);
            txtTimetableTimer.setVisibility(View.GONE);
        }

        fileName = userId + " " + username + ".png";

        // get uri
        Uri uri = Uri.parse("content://com.example.timetablerapp/" + fileName);
        File file = new File(this.getFilesDir(), uri.getPath());
        String filepath = file.getPath();

        bitmap = BitmapFactory.decodeFile(filepath);

        if (bitmap != null) {
            circleImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        // Start intent service to handle timers on the notification.
        Intent intentService = new Intent(this, ScheduleTimerIntentService.class);
        startService(intentService);

        txtUserId = findViewById(R.id.text_user_id);
        txtUsername = findViewById(R.id.text_user_name);
        txtUserType = findViewById(R.id.text_user_type);
        txtTimer = findViewById(R.id.text_scheduled_timer);
        txtTimetableTimer = findViewById(R.id.text_timetable_schedule);

        circleImageView = findViewById(R.id.circle_view);

        frameTimetable = findViewById(R.id.timetable_fragment_container);

        userType = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");
        txtUserType.setText(userType);
        username = MainApplication.getSharedPreferences().getString(Constants.USERNAME, "");
        txtUsername.setText(username);

        userId = MainApplication.getSharedPreferences().getString(Constants.USER_ID, "");
        txtUserId.setText(userId);

        toolbar = findViewById(R.id.toolbar_user_details);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);

        fragment = getSupportFragmentManager().findFragmentById(R.id.timetable_fragment_container);

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

//        fragment = getSupportFragmentManager().findFragmentById(R.id.chat_fragment_container);
//
//        if (fragment == null) {
//            fragment = new ChatSectionFragment();
//
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.chat_fragment_container, fragment)
//                    .commit();
//        }
    }

    private void startTimer() {
//        timeRemaining = date.getTime() - Calendar.getInstance().getTimeInMillis();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                isUnitRegistrationScheduled = MainApplication.getSharedPreferences().getBoolean(Constants.SCHEDULE, false);
                isTimeAdded = MainApplication.getSharedPreferences().getBoolean(Constants.IS_TIME_ADDED, false);

                timeRemaining -= 1000;
                int days = (int) timeRemaining / 86400000;
                long remainder = timeRemaining % 86400000;
                int hours = (int) remainder / 3600000;
                remainder = remainder % 3600000;
                int minutes = (int) remainder / 60000;
                remainder = remainder % 60000;
                int seconds = (int) remainder / 1000;

                Log.d(TAG, "run: Remainder " + remainder % 1000);

                // Format timer
                strTimer = days > 9 ? "" + days + ":" : "0" + days + ":";
                strTimer += hours > 9 ? "" + hours + ":" : "0" + hours + ":";
                strTimer += minutes > 9 ? "" + minutes + ":" : "0" + minutes + ":";
                strTimer += seconds > 9 ? "" + seconds : "0" + seconds;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isUnitRegistrationScheduled && !isTimeAdded) {
                            strTimer = "Registration ends in\n" + strTimer;
                            txtTimer.setVisibility(View.VISIBLE);
                            txtTimer.setText(strTimer);
                        } else {
                            strTimer = "Timetable will be available in\n" + strTimer;
                            txtTimetableTimer.setText(strTimer);
                        }
                    }
                });

                if (timeRemaining < 1000 && isTimeAdded) {
                    timer.cancel();
                } else if (timeRemaining < 1000 && !isTimeAdded) {
                    isUnitRegistrationScheduled = false;

                    MainApplication.getSharedPreferences().edit()
                            .putBoolean(Constants.SCHEDULE, isUnitRegistrationScheduled)
                            .apply();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txtTimer.setVisibility(View.GONE);
                        }
                    });

                    timeRemaining = TimeUnit.MINUTES.toMillis(5);

                    MainApplication.getSharedPreferences().edit()
                            .putString(Constants.END_DATE, DateFormat.format("dd-MM-yyyy HH:mm:ss", timeRemaining - Calendar.getInstance().getTimeInMillis()).toString())
                            .apply();

                    isTimeAdded = true;

                    MainApplication.getSharedPreferences().edit()
                            .putBoolean(Constants.IS_TIME_ADDED, isTimeAdded)
                            .apply();
                }
            }

            @Override
            public boolean cancel() {
                timer.purge();
                return super.cancel();
            }

//            schedu
        }, 0, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (userType.equalsIgnoreCase("admin")) {
            menu.add(0,102,0, R.string.schedule_registration);
        }

        if (userType.equalsIgnoreCase("student") ||
                userType.equalsIgnoreCase("lecturer") ||
                userType.equalsIgnoreCase("admin")) {
            if (isUnitRegistrationScheduled || userType.equalsIgnoreCase("admin")) {
                menu.add(0, 103, 1, "Register Units");
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
//                MainApplication.getSharedPreferences().edit().putString(Constants.ROLE, "").apply();
                startActivity(new Intent(this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();

                // remove session
                MainApplication.getSharedPreferences().edit()
                        .putBoolean(Constants.IS_LOGGED_IN, false)
                        .putBoolean(Constants.IS_TIME_ADDED, false)
                        .putBoolean(Constants.SCHEDULE, false)
                        .putString(Constants.USER_ID, "")
                        .putString(Constants.START_DATE, "")
                        .putString(Constants.END_DATE, "")
                        .putString(Constants.USERNAME, "")
                        .putInt(Constants.NOTIFICATION_ID, 0)
                        .apply();
                break;
            case R.id.show_registered_units:
                startActivity(new Intent(this, RegisteredUnitsActivity.class));
                break;
            case R.id.show_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case 102:
                DialogFragment dialog = new ScheduleRegistration();
                dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
                break;
            case 103:
                startActivity(new Intent(this, RegisterUnitsActivity.class));
                break;
            default:
                Toast.makeText(this, "No action to perform", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void onClick(String startDate, String deadline) {
        Log.d(TAG, "onClick: Starting data:" + startDate + " Deadline: " + deadline);

        MainApplication.getSharedPreferences().edit()
                .putString(Constants.START_DATE, startDate)
                .putString(Constants.END_DATE, deadline)
                .apply();

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
//        sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

//        if (message.startsWith("Unit") || message.startsWith("unit")) {
//            try {
//                timeRemaining = sf.parse(deadline).getTime() - Calendar.getInstance().getTimeInMillis();
//                startTimer();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
