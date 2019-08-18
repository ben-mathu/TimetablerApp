package com.example.timetablerapp.timetable.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.timetablerapp.R;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 12/06/19 -bernard
 */
public class ScheduleRegistration extends DialogFragment {
    private static final String TAG = ScheduleRegistration.class.getSimpleName();

    private Calendar calendar;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private OnClickListener onClick;

    private Button btnStartDate, btnDeadline;
    private TextView txtCancel, txtOk;

    private String startDate = "", deadline = "";

    public ScheduleRegistration() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        onClick = (OnClickListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        calendar = Calendar.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.schedule_unit_registration, null);

        btnStartDate = view.findViewById(R.id.button_start_date);
        btnStartDate.setOnClickListener(v -> {
            // declare time listener
            onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    btnStartDate.setText(DateFormat.format("dd-MM-yyyy HH:mm:ss", calendar.getTimeInMillis()));
                }
            };

            // declare date listener
            onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);

                    // display time picker dialog
                    TimePickerDialog dialog = new TimePickerDialog(
                            getActivity(),
                            R.style.Theme_TimePicker,
                            onTimeSetListener,
                            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                            Calendar.getInstance().get(Calendar.MINUTE),
                            true);

                    dialog.show();
                }
            };

            DatePickerDialog d = new DatePickerDialog(
                    getActivity(),
                    R.style.Theme_DatePicker,
                    onDateSetListener,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            d.show();
        });

        btnDeadline = view.findViewById(R.id.button_deadline);
        btnDeadline.setOnClickListener(v -> {
            // declare time listener
            onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    btnDeadline.setText(DateFormat.format("dd-MM-yyyy HH:mm:ss", calendar.getTimeInMillis()));
                }
            };

            // declare date listener
            onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);

                    // display time picker dialog
                    TimePickerDialog dialog = new TimePickerDialog(
                            getActivity(),
                            R.style.Theme_TimePicker,
                            onTimeSetListener,
                            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                            Calendar.getInstance().get(Calendar.MINUTE),
                            true);

                    dialog.show();
                }
            };

            DatePickerDialog d = new DatePickerDialog(
                    getActivity(),
                    R.style.Theme_DatePicker,
                    onDateSetListener,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            d.show();
        });

        builder.setView(view);

        builder.setPositiveButton("Set", (dialogInterface, i) -> {
            startDate = btnStartDate.getText().toString();
            deadline = btnDeadline.getText().toString();

            onClick.onClick(startDate, deadline);
        }).setNegativeButton("Cancel", (dialogInterface, i) -> dismiss());
        return builder.create();
    }

    public interface OnClickListener extends Serializable {
        void onClick(String startDate, String deadline);
    }
}
