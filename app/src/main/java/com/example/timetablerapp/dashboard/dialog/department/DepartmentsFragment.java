package com.example.timetablerapp.dashboard.dialog.department;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.DashboardPresenter;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 06/09/19 -bernard
 */
public class DepartmentsFragment extends Fragment implements DepartView {
    // Lists
    private List<Department> list;
    private List<Campus> campuses;
    private List<Faculty> faculties;

    // object dependencies
    private DepartmentAdapter adapter;
    private DashboardPresenter presenter;
    private Campus campus;

    // Widgets
    private RecyclerView recyclerDepartment;
    private Spinner spinnerCampus;
    private Spinner spinnerFaculty;

    @Override
    public void onStart() {
        super.onStart();

        presenter = new DashboardPresenter(this,
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
            builder.create().show();

        });
        return view;
    }

    private List<Department> filterList(List<Department> list, String s) {
        List<Department> items = new ArrayList<>();

        if (list != null) {
            for (Department dep : list) {
                if (dep.getDepartmentId().contains(s) || dep.getDepartmentName().contains(s)) {
                    items.add(dep);
                }
            }
        }
        return items;
    }

    @Override
    public void showMessage(String message) {

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

        adapter = new DepartmentAdapter(getActivity(), departments);
        recyclerDepartment.setAdapter(adapter);
    }
}
