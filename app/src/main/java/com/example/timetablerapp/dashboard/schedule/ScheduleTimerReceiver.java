package com.example.timetablerapp.dashboard.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.timetablerapp.data.Constants;

import java.util.Objects;

/**
 * 13/06/19 -bernard
 */
public class ScheduleTimerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), Constants.ACTION_SNOOZE)) {
            Intent intentService = new Intent(context, ReminderIntentService.class);
            context.startService(intentService);
        }
    }
}
