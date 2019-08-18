package com.example.timetablerapp.timetable.schedule;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.example.timetablerapp.timetable.dialog.ScheduleTimerActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static android.app.Notification.VISIBILITY_PRIVATE;

/**
 * 29/07/19 -bernard
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScheduleTimerJobSchedule extends JobService {
    private static final String TAG = ScheduleTimerJobSchedule.class.getSimpleName();

    private String notificationContent = "";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: Job started");

        boolean isJobScheduled = MainApplication.getSharedPreferences().getBoolean(Constants.SCHEDULE, false);

        if (isJobScheduled) {
            requestTimer(jobParameters);

            // show that registration is scheduled
//            MainApplication.getSharedPreferences().edit().putBoolean(Constants.SCHEDULE, true).apply();
        } else {
            jobFinished(jobParameters, false);
        }

        return true;
    }

    private void requestTimer(JobParameters jobParameters) {
        Call<DeadlineSettings> call = RetrofitClient.getRetrofit()
                .create(TimerApi.class)
                .getRegistrationSchedule();

        call.enqueue(new Callback<DeadlineSettings>() {
            @Override
            public void onResponse(Call<DeadlineSettings> call, Response<DeadlineSettings> response) {
                if (response.isSuccessful()) {
                    String startDate = response.body().getStartDate();
                    String deadline = response.body().getDeadline();

                    formatNotification(startDate, deadline);
                } else {
                    Log.d(TAG, "onResponse: Error no response");
                }
                jobFinished(jobParameters, true);
            }

            @Override
            public void onFailure(Call<DeadlineSettings> call, Throwable throwable) {
                Log.e(TAG, "onFailure: Error check logs", throwable);
                jobFinished(jobParameters, true);
            }
        });
    }

    private void formatNotification(String start, String end) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

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

                    notificationContent = "Start date: " + start + "End Date: " + end;
                    showNotification(notificationContent);
                } else {
                    notificationContent = DateFormat.format("dd:HH:mm:ss", todayInMillis - deadlineInMillis).toString();
                    showNotification(notificationContent);

                    // Start intent service to handle timers on the notification.
                    Intent intentService = new Intent(this, ScheduleTimerIntentService.class);
                    intentService.putExtra(Constants.NOTIFICATION_CONTENT, notificationContent);
                    startService(intentService);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showNotification(String notificationContent) {
        Intent intent = new Intent(this, ScheduleTimerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent snoozeIntent = new Intent(this, ScheduleTimerReceiver.class);
        snoozeIntent.setAction(Constants.ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);

        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, MainApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_schedule)
                .setContentTitle("Scheduled Unit Registration")
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

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobFinished(jobParameters, true);
        return true;
    }
}
