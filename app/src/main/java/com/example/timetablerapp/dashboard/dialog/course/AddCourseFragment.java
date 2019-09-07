package com.example.timetablerapp.dashboard.dialog.course;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.DashboardPresenter;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * 02/09/19 -bernard
 */
public class AddCourseFragment extends Fragment implements CourseView {

    private List<Unit> list;
    private List<Faculty> faculties;
    private List<Department> departments;
    private List<Programme> programmes;

    private CourseAdapter adapter;

    private AlertDialog.Builder builder;
    private Spinner spinnerFaculty, spinnerDepartment, spinnerProgramme;
    private DashboardPresenter presenter;
    private Button btnAddCourse;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Programme programme;
    private String programmeName;
    private Department department;
    private String departmentName;
    private Faculty faculty;
    private String facultyName;

    @Override
    public void onStart() {
        super.onStart();
        presenter = new DashboardPresenter(this,
                MainApplication.getUnitRepo(),
                MainApplication.getFacultyRepo(),
                MainApplication.getDepRepo(),
                MainApplication.getProgRepo());
        presenter.getCourses();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        presenter =  new DashboardPresenter(this,
                MainApplication.getUnitRepo(),
                MainApplication.getFacultyRepo(),
                MainApplication.getDepRepo(),
                MainApplication.getProgRepo());

        recyclerView = view.findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Unit> filteredList = filterList(list, s);
                if (adapter != null) {
                    adapter.setList(filteredList);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.course_fields, null, false);
        EditText edtFUnitId = dialogView.findViewById(R.id.edit_unit_id),
                edtUnitName = dialogView.findViewById(R.id.edit_unit_name),
                edtPassCode = dialogView.findViewById(R.id.edit_pass_code);

        spinnerFaculty = dialogView.findViewById(R.id.spinner_faculty);
        spinnerFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
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
        spinnerDepartment = dialogView.findViewById(R.id.spinner_departments);
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                departmentName = parent.getItemAtPosition(position).toString();
                department = departments.get(position);
                presenter.getProgrammes(departments.get(position).getDepartmentId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                departmentName = parent.getSelectedItem().toString();
                department = departments.get(parent.getSelectedItemPosition());
                presenter.getProgrammes(departments.get(parent.getSelectedItemPosition()).getDepartmentId());
            }
        });

        spinnerProgramme = dialogView.findViewById(R.id.spinner_programme);
        spinnerProgramme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                programmeName = parent.getItemAtPosition(position).toString();
                programme = programmes.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                programmeName = parent.getSelectedItem().toString();
                programme = programmes.get(parent.getSelectedItemPosition());
            }
        });

        Switch switchCommon = dialogView.findViewById(R.id.switch_common),
                switchPractical = dialogView.findViewById(R.id.switch_practical);
        edtPassCode.setVisibility(View.VISIBLE);

        btnAddCourse = view.findViewById(R.id.button_add_item);
        btnAddCourse.setOnClickListener(v -> {
            presenter.getFaculties();

            builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Dialogs);
            builder.setCancelable(true);
            builder.setView(dialogView);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (edtFUnitId.getText().toString().isEmpty()
                            || edtUnitName.getText().toString().isEmpty()
                    ) {
                        showMessage("All the fields are required");
                    } else {
                        Unit unit = new Unit();
                        unit.setCommon(switchCommon.isChecked());
                        unit.setFacultyId(faculty.getFacultyId());
                        unit.setId(edtFUnitId.getText().toString());
                        unit.setProgrammeId(programme.getProgrammeId());
                        unit.setDepartmentId(department.getDepartmentId());
                        unit.setPractical(switchPractical.isChecked());
                        unit.setUnitName(edtUnitName.getText().toString());

                        presenter.addCourse(unit, edtPassCode.getText().toString());
                    }
                }
            }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

            if (dialogView.getParent() != null) {
                ((ViewGroup) dialogView.getParent()).removeView(dialogView);
            }

            builder.create().show();
        });

        return view;
    }

    private List<Unit> filterList(List<Unit> list, String s) {
        List<Unit> items = new ArrayList<>();

        if (list != null) {
            for (Unit unit : list) {
                if (unit.getId().contains(s) || unit.getUnitName().contains(s)) {
                    items.add(unit);
                }
            }
        }
        return items;
    }

    @Override
    public void setUnits(List<Unit> units) {
        this.list = units;
        adapter = new CourseAdapter(getActivity(), units);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
        List<String> facultyNames = new ArrayList<>();
        for (Faculty faculty : faculties) {
            facultyNames.add(faculty.getFacultyName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, facultyNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFaculty.setAdapter(arrayAdapter);
    }

    @Override
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
        List<String> departmentNames = new ArrayList<>();
        for (Department department : departments) {
            departmentNames.add(department.getDepartmentName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, departmentNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(arrayAdapter);
    }

    @Override
    public void setProgrammes(List<Programme> programmes) {
        this.programmes = programmes;
        List<String> programmesNames = new ArrayList<>();
        for (Programme programme : programmes) {
            programmesNames.add(programme.getProgrammeName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, programmesNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProgramme.setAdapter(arrayAdapter);
    }
}
