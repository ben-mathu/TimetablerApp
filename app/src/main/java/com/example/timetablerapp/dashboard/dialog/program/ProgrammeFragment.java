package com.example.timetablerapp.dashboard.dialog.program;

import android.annotation.SuppressLint;
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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.util.CompareStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 09/09/19 -bernard
 */
public class ProgrammeFragment extends Fragment implements ProgrammeView, OnItemSelectedListener<Programme> {
    // List
    private List<Programme>  programmes;
    private List<Department>  departments;
    private List<Faculty>  faculties;
    private List<Campus>  campuses;

    //Classes
    private ProgrammeAdapter adapter;
    private ProgrammePresenter presenter;
    private Campus campus;
    private Faculty faculty;
    private Department department;
    private Programme programme;

    // Widgets
    private RecyclerView recyclerProgrammes;
    private Spinner spinnerCampus;
    private Spinner spinnerFaculty;
    private Spinner spinnerDepartment;
    private TextView txtFacultyName;
    private TextView txtDepartmentName;
    private AlertDialog dialog;

    // String variables
    private String positiveBtnText;

    @Override
    public void onStart() {
        super.onStart();
        presenter = new ProgrammePresenter(this,
                MainApplication.getCampusRepo(),
                MainApplication.getFacultyRepo(),
                MainApplication.getDepRepo(),
                MainApplication.getProgRepo());
        presenter.getAllProgrammes();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        recyclerProgrammes = view.findViewById(R.id.recycler_view);
        recyclerProgrammes.setLayoutManager(new LinearLayoutManager(getActivity()));

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Programme> filteredList = filterList(programmes, s);
                if (adapter != null) {
                    adapter.setList(filteredList);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        // create a dialog
        View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_programme, container, false);
        EditText edtProgrammeName = dialogView.findViewById(R.id.edit_programme_name);

        spinnerCampus = dialogView.findViewById(R.id.spinner_campus);
        spinnerCampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                campusName = parent.getItemAtPosition(position).toString();
                campus = campuses.get(position);
                presenter.getFacultyById(campus.getCampusId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                campusName= parent.getSelectedItem().toString();
                campus = campuses.get(parent.getSelectedItemPosition());
                presenter.getFacultyById(campus.getCampusId());
            }
        });

        spinnerFaculty = dialogView.findViewById(R.id.spinner_faculty);
        spinnerFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                campusName = parent.getItemAtPosition(position).toString();
                faculty = faculties.get(position);
                presenter.getDepartments(faculty.getFacultyId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                campusName= parent.getSelectedItem().toString();
                faculty = faculties.get(parent.getSelectedItemPosition());
                presenter.getDepartments(faculty.getFacultyId());
            }
        });

        spinnerDepartment = dialogView.findViewById(R.id.spinner_departments);
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                campusName = parent.getItemAtPosition(position).toString();
//                department = departments.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                campusName= parent.getSelectedItem().toString();
//                department = departments.get(parent.getSelectedItemPosition());
            }
        });

        Button btnAddCampus = view.findViewById(R.id.button_add_item);
        btnAddCampus.setText(R.string.add_programme);
        btnAddCampus.setOnClickListener(v -> {
            presenter.getCampusesForDepartment();

            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.Theme_Dialogs)
                    .setPositiveButton("Send", (dialogInterface, i) -> {
                        Programme programme = new Programme();
                        programme.setProgrammeId("");
                        programme.setProgrammeName(edtProgrammeName.getText().toString());
                        programme.setDepartmentId(departments.get(spinnerDepartment.getSelectedItemPosition()).getDepartmentId());
                        programme.setFacultyId(faculties.get(spinnerFaculty.getSelectedItemPosition()).getFacultyId());
                        presenter.addProgramme(programme);
                    }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.setTitle("Add Department Dialog");
            builder.setView(dialogView);
            builder.setCancelable(false);

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

    private List<Programme> filterList(List<Programme> list, String s) {
        List<Programme> items = new ArrayList<>();

        if (list != null) {
            for (Programme prog : list) {
                if (CompareStrings.compare(prog.getDepartmentId(), s) || CompareStrings.compare(prog.getProgrammeName(), s)) {
                    items.add(prog);
                }
            }
        }
        return items;
    }

    @Override
    public void setCampuses(List<Campus> campuses) {
        this.campuses = campuses;
        List<String> campusNames = new ArrayList<>();

        for (Campus campus : campuses) {
            campusNames.add(campus.getCampusName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, campusNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCampus.setAdapter(arrayAdapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        presenter.getAllProgrammes();
    }

    @Override
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;

        List<String> facultyNames = new ArrayList<>();

        for (Faculty faculty : faculties) {
            facultyNames.add(faculty.getFacultyName());
        }

        spinnerFaculty.setAdapter(getArrayAdapter(facultyNames));
    }

    @Override
    public void setDepartments(List<Department> departments) {
        this.departments = departments;

        List<String> departmentNames = new ArrayList<>();

        for (Department department : departments) {
            departmentNames.add(department.getDepartmentName());
        }

        spinnerDepartment.setAdapter(getArrayAdapter(departmentNames));
    }

    private SpinnerAdapter getArrayAdapter(List<String> items) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, items);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    @Override
    public void setProgrammes(List<Programme> programmes) {
        this.programmes = programmes;

        adapter = new ProgrammeAdapter(getActivity(), programmes, this);
        recyclerProgrammes.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(Programme item) {
        this.programme = item;
        presenter.getCampusesForDepartment();

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_programme, null, false);

        LinearLayout llCourseDetails = view.findViewById(R.id.ll_course_details);
        LinearLayout llCourseEditDetails = view.findViewById(R.id.ll_course_edit_details);

        // Before editing
        // Display details in plain text.
        TextView txtProgrammeId = view.findViewById(R.id.text_programme_id);
        txtProgrammeId.setText(item.getProgrammeId());
        TextView txtProgrammeName = view.findViewById(R.id.text_unit_name);
        txtProgrammeName.setText(item.getProgrammeName());

        txtFacultyName = view.findViewById(R.id.text_faculty);

        txtDepartmentName = view.findViewById(R.id.text_department);

        EditText edtProgrammeId = view.findViewById(R.id.edit_unit_id);
        edtProgrammeId.setText(item.getProgrammeId());
        EditText edtProgrammeName = view.findViewById(R.id.edit_unit_name);
        edtProgrammeName.setText(item.getProgrammeName());

        spinnerCampus = view.findViewById(R.id.change_spinner_campus);
        spinnerCampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                campusName = parent.getItemAtPosition(position).toString();
                campus = campuses.get(position);
                presenter.getFacultyById(campus.getCampusId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                campusName= parent.getSelectedItem().toString();
                campus = campuses.get(parent.getSelectedItemPosition());
                presenter.getFacultyById(campus.getCampusId());
            }
        });

        spinnerFaculty = view.findViewById(R.id.change_spinner_faculty);
        spinnerFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                campusName = parent.getItemAtPosition(position).toString();
                faculty = faculties.get(position);
                presenter.getDepartments(faculty.getFacultyId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                campusName= parent.getSelectedItem().toString();
                faculty = faculties.get(parent.getSelectedItemPosition());
                presenter.getDepartments(faculty.getFacultyId());
            }
        });

        spinnerDepartment = view.findViewById(R.id.change_spinner_departments);
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                campusName = parent.getItemAtPosition(position).toString();
//                department = departments.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                campusName= parent.getSelectedItem().toString();
//                department = departments.get(parent.getSelectedItemPosition());
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Theme_Dialogs);
        builder.setView(view);
        builder.setTitle("Edit Course");
        positiveBtnText = "Edit";

        builder.setPositiveButton(positiveBtnText, null);

        builder.setNegativeButton(R.string.delete, (dialogInterface, i) -> {
            presenter.deleteProgramme(item);
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
                Programme p = new Programme();
                p.setProgrammeId(edtProgrammeId.getText().toString());
                p.setProgrammeName(edtProgrammeName.getText().toString());
                p.setFacultyId(faculties.get(spinnerFaculty.getSelectedItemPosition()).getFacultyId());
                p.setDepartmentId(departments.get(spinnerDepartment.getSelectedItemPosition()).getDepartmentId());
                presenter.updateCourse(p);
            }
        });

        adapter.notifyDataSetChanged();
    }
}
