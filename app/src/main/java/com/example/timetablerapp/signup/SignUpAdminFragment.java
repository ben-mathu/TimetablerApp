package com.example.timetablerapp.signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.login.LoginActivity;
import com.example.timetablerapp.timetable.TimetableActivity;

import java.util.List;

/**
 * 22/05/19 -bernard
 */
public class SignUpAdminFragment extends Fragment implements SignUpContract.View, View.OnClickListener {

    private SignUpPresenter presenter;

    private TextView txtLogin;
    private EditText edtFName, edtMName, edtLName, edtUsername, edtPassword, edtAdminId;
    private Button btnRegister;

    private String role = "";
    private String dbPassword = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        role = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");

        presenter = new SignUpPresenter(this,
                MainApplication.getDepRepo(),
                MainApplication.getProgRepo(),
                MainApplication.getCampusRepo(),
                MainApplication.getFacultyRepo(),
                MainApplication.getStudentRepository(),
                MainApplication.getLecturerRepo(),
                MainApplication.getAdminRepo());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_admin, container, false);

        txtLogin = view.findViewById(R.id.text_login);
        txtLogin.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        edtAdminId = view.findViewById(R.id.edit_admin_id);
        edtFName = view.findViewById(R.id.edit_first_name);
        edtMName = view.findViewById(R.id.edit_middle_name);
        edtLName = view.findViewById(R.id.edit_last_name);
        edtUsername = view.findViewById(R.id.edit_username);
        edtPassword = view.findViewById(R.id.edit_password);

        btnRegister = view.findViewById(R.id.button_register);
        btnRegister.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Admin admin = new Admin();
        admin.setAdminId(edtAdminId.getText().toString());
        admin.setfName(edtFName.getText().toString());
        admin.setmName(edtMName.getText().toString());
        admin.setlName(edtLName.getText().toString());
        admin.setUsername(edtUsername.getText().toString());
        admin.setPassword(edtPassword.getText().toString());

        LayoutInflater inflater = getLayoutInflater();
        View layoutView = inflater.inflate(R.layout.dialog_db_password, null);

        EditText editText = layoutView.findViewById(R.id.edit_password);
        editText.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        editText.setHintTextColor(ResourcesCompat.getColor(getResources(), R.color.my_kind_of_grey, null));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter Db Password");
        builder.setView(layoutView);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbPassword = editText.getText().toString();
                presenter.registerUser(admin, dbPassword);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public void showFaculties(List<Faculty> faculties) {

    }

    @Override
    public void showCampuses(List<Campus> campuses) {

    }

    @Override
    public void showMessages(String message) {

    }

    @Override
    public void showDepartments(List<Department> departments) {

    }

    @Override
    public void showProgrammes(List<Programme> programmes) {

    }

    @Override
    public void startLoginActivity() {
        startActivity(
                new Intent(getActivity(), LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
        );
        getActivity().finish();
    }
}
