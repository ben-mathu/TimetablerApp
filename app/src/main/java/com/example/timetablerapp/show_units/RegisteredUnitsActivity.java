package com.example.timetablerapp.show_units;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.register_units.adapter_utils.UnitsAdapter;
import com.example.timetablerapp.timetable.TimetableActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 22/08/19 -bernard
 */
public class RegisteredUnitsActivity extends AppCompatActivity implements RegisteredUnitsView {
    private static final String TAG = RegisteredUnitsActivity.class.getSimpleName();

    private RegisteredUnitsPresenter presenter;
    private UnitsAdapter unitsAdapter;
    private List<Unit> unitList;

    private TextView txtUserId, txtUsername, txtUserType;
    private Button btnRemoveUnits;
    private RecyclerView recyclerView;

    private String username;
    private String userType;
    private String userId;

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new RegisteredUnitsPresenter(this, this, MainApplication.getUnitRepo());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_units);
        Log.d(TAG, "onCreate");

        presenter = new RegisteredUnitsPresenter(this, this, MainApplication.getUnitRepo());

        unitList = new ArrayList<>();

        txtUserId = findViewById(R.id.text_user_id);
        txtUsername = findViewById(R.id.text_user_name);
        txtUserType = findViewById(R.id.text_user_type);

        userType = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");
        username = MainApplication.getSharedPreferences().getString(Constants.USERNAME, "");

        txtUserType.setText(userType);
        txtUsername.setText(username);

        userId = MainApplication.getSharedPreferences().getString(Constants.USER_ID, "");
        txtUserId.setText(userId);

        recyclerView = findViewById(R.id.unit_to_register);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnRemoveUnits = findViewById(R.id.button_register_units);
        btnRemoveUnits.setOnClickListener(view -> presenter.submitUnits(userId, unitList, unitsAdapter));

        boolean isScheduleSet = MainApplication.getSharedPreferences().getBoolean(Constants.SCHEDULE, false);
        if (!isScheduleSet) {
            btnRemoveUnits.setVisibility(View.GONE);
        }

        presenter.getUnits(userId, userType);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startTimetableActivity() {
        startActivity(new Intent(this, TimetableActivity.class));
        finish();
    }

    @Override
    public void setUnits(List<Unit> units) {
        unitsAdapter = new UnitsAdapter(units, new UnitsAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(Unit unit) {
                unitList.add(unit);
            }

            @Override
            public void onItemUnchecked(Unit unit) {
                unitList.remove(unit);
            }
        });

        recyclerView.setAdapter(unitsAdapter);
    }
}
