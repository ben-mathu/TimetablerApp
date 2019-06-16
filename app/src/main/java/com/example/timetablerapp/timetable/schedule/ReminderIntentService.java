package com.example.timetablerapp.timetable.schedule;

import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

/**
 * 14/06/19 -bernard
 */
public class ReminderIntentService extends IntentService {

    public ReminderIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Reminder")
                .setPositiveButton("ok", (dialogInterface, i) -> {

                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    
                })
                .setCancelable(false);

        dialog.create();
        dialog.show();
    }
}
