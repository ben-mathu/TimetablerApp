package com.example.timetablerapp.dashboard.dialog;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.schedule.NotificationIntentService;
import com.example.timetablerapp.dashboard.schedule.ReminderIntentService;
import com.example.timetablerapp.data.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * 19/06/19 -bernard
 */
public class ReminderDialogActivity extends DialogFragment {
    private static final String TAG = ReminderIntentService.class.getSimpleName();

    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Calendar calendar = Calendar.getInstance();

    private Button btnDate;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_reminder_dialog, null, false);

        btnDate = view.findViewById(R.id.text_date);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Dialogs)
                .setTitle("Set Reminder")
                .setView(view)
                .setPositiveButton("Set Reminder", (dialogInterface, i) -> {
                    onButtonClick();

                    String reminderDateStr = calendar.getTime().toString();
                    MainApplication.getSharedPreferences().edit()
                            .putString(Constants.REMINDER, reminderDateStr)
                            .putBoolean(Constants.REMINDER_SET, true)
                            .apply();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        return builder.create();
    }

    /**
     * Define button click events.
     */
    private void onButtonClick() {
        btnDate.setOnClickListener(view1 -> {
            onTimeSetListener = (timePicker, hour, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);


                MainApplication.getSharedPreferences().edit().putString(Constants.REMINDER, new SimpleDateFormat(Constants.DATE_FORMAT).format(calendar.getTime())).apply();

                Intent intent = new Intent(getActivity(), NotificationIntentService.class);
                AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getService(getActivity(), 0, intent, 0);
                manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), TimeUnit.HOURS.toMillis(6), pendingIntent);
            };

            onDateSetListener = (datePicker, year, month, day) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                TimePickerDialog dialog = new TimePickerDialog(
                        getActivity(),
                        R.style.Theme_Dialogs,
                        onTimeSetListener,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        true
                );

                dialog.show();
            };

            DatePickerDialog dialog = new DatePickerDialog(
                    getActivity(),
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
