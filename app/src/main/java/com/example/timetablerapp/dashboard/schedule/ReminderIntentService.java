package com.example.timetablerapp.dashboard.schedule;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.ReminderDialogActivity;
import com.example.timetablerapp.data.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * start the date dialog
 *
 * 14/06/19 -bernard
 */
public class ReminderIntentService extends IntentService {
    private static final String TAG = ReminderIntentService.class.getSimpleName();

    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Calendar calendar = Calendar.getInstance();

    private Button btnDate;

    public ReminderIntentService() {
        super("Reminder Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        Intent activityIntent = new Intent(this, ReminderDialogActivity.class);
//        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        startActivity(activityIntent);

        showDialog(getApplicationContext(), "Set Reminder");
    }

    private void showDialog(Context applicationContext, String set_reminder) {
        View view = LayoutInflater.from(applicationContext).inflate(R.layout.activity_reminder_dialog, null, false);

        btnDate = view.findViewById(R.id.text_date);

        AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext, R.style.Theme_Dialogs)
                .setTitle(set_reminder)
                .setView(view)
                .setPositiveButton("Set Reminder", (dialogInterface, i) -> {
                    onButtonClick(applicationContext);

                    String reminderDateStr = calendar.getTime().toString();
                    MainApplication.getSharedPreferences().edit()
                            .putString(Constants.REMINDER, reminderDateStr)
                            .putBoolean(Constants.REMINDER_SET, true)
                            .apply();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        builder.create();
        builder.show();
    }

    /**
     * Define button click events.
     */
    private void onButtonClick(Context applicationContext) {
        btnDate.setOnClickListener(view1 -> {
            onTimeSetListener = (timePicker, hour, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);


                MainApplication.getSharedPreferences().edit().putString(Constants.REMINDER, new SimpleDateFormat(Constants.DATE_FORMAT).format(calendar.getTime())).apply();

                Intent intent = new Intent(applicationContext, NotificationIntentService.class);
                AlarmManager manager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getService(applicationContext, 0, intent, 0);
                manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), TimeUnit.HOURS.toMillis(6), pendingIntent);
            };

            onDateSetListener = (datePicker, year, month, day) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                TimePickerDialog dialog = new TimePickerDialog(
                        applicationContext,
                        R.style.Theme_Dialogs,
                        onTimeSetListener,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        true
                );

                dialog.show();
            };

            DatePickerDialog dialog = new DatePickerDialog(
                    applicationContext,
                    R.style.Theme_Dialogs,
                    onDateSetListener,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );

            dialog.show();
        });
    }
}
