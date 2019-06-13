package com.example.timetablerapp.timetable.schedule;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.timetablerapp.data.Constants;

/**
 * 13/06/19 -bernard
 */
public class ScheduleTimerIntentService extends IntentService {

    public ScheduleTimerIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String timeRemaining = intent.getStringExtra(Constants.NOTIFICATION_CONTENT);

        // TODO : get substrings of timeRemaining
    }
}
