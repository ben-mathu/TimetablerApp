package com.example.timetablerapp.signup;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.user.student.StudentRepository;

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

    private EditText edtUserId, edtFName, edtLName, edtMName, edtUsername, edtPassword;
    private Button btnRegister, btnDatePicker;
    private Spinner spnDepartments, spnProgrammes, spnCampuses, spnFaculties;

    private String role;
    private String depName = "", progName = "", campusName = "", facultyName = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

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
                presenter.getFaculties(campusName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnFaculties = view.findViewById(R.id.spinner_faculty);
        spnFaculties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                facultyName = parent.getItemAtPosition(position).toString();
                presenter.getDepartments(facultyName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnDepartments = view.findViewById(R.id.spinner_departments);
        spnDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depName = parent.getItemAtPosition(position).toString();
                presenter.getProgrammes(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnProgrammes = view.findViewById(R.id.spinner_programme);
        spnProgrammes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progName = parent.getItemAtPosition(position).toString();
                presenter.getProgrammes(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDatePicker = view.findViewById(R.id.button_date_picker);
        btnDatePicker.setOnClickListener(v -> {
            calendar = Calendar.getInstance();

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), (DatePicker datepicker, int year, int month, int day) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();

            String time = DateFormat.format(Constants.DATE_FORMAT, calendar.getTimeInMillis()).toString();

            // Another way to format dates.
//            SimpleDateFormat simpleDateFormat;
//            simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
//            String dateTime = simpleDateFormat.format(calendar);
            btnDatePicker.setText(time);
        });


        btnRegister = view.findViewById(R.id.button_register);
        btnRegister.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showFaculties(List<Faculty> faculties) {
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
    public void startLoginActiity() {

    }
}
