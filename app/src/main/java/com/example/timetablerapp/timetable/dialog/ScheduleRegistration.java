package com.example.timetablerapp.timetable.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TextView;

import com.example.timetablerapp.R;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 12/06/19 -bernard
 */
public class ScheduleRegistration extends AppCompatActivity {
    private static final String TAG = ScheduleRegistration.class.getSimpleName();

    private Calendar calendar;

    private OnClickListener onClick;

    private Button btnStartDate, btnDeadline;
    private TextView txtCancel, txtOk;

    private String startDate = "", deadline = "";

    public static Intent newIntent(Context context, OnClickListener onClick) {
        Intent intent = new Intent(context, ScheduleRegistration.class);
        intent.putExtra("interface", onClick);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_unit_registration);

        onClick = (OnClickListener) getIntent().getSerializableExtra("interface");

        btnStartDate = findViewById(R.id.button_start_date);
        btnStartDate.setOnClickListener(view -> {
            calendar = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener datePickerDialog = (view1, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                btnStartDate.setText(DateFormat.format("yyyy-MM-dd", calendar.getTimeInMillis()));
            };

            DatePickerDialog d = new DatePickerDialog(ScheduleRegistration.this, datePickerDialog,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            d.show();
        });

        btnDeadline = findViewById(R.id.button_deadline);
        btnDeadline.setOnClickListener(view -> {
            calendar = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener datePickerDialog = (view12, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                btnDeadline.setText(DateFormat.format("yyyy-MM-dd", calendar.getTimeInMillis()));
            };
            DatePickerDialog d = new DatePickerDialog(ScheduleRegistration.this, datePickerDialog,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            d.show();
        });

        txtCancel.setOnClickListener(view -> {
            finish();
        });

        txtOk.setOnClickListener(view -> {
            startDate = btnStartDate.getText().toString();
            deadline = btnDeadline.getText().toString();

            onClick.onClick(startDate, deadline);
        });
    }

    public interface OnClickListener extends Serializable {
        void onClick(String startDate, String deadline);
    }
}
