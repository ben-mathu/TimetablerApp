package com.example.timetablerapp.dashboard.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;

import java.util.Calendar;

/**
 * 19/06/19 -bernard
 */
public class ReminderDialogActivity extends Dialog {

    private TextView txtDate, txtTime;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    private String date, time;

    protected ReminderDialogActivity(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_dialog);

        txtDate = findViewById(R.id.text_date);

        txtDate.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    getContext(),
                    android.R.style.Theme_Holo_Light_Dialog,
                    onDateSetListener,
                    year, month, day);

            dialog.show();
        });

        onDateSetListener = (datePicker, year, month, day) -> {
            date = day + "-" + month + "-" + year;

            txtDate.setText(date);
        };

        txtTime.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();

            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(
                    getContext(),
                    onTimeSetListener,
                    hour, minute, true);

            dialog.show();
        });

        onTimeSetListener = (timePicker, hour, minute) -> {
            time = hour + ":" + minute;

            txtTime.setText(time);
        };

        MainApplication.getSharedPreferences().edit().putString(Constants.REMINDER, date + " " + time).apply();
    }
}
