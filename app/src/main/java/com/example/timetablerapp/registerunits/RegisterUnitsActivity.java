package com.example.timetablerapp.registerunits;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.login.LoginActivity;
import com.example.timetablerapp.registerunits.adapter_utils.UnitsAdapter;

import java.util.ArrayList;
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

        presenter.getDepartment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_units);
        Log.d(TAG, "onCreate");

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

        btnRegisterUnits = findViewById(R.id.button_register_units);
        btnRegisterUnits.setOnClickListener(view -> presenter.submitUnits(userId, unitList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                break;
        }
        return true;
    }

    @Override
    public void setUnits(List<Unit> units) {
        adapter = new UnitsAdapter(units, new UnitsAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(Unit unit) {
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
        finish();
    }
}
