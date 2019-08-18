package com.example.timetablerapp.timetable.schedule;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.timetable.TimetableActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static android.app.Notification.VISIBILITY_PRIVATE;

/**
 * 14/08/19 -bernard
 */
public class NotificationIntentService extends IntentService {
    public static final String TAG = NotificationIntentService.class.getSimpleName();

    private SimpleDateFormat sf;

    public NotificationIntentService() {
        super(TAG);

        sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Initialize constraints, ie deadline or scheduled time to registration
        if (intent != null) {
            String startTime = intent.getStringExtra("registration_start_timer");
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (sf.parse(startTime).getTime() > Calendar.getInstance().getTimeInMillis()) {
                            showNotification("Unit registrations have started.");

                            timer.cancel();
                            timer.purge();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, TimeUnit.HOURS.toMillis(6));
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
