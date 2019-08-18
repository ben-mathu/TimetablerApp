package com.example.timetablerapp.login;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainActivity;
import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.settings.model.DeadlineSettings;
import com.example.timetablerapp.signup.SignUpActivity;
import com.example.timetablerapp.timetable.TimetableActivity;
import com.example.timetablerapp.timetable.dialog.ScheduleTimerActivity;
import com.example.timetablerapp.timetable.schedule.ScheduleTimerIntentService;
import com.example.timetablerapp.timetable.schedule.ScheduleTimerReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static android.app.Notification.VISIBILITY_PRIVATE;
import static android.app.Notification.VISIBILITY_PUBLIC;

/**
 * 06/05/19 -bernard
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter loginPresenter;

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private TextView txtSignUp, txtClearRole;

    private boolean isLoggedIn = false;
    private String notificationContent = "";
    private String role;

    @Override
    protected void onStart() {
        super.onStart();
        isLoggedIn = MainApplication.getSharedPreferences().getBoolean(Constants.IS_LOGGED_IN, false);

        // check if session is saved
        if (isLoggedIn) {
            startActivity(new Intent(this, TimetableActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize LoginPresenter
        loginPresenter = new LoginPresenter(MainApplication.getStudentRepository(), MainApplication.getLecturerRepo(), this);

        edtUsername = findViewById(R.id.edit_username);
        edtPassword = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.button_login);

        txtSignUp = findViewById(R.id.text_register);
        txtSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });

        txtClearRole = findViewById(R.id.text_clear_role);
        role = txtClearRole.getText() + ": " + MainApplication.getSharedPreferences().getString(Constants.ROLE, "");

        txtClearRole.setText(role);

        txtClearRole.setOnClickListener(v -> {
            MainApplication.getSharedPreferences().edit()
                    .putString(Constants.ROLE, "")
                    .apply();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            loginPresenter.login();
        });
    }

    @Override
    public boolean startProgressDialog() {
        return false;
    }

    @Override
    public void showUsernameError(int s) {
        String message = getResources().getString(s);
        edtUsername.setError(message);
    }

    @Override
    public void showPasswordError(int s) {
        String message = getResources().getString(s);
        edtPassword.setError(message);
    }

    @Override
    public String getUsername() {
        String username = edtUsername.getText().toString();
        MainApplication.getSharedPreferences().edit().putString(Constants.USERNAME, username).apply();
        return username;
    }

    @Override
    public String getPassword() {
        return edtPassword.getText().toString();
    }

    @Override
    public void startMainActivity() {
        startActivity(MainActivity.newIntent(this));
    }

    @Override
    public String getUserRole() {
        return null;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startTimetableActivity() {
        //Save session
        MainApplication.getSharedPreferences().edit()
                .putBoolean(Constants.IS_LOGGED_IN, true)
                .apply();

        startActivity(
                new Intent(this, TimetableActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
        );
        finish();

        loginPresenter.fetchSettings();
    }

//    @Override
//    public void configureSettings(DeadlineSettings settings) {
//        Calendar calendar = Calendar.getInstance();
//
//        String startDateStr = settings.getStartDate();
//        String deadlineStr = settings.getDeadline();
//
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//
//        try {
//            // get today's date
//            Date today = calendar.getTime();
//            long todayInMillis = today.getTime();
//
//            Date startDate = sf.parse(startDateStr);
//            long startDateInMillis = startDate.getTime();
//
//            Date deadline = sf.parse(deadlineStr);
//            long deadlineInMillis = deadline.getTime();
//
//            // compare dates
//            if (deadlineInMillis > todayInMillis) {
//                if (startDateInMillis > todayInMillis) {
//
//                    notificationContent = "Start date: " + startDateStr + "End Date: " + deadlineStr;
//                    showNotification(notificationContent);
//                } else {
//                    notificationContent = DateFormat.format("dd:HH:mm:ss", todayInMillis - deadlineInMillis).toString();
//                    showNotification(notificationContent);
//
//                    // Start intent service to handle timers on the notification.
//                    Intent intentService = new Intent(this, ScheduleTimerIntentService.class);
//                    intentService.putExtra(Constants.NOTIFICATION_CONTENT, notificationContent);
//                    startService(intentService);
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

//    private void showNotification(String notificationContent) {
//        Intent intent = new Intent(this, ScheduleTimerActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        Intent snoozeIntent = new Intent(this, ScheduleTimerReceiver.class);
//        snoozeIntent.setAction(Constants.ACTION_SNOOZE);
//        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
//
//        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);
//
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, MainApplication.CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_schedule)
//                .setContentTitle("Scheduled Unit Registration")
//                .setContentText(notificationContent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .addAction(R.drawable.ic_snooze, "Remind me", snoozePendingIntent)
//                .setVisibility(VISIBILITY_PRIVATE)
//                .setAutoCancel(true);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//        int notificationId = new Random().nextInt(10) + 20;
//        MainApplication.getSharedPreferences().edit().putInt(Constants.NOTIFICATION_ID, notificationId).apply();
//
//        notificationManager.notify(notificationId, notification.build());
//    }
}
