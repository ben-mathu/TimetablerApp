package com.example.timetablerapp.dashboard.dialog.faculty;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.DashboardPresenter;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.util.CompareStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * 06/09/19 -bernard
 */
public class FacultiesFragment extends Fragment implements FacultyView {
    private List<Faculty> faculties;
    private List<Campus> campuses;

    // Classes
    private FacultyAdapter adapter;
    private DashboardPresenter presenter;
    private Campus campus;

    // Widgets
    private RecyclerView recyclerFaculty;
    private Spinner spinnerCampus;

    // Literals
    private String campusName = "";

    @Override
    public void onStart() {
        super.onStart();
        presenter = new DashboardPresenter(this,
                MainApplication.getFacultyRepo(),
                MainApplication.getCampusRepo());
        presenter.getFacultiesForFaculty();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, null, false);

        presenter = new DashboardPresenter(this,
                MainApplication.getFacultyRepo(),
                MainApplication.getCampusRepo());

        recyclerFaculty = view.findViewById(R.id.recycler_view);
        recyclerFaculty.setLayoutManager(new LinearLayoutManager(getActivity()));

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Faculty> filteredList = filterList(faculties, s);
                if (adapter != null) {
                    adapter.setList(filteredList);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_faculty, null, false);
        EditText edtFacultyName = dialogView.findViewById(R.id.edit_campus_name);

        spinnerCampus = dialogView.findViewById(R.id.spinner_campus);
        spinnerCampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                campusName = parent.getItemAtPosition(position).toString();
                campus = campuses.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                campusName= parent.getSelectedItem().toString();
                campus = campuses.get(parent.getSelectedItemPosition());
            }
        });

        Button btnAddCampus = view.findViewById(R.id.button_add_item);
        btnAddCampus.setText(R.string.add_faculty);
        btnAddCampus.setOnClickListener(v -> {
            presenter.getCampusesForFaculty();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Dialogs)
                    .setPositiveButton("Send", (dialogInterface, i) -> {
                        Faculty faculty = new Faculty("", edtFacultyName.getText().toString(), campuses.get(spinnerCampus.getSelectedItemPosition()).getCampusId());
                        presenter.addFaculty(faculty);
                    }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.setTitle("Add Faculty");
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

    private List<Faculty> filterList(List<Faculty> list, String s) {
        List<Faculty> items = new ArrayList<>();

        if (list != null) {
            for (Faculty faculty : list) {
                if (CompareStrings.compare(faculty.getFacultyName(), s)) {
                    items.add(faculty);
                }
            }
        }
        return items;
    }

    @Override
    public void setCampusList(List<Campus> campuses) {
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
    }

    @Override
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;

        adapter = new FacultyAdapter(getActivity(), faculties);
        recyclerFaculty.setAdapter(adapter);
    }
}
