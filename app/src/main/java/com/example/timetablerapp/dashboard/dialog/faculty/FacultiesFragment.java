package com.example.timetablerapp.dashboard.dialog.faculty;

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
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.util.CompareStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 06/09/19 -bernard
 */
public class FacultiesFragment extends Fragment implements FacultyView, OnItemSelectedListener<Faculty> {
    private List<Faculty> faculties;
    private List<Campus> campuses;

    // Classes
    private FacultyPresenter presenter;
    private FacultyAdapter adapter;
    private Campus campus;
    private Faculty faculty;

    // Widgets
    private RecyclerView recyclerFaculty;
    private Spinner spinnerCampus;

    // Literals
    private String campusName = "";
    private String positiveBtnText = "";
    private String negativeBtnText;
    private AlertDialog dialog;

    @Override
    public void onStart() {
        super.onStart();
        presenter = new FacultyPresenter(this,
                MainApplication.getFacultyRepo(),
                MainApplication.getCampusRepo());
        presenter.getFacultiesForFaculty();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, null, false);

        presenter = new FacultyPresenter(this,
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

        spinnerCampus.setAdapter(initializeArrayAdapter(campusNames));
    }

    private SpinnerAdapter initializeArrayAdapter(List<String> items) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, items);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return arrayAdapter;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;

        adapter = new FacultyAdapter(getActivity(), faculties, this);
        recyclerFaculty.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(Faculty item) {
        faculty = item;
        presenter.getCampusesForFaculty();

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_faculty, null, false);


        LinearLayout llLecturerDetails = view.findViewById(R.id.ll_faculty_details);
        LinearLayout llEditLecturerDetails = view.findViewById(R.id.ll_faculty_edit_details);

        // Before editing
        // show lecturer details.
        TextView txtFacultyId = view.findViewById(R.id.text_faculty_id);
        txtFacultyId.setText(item.getFacultyId());
        TextView txtFacultyName = view.findViewById(R.id.text_faculty_name);
        txtFacultyName = view.findViewById(R.id.text_faculty_name);

        EditText edtFacultyId = view.findViewById(R.id.edit_faculty_id);
        edtFacultyId.setText(item.getFacultyId());

        EditText edtFacultyName = view.findViewById(R.id.edit_faculty_name);
        edtFacultyName.setText(item.getFacultyName());

        spinnerCampus = view.findViewById(R.id.change_spinner_campus);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()),R.style.Theme_Dialogs);
        builder.setTitle("Edit Lecturer");
        builder.setView(view);

        // set initial positive button text to edit
        positiveBtnText = "Edit";
        builder.setPositiveButton(positiveBtnText, null);

        // set initial negative button text to delete
        negativeBtnText = "Delete";
        builder.setNegativeButton(negativeBtnText, null);

        dialog = builder.create();
        dialog.show();

        Button btnCancel = view.findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        // configure positive button
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
            if (positiveBtnText.equalsIgnoreCase("edit")) {
                llEditLecturerDetails.setVisibility(View.VISIBLE);
                llLecturerDetails.setVisibility(View.GONE);

                // if button pressed and text == "edit" set positive button to "save"
                // and negative button "cancel"
                positiveBtnText = "Save";
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.save);

                negativeBtnText = "Cancel";
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(negativeBtnText);
            } else {
                Faculty f = new Faculty();
                f.setFacultyId(edtFacultyId.getText().toString());
                f.setFacultyName(edtFacultyName.getText().toString());
                f.setCampusId(campuses.get(spinnerCampus.getSelectedItemPosition()).getCampusId());
                presenter.updateFaculty(f);

                dialog.dismiss();
            }
        });

        // configure negative button
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(view1 -> {
            if (negativeBtnText.equalsIgnoreCase("cancel")) {
                dialog.dismiss();
            } else {
                presenter.deleteFaculty(item);
                dialog.dismiss();
            }
        });

        adapter.notifyDataSetChanged();
    }
}
