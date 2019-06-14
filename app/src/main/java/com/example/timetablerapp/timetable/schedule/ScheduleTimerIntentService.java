package com.example.timetablerapp.timetable.schedule;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Notification.VISIBILITY_PRIVATE;

/**
 * 13/06/19 -bernard
 */
public class ScheduleTimerIntentService extends IntentService {
    private static final String TAG = ScheduleTimerIntentService.class.getSimpleName();

    private NotificationCompat.Builder notification;

    private MessageHandler messageHandler;

    private String daysStr, hoursStr, minutesStr, secondsStr;
    private String notificationContent;

    private int notificationId = 0;

    public ScheduleTimerIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        notificationId = MainApplication.getSharedPreferences().getInt(Constants.NOTIFICATION_ID, 0);

        String timeRemaining = intent.getStringExtra(Constants.NOTIFICATION_CONTENT);

        Pattern pattern = Pattern.compile("^(\\d+):(\\d+):(\\d+):(\\d+)$");

        Matcher matcher = pattern.matcher(timeRemaining);

        if (matcher.find()) {
            daysStr = matcher.group(1);
            hoursStr = matcher.group(2);
            minutesStr = matcher.group(3);
            secondsStr = matcher.group(4);
        }

        Thread thread = new Thread(
                new Timerseconds(
                        Integer.parseInt(secondsStr),
                        Integer.parseInt(minutesStr),
                        Integer.parseInt(hoursStr),
                        Integer.parseInt(daysStr)
                )
        );

        messageHandler = new MessageHandler();
    }

    public class Timerseconds implements Runnable {
        int seconds = 0;
        private final int minutes;
        private final int hours;
        private final int days;

        public Timerseconds(int seconds, int minutes, int hours, int days) {
            this.seconds = seconds;
            this.minutes = minutes;
            this.hours = hours;
            this.days = days;
        }

        @Override
        public void run() {
            for (int i = days; i >= 0; i--) {
                for (int j = hours; j >= 0; i--) {
                    for (int k = minutes; k >=0; j--) {
                        for (int m = seconds; m >= 0; m--) {
                            try {
                                Thread.sleep(1000);
                                if (m < 10)
                                    secondsStr = 0 + String.valueOf(m);
                                else
                                    secondsStr = String.valueOf(m);

                                if (k < 10)
                                    minutesStr = 0 + String.valueOf(k);
                                else
                                    minutesStr = String.valueOf(k);

                                if (j < 10)
                                    hoursStr = 0 + String.valueOf(j);
                                else
                                    hoursStr = String.valueOf(j);

                                if (i < 10)
                                    daysStr = 0 + String.valueOf(i);
                                else
                                    daysStr = String.valueOf(i);

                                notificationContent = daysStr + ":" + hoursStr + ":" + minutesStr + ":" + secondsStr;

                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.NOTIFICATION_CONTENT, notificationContent);

                                Message message = new Message();
                                message.setData(bundle);

                                messageHandler.sendMessage(message);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            notificationContent = msg.getData().getString(Constants.NOTIFICATION_CONTENT);

            notification = new NotificationCompat.Builder(ScheduleTimerIntentService.this, MainApplication.CHANNEL_ID)
                    .setContentText(notificationContent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ScheduleTimerIntentService.this);
            notificationManager.notify(notificationId, notification.build());
        }
    }
}
