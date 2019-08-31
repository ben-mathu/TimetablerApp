package com.example.timetablerapp.dashboard.schedule;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.settings.model.DeadlineSettings;
import com.example.timetablerapp.data.timer_schedule.TimerApi;
import com.example.timetablerapp.data.utils.RetrofitClient;
import com.example.timetablerapp.dashboard.TimetableActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static android.app.Notification.VISIBILITY_PRIVATE;

/**
 * 13/06/19 -bernard
 */
public class ScheduleTimerIntentService extends IntentService {
    private static final String TAG = ScheduleTimerIntentService.class.getSimpleName();

    private NotificationCompat.Builder notification;
    private Timer timer;

    private String daysStr, hoursStr, minutesStr, secondsStr;
    private String notificationContent;

    private boolean isRegistrationScheduled = false;
    private boolean isDeadlineReached = false;

    private int notificationId = 0;

    public ScheduleTimerIntentService() {
        super("Timer Scheduler");
        timer = new Timer();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: Service started");
        notificationId = MainApplication.getSharedPreferences().getInt(Constants.NOTIFICATION_ID, 0);

//        String timeRemaining = intent.getStringExtra(Constants.NOTIFICATION_CONTENT);
//
//        Pattern pattern = Pattern.compile("^(\\d+):(\\d+):(\\d+):(\\d+)$");
//
//        Matcher matcher = pattern.matcher(timeRemaining);
//
//        if (matcher.find()) {
//            daysStr = matcher.group(1);
//            hoursStr = matcher.group(2);
//            minutesStr = matcher.group(3);
//            secondsStr = matcher.group(4);
//        }

        isDeadlineReached = MainApplication.getSharedPreferences().getBoolean(Constants.IS_TIME_REACHED, false);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                isRegistrationScheduled = MainApplication.getSharedPreferences().getBoolean(Constants.SCHEDULE, false);
                String role = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");
                if (!isRegistrationScheduled && role.equalsIgnoreCase("lecturer")) {
                    String userId = MainApplication.getSharedPreferences().getString(Constants.USER_ID, "");
                    Call<DeadlineSettings> call = RetrofitClient.getRetrofit()
                            .create(TimerApi.class)
                            .getRegistrationSchedule(userId);

                    call.enqueue(new Callback<DeadlineSettings>() {
                        @Override
                        public void onResponse(Call<DeadlineSettings> call, Response<DeadlineSettings> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getDeadline() != null && response.body().getStartDate() != null) {
                                    String startDate = response.body().getStartDate();
                                    String deadline = response.body().getDeadline();

                                    formatNotification(startDate, deadline);
                                }
                            } else {
                                Log.d(TAG, "onResponse: Error no response");
                            }
                        }

                        @Override
                        public void onFailure(Call<DeadlineSettings> call, Throwable throwable) {
                            Log.e(TAG, "onFailure: Error check logs", throwable);
                        }
                    });
                } else {
                    Call<DeadlineSettings> call = RetrofitClient.getRetrofit()
                            .create(TimerApi.class)
                            .getRegistrationSchedule();

                    call.enqueue(new Callback<DeadlineSettings>() {
                        @Override
                        public void onResponse(Call<DeadlineSettings> call, Response<DeadlineSettings> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getDeadline() != null && response.body().getStartDate() != null) {
                                    String startDate = response.body().getStartDate();
                                    String deadline = response.body().getDeadline();

                                    formatNotification(startDate, deadline);
                                }
                            } else {
                                Log.d(TAG, "onResponse: Error no response");
                                MainApplication.getSharedPreferences().edit()
                                        .putBoolean(Constants.SCHEDULE, false)
                                        .apply();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeadlineSettings> call, Throwable throwable) {
                            Log.e(TAG, "onFailure: Error check logs", throwable);
                        }
                    });
                }
            }
        }, 0, TimeUnit.MINUTES.toMillis(5));
    }

    private void formatNotification(String start, String end) {
        // show that time
        MainApplication.getSharedPreferences().edit()
                .putBoolean(Constants.SCHEDULE, true)
                .apply();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            // get today's date
            Date today = calendar.getTime();
            long todayInMillis = today.getTime();

            Date startDate = sf.parse(start);
            long startDateInMillis = startDate.getTime();

            Date deadline = sf.parse(end);
            long deadlineInMillis = deadline.getTime();

            // compare dates
            if (deadlineInMillis > todayInMillis) {
                if (startDateInMillis > todayInMillis) {
                    notificationContent = start + " " + end;

                    Log.d(TAG, "formatNotification: " + notificationContent);
                    MainApplication.getSharedPreferences().edit()
                            .putString(Constants.START_DATE, start)
                            .putString(Constants.END_DATE, end)
                            .putBoolean(Constants.SCHEDULE, true)
                            .apply();
                    showNotification("Scheduled time for Registration: start " + start + "end " + end);
                } else {
                    // show that time is reached
                    MainApplication.getSharedPreferences().edit()
                            .putBoolean(Constants.IS_TIME_REACHED, false)
                            .apply();

                    notificationContent = DateFormat.format("dd-MM-yyyy HH:mm:ss", deadlineInMillis).toString();
                    Log.d(TAG, "formatNotification: " + notificationContent);

                    // save time state
                    MainApplication.getSharedPreferences().edit()
                            .putString(Constants.START_DATE, start)
                            .putString(Constants.END_DATE, end)
                            .putBoolean(Constants.SCHEDULE, true)
                            .apply();
                    showNotification("Deadline is on: " + notificationContent);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showNotification(String notificationContent) {
        Intent intent = new Intent(this, TimetableActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent snoozeIntent = new Intent(this, ReminderIntentService.class);
        snoozeIntent.setAction(Constants.ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);

        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, MainApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_schedule)
                .setContentTitle("Scheduled unit registration")
                .setContentText(notificationContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_snooze, "Remind me", snoozePendingIntent)
                .setVisibility(VISIBILITY_PRIVATE)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        int notificationId = new Random().nextInt(10) + 20;
        MainApplication.getSharedPreferences().edit().putInt(Constants.NOTIFICATION_ID, notificationId).apply();

        notificationManager.notify(notificationId, notification.build());
    }
}
