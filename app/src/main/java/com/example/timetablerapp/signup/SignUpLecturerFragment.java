package com.example.timetablerapp.signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 08/05/19 -bernard
 */
public class SignUpLecturerFragment extends Fragment implements View.OnClickListener, SignUpContract.View {
    private SignUpPresenter presenter;

    private TextView txtLogin;
    private EditText edtUserId, edtFName, edtLName, edtMName, edtUsername, edtPassword, edtEmail;
    private Spinner spnDepartments, spnFaculties;
    private Switch switchInSess;
    private Button btnRegister;

    private List<Faculty> faculties;
    private List<Department> departments;

    private Faculty faculty;
    private Department department;

    private String role = "";
    private String depName = "";
    private String facultyName = "";
    private String dbPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        faculties = new ArrayList<>();
        departments = new ArrayList<>();

        presenter = new SignUpPresenter(this,
                MainApplication.getDepRepo(),
                MainApplication.getProgRepo(),
                MainApplication.getCampusRepo(),
                MainApplication.getFacultyRepo(),
                MainApplication.getStudentRepository(),
                MainApplication.getLecturerRepo(),
                MainApplication.getAdminRepo());
        role = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_lecturer, container, false);

//        txtLogin = view.findViewById(R.id.text_login);
//        txtLogin.setOnClickListener(v -> {
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//            Objects.requireNonNull(getActivity()).finish();
//        });

        // Initialize Widgets
//        edtUserId = view.findViewById(R.id.edit_user_id);
//        edtFName = view.findViewById(R.id.edit_first_name);
//        edtLName = view.findViewById(R.id.edit_last_name);
//        edtMName = view.findViewById(R.id.edit_middle_name);
        edtUsername = view.findViewById(R.id.edit_username);
        edtPassword = view.findViewById(R.id.edit_password);
        edtEmail = view.findViewById(R.id.edit_email_address);

        spnFaculties = view.findViewById(R.id.spinner_faculties);
        presenter.getFaculties();

        spnDepartments = view.findViewById(R.id.spinner_departments);

        switchInSess = view.findViewById(R.id.switch_in_session);

        btnRegister = view.findViewById(R.id.button_register);
        btnRegister.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Lecturer lec = new Lecturer();
        lec.setUsername(edtUsername.getText().toString());
        lec.setEmail(edtEmail.getText().toString());
        lec.setPassword(edtPassword.getText().toString());
        lec.setFacultyId(faculties.get(spnFaculties.getCount() > 0 ? spnFaculties.getSelectedItemPosition() : 0).getFacultyId());
        lec.setDepartmentId(departments.get(spnDepartments.getCount() > 0 ? spnDepartments.getSelectedItemPosition() : 0).getDepartmentId());
        lec.setCampusId(""); // TODO: Campus Id is not set up.
        boolean inSess = switchInSess.isChecked();
        lec.setInSesson(inSess);

        LayoutInflater inflater = getLayoutInflater();
        View layoutView = inflater.inflate(R.layout.dialog_db_password, null);

        EditText editText = layoutView.findViewById(R.id.edit_password);
        editText.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.Theme_Dialogs);
        builder.setTitle("Enter Db Password");
        builder.setView(layoutView);
        builder.setPositiveButton("ok", (dialog, which) -> {
            dbPassword = editText.getText().toString();
            presenter.registerUser(lec, dbPassword, faculty, department);
        });

        builder.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private String getDepartmentId(String depName) {
        String id = "";
        for (Department department : departments) {
            if (depName.equals(department.getDepartmentName())) {
                id = department.getDepartmentId();
            }
        }
        return id;
    }

    private String getFacultyId(String toString) {
        String id = "";
        for (Faculty faculty : faculties) {
            if (faculty.getFacultyName().equals(toString)) {
                id = faculty.getFacultyId();
            }
        }
        return id;
    }

    @Override
    public void showFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
        List<String> facultyNames = new ArrayList<>();
        for (Faculty faculty : faculties) {
            if (faculty != null)
                facultyNames.add(faculty.getFacultyName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_spinner_dropdown_item, facultyNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFaculties.setAdapter(arrayAdapter);
        spnFaculties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultyName = parent.getItemAtPosition(position).toString();
                presenter.getDepartments(faculties.get(parent.getSelectedItemPosition()).getFacultyId());
                faculty = faculties.get(parent.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                facultyName = parent.getSelectedItem().toString();
                presenter.getDepartments(faculties.get(parent.getSelectedItemPosition()).getFacultyId());
                faculty = faculties.get(parent.getSelectedItemPosition());
            }
        });
    }

    @Override
    public void showCampuses(List<Campus> campuses) {

    }

    @Override
    public void showMessages(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDepartments(List<Department> departments) {
        this.departments = departments;
        List<String> departmentNames = new ArrayList<>();
        if (departments != null) {
            for (Department department : departments) {
                departmentNames.add(department.getDepartmentName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                    android.R.layout.simple_spinner_dropdown_item, departmentNames);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnDepartments.setAdapter(arrayAdapter);
            spnDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    department = departments.get(adapterView.getSelectedItemPosition());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    department = departments.get(adapterView.getSelectedItemPosition());
                }
            });
        } else {
            Toast.makeText(getActivity(), "Sorry there are not departments in faculty: " + facultyName, Toast.LENGTH_SHORT).show();
        }
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
