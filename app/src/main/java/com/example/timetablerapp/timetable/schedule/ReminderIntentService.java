package com.example.timetablerapp.timetable.schedule;

import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.example.timetablerapp.timetable.dialog.ReminderDialogActivity;

/**
 * 14/06/19 -bernard
 */
public class ReminderIntentService extends IntentService {

    public ReminderIntentService() {
        super("Reminder Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent activityIntent = new Intent(this, ReminderDialogActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(activityIntent);
    }
}
