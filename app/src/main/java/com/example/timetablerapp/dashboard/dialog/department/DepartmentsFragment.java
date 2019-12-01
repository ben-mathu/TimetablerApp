package com.example.timetablerapp.dashboard.dialog.department;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.util.CompareStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 06/09/19 -bernard
 */
public class DepartmentsFragment extends Fragment implements DepartView, OnItemSelectedListener<Department> {
    // Lists
    private List<Department> list;
    private List<Campus> campuses;
    private List<Faculty> faculties;
    private Department department;

    // object dependencies
    private DepartmentPresenter presenter;
    private DepartmentAdapter adapter;
    private Campus campus;

    // Widgets
    private RecyclerView recyclerDepartment;
    private Spinner spinnerCampus;
    private Spinner spinnerFaculty;
    private TextView txtFacultyName;
    private TextView txtCampusName;
    private String facultyName;
    private Faculty faculty;
    private Spinner spinnerDepartment;
    private String departmentName;
    private String positiveBtnText;
    private AlertDialog dialog;

    @Override
    public void onStart() {
        super.onStart();

        presenter = new DepartmentPresenter(this,
                MainApplication.getFacultyRepo(),
                MainApplication.getDepRepo(),
                MainApplication.getCampusRepo());
        presenter.getDepartments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        recyclerDepartment = view.findViewById(R.id.recycler_view);
        recyclerDepartment.setLayoutManager(new LinearLayoutManager(getActivity()));

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Department> filteredList = filterList(list, s);
                if (adapter != null) {
                    adapter.setList(filteredList);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_department, container, false);
        EditText edtDepartmentName = dialogView.findViewById(R.id.edit_department_name);

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
//                campus = campuses.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                campusName= parent.getSelectedItem().toString();
//                campus = campuses.get(parent.getSelectedItemPosition());
            }
        });

        Button btnAddCampus = view.findViewById(R.id.button_add_item);
        btnAddCampus.setText(R.string.add_department);
        btnAddCampus.setOnClickListener(v -> {
            presenter.getCampusesForDepartment();

            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.Theme_Dialogs)
                    .setPositiveButton("Send", (dialogInterface, i) -> {
                        Department department = new Department();
                        department.setDepartmentId("");
                        department.setDepartmentName(edtDepartmentName.getText().toString());
                        department.setFacultyId(faculties.get(spinnerFaculty.getSelectedItemPosition()).getFacultyId());
                        presenter.addDepartment(department);
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

    private List<Department> filterList(List<Department> list, String s) {
        List<Department> items = new ArrayList<>();

        if (list != null) {
            for (Department dep : list) {
                if (CompareStrings.compare(dep.getDepartmentName(), s)) {
                    items.add(dep);
                }
            }
        }
        return items;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;

        List<String> facultyNames = new ArrayList<>();
        for (Faculty item : faculties) {
            facultyNames.add(item.getFacultyName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_spinner_dropdown_item, facultyNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFaculty.setAdapter(arrayAdapter);
    }

    @Override
    public void setCampusList(List<Campus> campuses) {
        this.campuses = campuses;

        List<String> campusNames = new ArrayList<>();
        for (Campus item : campuses) {
            campusNames.add(item.getCampusName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_spinner_dropdown_item, campusNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCampus.setAdapter(arrayAdapter);
    }

    @Override
    public void setDepartments(List<Department> departments) {
        this.list = departments;

        adapter = new DepartmentAdapter(getActivity(), this, departments);
        recyclerDepartment.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(Department item) {
        this.department = item;
        presenter.getFacultyById(item.getFacultyId());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_department, null, false);

        LinearLayout llCourseDetails = view.findViewById(R.id.ll_course_details);
        LinearLayout llCourseEditDetails = view.findViewById(R.id.ll_course_edit_details);

        // Before editing
        // Display details in plain text.
        TextView txtDepartmentId = view.findViewById(R.id.text_department_id);
        txtDepartmentId.setText(item.getDepartmentId());
        TextView txtDepartmentName = view.findViewById(R.id.text_department_name);
        txtDepartmentName.setText(item.getDepartmentName());

        txtFacultyName = view.findViewById(R.id.text_faculty);
        txtCampusName = view.findViewById(R.id.text_campus);

        EditText edtDepartmentId = view.findViewById(R.id.edit_department_id);
        edtDepartmentId.setText(item.getDepartmentId());
        EditText edtDepartmentName = view.findViewById(R.id.edit_department_name);
        edtDepartmentName.setText(item.getDepartmentName());

        spinnerFaculty = view.findViewById(R.id.change_spinner_faculty);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Theme_Dialogs);
        builder.setView(view);
        builder.setTitle("Edit Course");
        positiveBtnText = "Edit";

        builder.setPositiveButton(positiveBtnText, null);

        builder.setNegativeButton(R.string.delete, (dialogInterface, i) -> {
            presenter.deleteDepartment(item);
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
                Department department = new Department();
                department.setDepartmentId(edtDepartmentId.getText().toString());
                department.setDepartmentName(edtDepartmentName.getText().toString());
                department.setFacultyId(faculties.get(spinnerFaculty.getSelectedItemPosition()).getFacultyId());
                presenter.updateDepartment(department);
            }
        });

        adapter.notifyDataSetChanged();
    }
}
