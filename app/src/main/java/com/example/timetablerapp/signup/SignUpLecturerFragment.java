package com.example.timetablerapp.signup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.timetable.TimetableActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class SignUpLecturerFragment extends Fragment implements View.OnClickListener, SignUpContract.View {
    private SignUpPresenter presenter;

    private EditText edtUserId, edtFName, edtLName, edtMName, edtUsername, edtPassword;
    private Spinner spnDepartments, spnFaculties;
    private Switch switchInSess;
    private Button btnRegister;

    private List<Faculty> faculties;
    private List<Department> departments;

    private String role = "";
    private String depName = "";
    private String facultyName = "";

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
                MainApplication.getUserRepository(),
                MainApplication.getLecturerRepo());
        role = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_lecturer, container, false);

        // Initialize Widgets
        edtUserId = view.findViewById(R.id.edit_user_id);
        edtFName = view.findViewById(R.id.edit_first_name);
        edtLName = view.findViewById(R.id.edit_last_name);
        edtMName = view.findViewById(R.id.edit_middle_name);
        edtUsername = view.findViewById(R.id.edit_username);
        edtPassword = view.findViewById(R.id.edit_password);

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
        lec.setId(edtUserId.getText().toString());
        lec.setFirstName(edtFName.getText().toString());
        lec.setLastName(edtLName.getText().toString());
        lec.setMiddleName(edtMName.getText().toString());
        lec.setUsername(edtUsername.getText().toString());
        lec.setPassword(edtPassword.getText().toString());
        lec.setFacultyId(getFacultyId(spnFaculties.getSelectedItem().toString()));
        lec.setDepartmentId(getDepartmentId(spnDepartments.getSelectedItem().toString()));
        boolean inSess = switchInSess.isChecked();
        lec.setInSesson(inSess);

        presenter.registerUser(lec);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, facultyNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFaculties.setAdapter(arrayAdapter);
        spnFaculties.setSelection(0, false);
        spnFaculties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultyName = parent.getItemAtPosition(position).toString();
                for (Faculty faculty : faculties) {
                    if (facultyName.equals(faculty.getFacultyName())) {
                        presenter.getDepartments(faculty.getFacultyId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                facultyName = parent.getSelectedItem().toString();
                for (Faculty faculty : faculties) {
                    if (facultyName.equals(faculty.getFacultyName())) {
                        presenter.getDepartments(faculty.getFacultyId());
                    }
                }
            }
        });
    }

    @Override
    public void showCampuses(List<Campus> campuses) {

    }

    @Override
    public void showMessages(String message) {

    }

    @Override
    public void showDepartments(List<Department> departments) {
        this.departments = departments;
        List<String> departmentNames = new ArrayList<>();
        if (departments != null) {
            for (Department department : departments) {
                departmentNames.add(department.getDepartmentName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, departmentNames);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnDepartments.setAdapter(arrayAdapter);
        } else {
            Toast.makeText(getActivity(), "Sorry there are not departments in faculty: " + facultyName, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgrammes(List<Programme> programmes) {

    }

    @Override
    public void startTimeTableActivity() {
        startActivity(new Intent(getActivity(), TimetableActivity.class));
    }
}
