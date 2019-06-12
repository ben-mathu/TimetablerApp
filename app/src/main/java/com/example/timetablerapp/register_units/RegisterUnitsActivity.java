package com.example.timetablerapp.register_units;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

/**
 * 22/05/19 -bernard
 */
public class RegisterUnitsActivity extends AppCompatActivity implements RegisterUnitView {
    private static final String TAG = RegisterUnitsActivity.class.getSimpleName();

    private RegisterUnitsPresenter presenter;
    private UnitsAdapter adapter;
    private List<Unit> unitList;

    private TextView txtUserId, txtUsername, txtUserType;
    private RecyclerView recyclerView;
    private Button btnRegisterUnits;

    private String username;
    private String userType;
    private String userId;

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new RegisterUnitsPresenter(this, this, MainApplication.getDepRepo(), MainApplication.getUnitRepo());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_units);

        txtUserId = findViewById(R.id.text_user_id);
        txtUsername = findViewById(R.id.text_user_name);
        txtUserType = findViewById(R.id.text_user_type);

        userType = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");
        username = MainApplication.getSharedPreferences().getString(Constants.USERNAME, "");

        txtUserType.setText(userType);
        txtUsername.setText(username);

        if (userType.equalsIgnoreCase("lecturer")) {
            userId = MainApplication.getSharedPreferences().getString(Constants.LECTURER_ID, "");
            txtUserId.setText(userId);
        } else if (userType.equalsIgnoreCase("student")) {
            userId = MainApplication.getSharedPreferences().getString(Constants.STUDENT_ID, "");
            txtUserId.setText(userId);
        }

        recyclerView = findViewById(R.id.recycler_timetable);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnRegisterUnits = findViewById(R.id.button_register);
        btnRegisterUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.submitUnits(userId, unitList);
            }
        });

        presenter.getDepartment();

    }

    @Override
    public void setUnits(List<Unit> units) {
        adapter = new UnitsAdapter(units, new UnitsAdapter.OnItemCheckedListener() {
            @Override
            public void onItemCheck(Unit unit) {
                unitList.add(unit);
            }

            @Override
            public void onItemUnchecked(Unit unit) {
                unitList.remove(unit);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startTimetableActivity() {
        Intent intent = new Intent(this, TimetableActivity.class);
        startActivity(intent);
    }
}
