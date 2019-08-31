package com.example.timetablerapp.dashboard.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 13/06/19 -bernard
 */
public class ScheduleTimerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, ReminderIntentService.class);
        context.startService(intentService);
    }
}
