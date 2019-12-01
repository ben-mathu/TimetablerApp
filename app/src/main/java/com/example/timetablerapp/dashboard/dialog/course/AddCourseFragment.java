package com.example.timetablerapp.dashboard.dialog.course;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.DashboardPresenter;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.util.CompareStrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 02/09/19 -bernard
 */
public class AddCourseFragment extends Fragment implements CourseView, OnItemSelectedListener<Unit> {

    private List<Unit> list;
    private List<Faculty> faculties;
    private List<Department> departments;
    private List<Programme> programmes;

    private CourseAdapter adapter;

    private CoursePresenter presenter;
    private AlertDialog.Builder builder;
    private Spinner spinnerFaculty, spinnerDepartment, spinnerProgramme;
    private Button btnAddCourse;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView txtFacultyName;
    private TextView txtDepartmentName;

    private Programme programme;
    private String programmeName;
    private Department department;
    private String departmentName;
    private Faculty faculty;
    private String facultyName;
    private String positiveBtnText = "";
    private AlertDialog dialog;
    private TextView txtProgrammeName;
    private String departmentId;
    private Unit unit;

    @Override
    public void onStart() {
        super.onStart();
        presenter = new CoursePresenter(this,
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

        presenter =  new CoursePresenter(this,
                MainApplication.getUnitRepo(),
                MainApplication.getFacultyRepo(),
                MainApplication.getDepRepo(),
                MainApplication.getProgRepo());

        recyclerView = view.findViewById(R.id.recycler_view);
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

            AlertDialog dialog = builder.create();
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(550, ViewGroup.LayoutParams.WRAP_CONTENT);
        });

        return view;
    }

    private List<Unit> filterList(List<Unit> list, String s) {
        List<Unit> items = new ArrayList<>();

        if (list != null) {
            for (Unit unit : list) {
                if (CompareStrings.compare(unit.getId(), s) || CompareStrings.compare(unit.getUnitName(), s)) {
                    items.add(unit);
                }
            }
        }
        return items;
    }

    @Override
    public void setUnits(List<Unit> units) {
        this.list = units;
        adapter = new CourseAdapter(getActivity(), units, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;

        if (spinnerFaculty != null) {
            List<String> facultyNames = new ArrayList<>();
            for (Faculty faculty : faculties) {
                facultyNames.add(faculty.getFacultyName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, facultyNames);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFaculty.setAdapter(arrayAdapter);
        }

        getFacultyById(unit.getFacultyId());
    }

    @Override
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
        departmentId = "";
        for (Department dep : departments) {
            departmentId = dep.getDepartmentId().equals(unit.getDepartmentId()) ? dep.getDepartmentId() : "";
        }
        presenter.getProgrammes(departmentId);

        if (spinnerDepartment != null) {
            List<String> departmentNames = new ArrayList<>();
            for (Department department : departments) {
                departmentNames.add(department.getDepartmentName());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, departmentNames);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDepartment.setAdapter(arrayAdapter);
        }

        getDepartmentById(unit.getDepartmentId());
    }

    @Override
    public void setProgrammes(List<Programme> programmes) {
        this.programmes = programmes;

        // get program associated with course
        programme = getProgramme(unit);

        if (spinnerProgramme != null) {
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

    @Override
    public void onItemSelected(Unit item) {
        this.unit = item;
        presenter.getFaculties();
        presenter.getDepartments();
        presenter.getProgrammes(item.getDepartmentId());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_course, null, false);

        LinearLayout llCourseDetails = view.findViewById(R.id.ll_course_details);
        LinearLayout llCourseEditDetails = view.findViewById(R.id.ll_course_edit_details);

        // Before editing
        // Display details in plain text.
        TextView txtUnitId = view.findViewById(R.id.text_unit_id);
        txtUnitId.setText(item.getId());
        TextView txtUnitName = view.findViewById(R.id.text_unit_name);
        txtUnitName.setText(item.getUnitName());

        txtFacultyName = view.findViewById(R.id.text_faculty);

        txtDepartmentName = view.findViewById(R.id.text_department);

        txtProgrammeName = view.findViewById(R.id.text_programme);

        TextView txtPractical = view.findViewById(R.id.show_practical);
        if (item.isPractical()) txtPractical.setText(R.string.practical);
        else txtPractical.setText(R.string.not_practical);
        TextView txtCommon = view.findViewById(R.id.show_common);
        if (item.isCommon()) txtCommon.setText(R.string.common);
        else txtCommon.setText(R.string.not_common);

        EditText edtUnitId = view.findViewById(R.id.edit_unit_id);
        edtUnitId.setText(item.getId());
        EditText edtUnitName = view.findViewById(R.id.edit_unit_name);
        edtUnitName.setText(item.getUnitName());

        spinnerFaculty = view.findViewById(R.id.change_spinner_faculty);
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

        spinnerDepartment = view.findViewById(R.id.change_spinner_departments);
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

        spinnerProgramme = view.findViewById(R.id.change_spinner_programme);
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

        Switch switchCommon = view.findViewById(R.id.change_switch_common);
        if (item.isCommon()) {
            switchCommon.setChecked(true);
        } else {
            switchCommon.setChecked(false);
        }

        Switch switchPractical = view.findViewById(R.id.change_switch_practical);
        if (item.isPractical()) {
            switchPractical.setChecked(true);
        } else {
            switchPractical.setChecked(false);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Theme_Dialogs);
        builder.setView(view);
        builder.setTitle("Edit Course");
        positiveBtnText = "Edit";

        builder.setPositiveButton(positiveBtnText, null);

        builder.setNegativeButton(R.string.delete, (dialogInterface, i) -> {
            presenter.deleteCourse(item);
        });

        dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
            if (positiveBtnText.equalsIgnoreCase("edit")) {
                llCourseEditDetails.setVisibility(View.VISIBLE);
                llCourseDetails.setVisibility(View.GONE);

                positiveBtnText = "Save";

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.save);
            } else {
                Unit unit = new Unit();
                unit.setCommon(switchCommon.isChecked());
                unit.setPractical(switchPractical.isChecked());
                unit.setId(edtUnitId.getText().toString());
                unit.setUnitName(edtUnitName.getText().toString());
                unit.setFacultyId(faculties.get(spinnerFaculty.getSelectedItemPosition()).getFacultyId());
                unit.setProgrammeId(programmes.get(spinnerProgramme.getSelectedItemPosition()).getProgrammeId());
                unit.setDepartmentId(departments.get(spinnerDepartment.getSelectedItemPosition()).getDepartmentId());
                presenter.updateCourse(unit);
            }
        });

        adapter.notifyDataSetChanged();
    }

    /**
     * Filters programmes depending on course selected
     * @param item Course selected by the user
     * @return programme with same programme id set in course object
     *         returns null if the programme was not found.
     * @see Programme
     */
    private Programme getProgramme(Unit item) {
        for (Programme prog : programmes) {
            if (item.getProgrammeId().equals(prog.getProgrammeId())) {
                txtProgrammeName.setText(prog.getProgrammeName());
                return prog;
            }
        }
        return null;
    }

    private void getDepartmentById(String departmentId) {
        if (departments != null) {
            for (Department dep : departments) {
                if (dep.getDepartmentId().equals(departmentId)) {
                    txtDepartmentName.setText(dep.getDepartmentName());
                    break;
                }
            }
        }
    }

    private void getFacultyById(String id) {
        if (faculties != null) {
            for (Faculty faculty : faculties) {
                if (faculty.getFacultyId().equals(id)) {
                    txtFacultyName.setText(faculty.getFacultyName());
                    break;
                }
            }
        }
    }
}
