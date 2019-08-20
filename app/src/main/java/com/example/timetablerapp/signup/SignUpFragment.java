package com.example.timetablerapp.signup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.user.student.StudentRepository;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.login.LoginActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class SignUpFragment extends Fragment implements View.OnClickListener, SignUpContract.View {
    private Calendar calendar;

    private SignUpPresenter presenter;

    private List<Department> departmentList;
    private List<Programme> programmes;
    private List<Campus> campuses;
    private List<Faculty> faculties;

    private Department department;
    private Programme programme;
    private Campus campus;
    private Faculty faculty;

    private TextView txtLogin;
    private EditText edtUserId, edtFName, edtLName, edtMName, edtUsername, edtPassword;
    private Button btnRegister, btnDatePicker;
    private Spinner spnDepartments, spnProgrammes, spnCampuses, spnFaculties;

    private String role;
    private String depName = "", progName = "", campusName = "", facultyName = "";
    private Switch switchInSess;
    private EditText edtYearOfStudy;
    private String date = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        txtLogin = view.findViewById(R.id.text_login);
        txtLogin.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        // Initialize Widgets
        edtUserId = view.findViewById(R.id.edit_user_id);
        edtFName = view.findViewById(R.id.edit_first_name);
        edtLName = view.findViewById(R.id.edit_last_name);
        edtMName = view.findViewById(R.id.edit_middle_name);
        edtUsername = view.findViewById(R.id.edit_username);
        edtPassword = view.findViewById(R.id.edit_password);

        // get campuses
        presenter.getCampuses();
        spnCampuses = view.findViewById(R.id.spinner_campus);
        spnCampuses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                campusName = parent.getItemAtPosition(position).toString();
                campus = campuses.get(position);
                presenter.getFaculties(campuses.get(position).getCampusId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                campusName = parent.getSelectedItem().toString();
                campus = campuses.get(parent.getSelectedItemPosition());
                presenter.getFaculties(campuses.get(parent.getSelectedItemPosition()).getCampusId());
            }
        });


        spnFaculties = view.findViewById(R.id.spinner_faculty);
        spnFaculties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultyName = parent.getItemAtPosition(position).toString();
                faculty = faculties.get(position);
                presenter.getDepartments(faculties.get(position).getFacultyId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                facultyName = parent.getSelectedItem().toString();
                faculty = faculties.get(parent.getSelectedItemPosition());
                presenter.getDepartments(faculties.get(parent.getSelectedItemPosition()).getFacultyId());
            }
        });

        spnDepartments = view.findViewById(R.id.spinner_departments);
        spnDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depName = parent.getItemAtPosition(position).toString();
                department = departmentList.get(position);
                presenter.getProgrammes(departmentList.get(position).getDepartmentId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                depName = parent.getSelectedItem().toString();
                department = departmentList.get(parent.getSelectedItemPosition());
                presenter.getProgrammes(departmentList.get(parent.getSelectedItemPosition()).getDepartmentId());
            }
        });

        spnProgrammes = view.findViewById(R.id.spinner_programme);
        spnProgrammes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progName = parent.getItemAtPosition(position).toString();
                programme = programmes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                progName = parent.getSelectedItem().toString();
                programme = programmes.get(parent.getSelectedItemPosition());
            }
        });

        btnDatePicker = view.findViewById(R.id.button_date_picker);
        btnDatePicker.setOnClickListener(v -> {
            calendar = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    btnDatePicker.setText(DateFormat.format("yyyy-MM-dd", calendar.getTimeInMillis()));
                }
            };
            DatePickerDialog d = new DatePickerDialog(getActivity(), datePickerDialog,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            d.show();
        });

        edtYearOfStudy = view.findViewById(R.id.edit_year_of_study);
        switchInSess = view.findViewById(R.id.switch_in_session);

        btnRegister = view.findViewById(R.id.button_register);
        btnRegister.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Student student = new Student();
        student.setStudentId(edtUserId.getText().toString());
        student.setFname(edtFName.getText().toString());
        student.setLname(edtLName.getText().toString());
        student.setMname(edtMName.getText().toString());
        student.setUsername(edtUsername.getText().toString());
        student.setPassword(edtPassword.getText().toString());
        student.setYearOfStudy(edtYearOfStudy.getText().toString());
        student.setAdmissionDate(btnDatePicker.getText().toString());

        try {
            student.setCampusId(campuses.get(spnCampuses.getCount() > 0 ? spnCampuses.getSelectedItemPosition() : 0).getCampusId());
            student.setFacultyId(faculties.get(spnFaculties.getCount() > 0 ? spnFaculties.getSelectedItemPosition() : 0).getFacultyId());
            student.setDepartmentId(departmentList.get(spnDepartments.getCount() > 0 ? spnDepartments.getSelectedItemPosition() : 0).getDepartmentId());
            student.setProgrammeId(programmes.get(spnProgrammes.getCount() > 0 ? spnProgrammes.getSelectedItemPosition() : 0).getProgrammeId());
            boolean inSess = switchInSess.isChecked();
            student.setInSession(inSess);

            presenter.registerUser(student, department, faculty, campus, programme);
        } catch (IndexOutOfBoundsException e) {
            showMessages("Requires all field, Contact admin if you have a problem");
        }
    }

    @Override
    public void showFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
        List<String> facultyNames = new ArrayList<>();
        for (Faculty faculty : faculties) {
            facultyNames.add(faculty.getFacultyName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, facultyNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFaculties.setAdapter(arrayAdapter);
    }

    @Override
    public void showCampuses(List<Campus> campuses) {
        this.campuses = campuses;
        List<String> campusNames = new ArrayList<>();
        for (Campus campus : campuses) {
            campusNames.add(campus.getCampusName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, campusNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCampuses.setAdapter(arrayAdapter);
    }

    @Override
    public void showMessages(String message) {
        Toast.makeText(getActivity(), "Message: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDepartments(List<Department> departments) {
        departmentList = departments;
        List<String> departmentNames = new ArrayList<>();
        for (Department department : departments) {
            departmentNames.add(department.getDepartmentName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, departmentNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDepartments.setAdapter(arrayAdapter);
    }

    @Override
    public void showProgrammes(List<Programme> programmes) {
        this.programmes = programmes;
        List<String> programmesNames = new ArrayList<>();
        for (Programme programme : programmes) {
            programmesNames.add(programme.getProgrammeName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, programmesNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProgrammes.setAdapter(arrayAdapter);
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
