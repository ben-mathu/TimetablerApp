package com.example.timetablerapp.dashboard.dialog.program;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.DashboardPresenter;
import com.example.timetablerapp.dashboard.dialog.course.CourseView;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 09/09/19 -bernard
 */
public class ProgrammeFragment extends Fragment implements ProgView {
    // List
    private List<Programme>  programmes;
    private List<Department>  departments;
    private List<Faculty>  faculties;
    private List<Campus>  campuses;

    //Classes
    private ProgrammeAdapter adapter;
    private DashboardPresenter presenter;
    private Campus campus;
    private Faculty faculty;

    // Widgets
    private RecyclerView recyclerProgrammes;
    private Spinner spinnerCampus;
    private Spinner spinnerFaculty;
    private Spinner spinnerDepartment;
    private Department department;

    @Override
    public void onStart() {
        super.onStart();
        presenter = new DashboardPresenter(this,
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
            builder.create().show();

        });
        return view;
    }

    private List<Programme> filterList(List<Programme> list, String s) {
        List<Programme> items = new ArrayList<>();

        if (list != null) {
            for (Programme prog : list) {
                if (prog.getProgrammeId().contains(s) || prog.getProgrammeName().contains(s)) {
                    items.add(prog);
                }
            }
        }
        return items;
    }

    @Override
    public void setCampuses(List<Campus> campuses) {
        // left blank intentionally
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    @Override
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public void setProgrammes(List<Programme> programmes) {
        this.programmes = programmes;

        adapter = new ProgrammeAdapter(getActivity(), programmes);
        recyclerProgrammes.setAdapter(adapter);
    }
}
