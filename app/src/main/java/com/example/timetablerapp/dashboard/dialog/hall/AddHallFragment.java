package com.example.timetablerapp.dashboard.dialog.hall;

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
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.util.CompareStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 03/12/19
 *
 * @author bernard
 */
public class AddHallFragment extends Fragment implements HallView, OnItemSelectedListener<Hall> {
    private static final String TAG = AddHallFragment.class.getSimpleName();

    private List<Hall> list;
    private List<Faculty> faculties;

    private Faculty faculty;
    private Hall hall;

    private HallPresenter presenter;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private HallAdapter adapter;
    private Spinner spinnerFaculty;
    private Button btnAddHall;
    private AlertDialog.Builder builder;

    private String facultyName;
    private TextView txtFacultyName;
    private String positiveBtnText;
    private AlertDialog dialog;

    @Override
    public void onStart() {
        super.onStart();
        presenter = new HallPresenter(this,
                MainApplication.getFacultyRepo(),
                MainApplication.getHallRepo());
        presenter.getHalls();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        presenter =  new HallPresenter(this,
                MainApplication.getFacultyRepo(),
                MainApplication.getHallRepo());

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
                List<Hall> filteredList = filterList(list, s);
                if (adapter != null) {
                    adapter.setList(filteredList);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.hall_fields, null, false);
        EditText edtHallId = dialogView.findViewById(R.id.edit_hall_id),
                edtHallName = dialogView.findViewById(R.id.edit_hall_name);

        spinnerFaculty = dialogView.findViewById(R.id.spinner_faculty);
        spinnerFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                facultyName = parent.getItemAtPosition(position).toString();
                faculty = faculties.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                facultyName = parent.getSelectedItem().toString();
                faculty = faculties.get(parent.getSelectedItemPosition());
            }
        });

        btnAddHall = view.findViewById(R.id.button_add_item);
        btnAddHall.setText("Add Hall");
        btnAddHall.setOnClickListener(v -> {
            presenter.getFacultiesForHalls();
            builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.Theme_Dialogs);
            builder.setCancelable(true);
            builder.setView(dialogView);
            builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                if (edtHallId.getText().toString().isEmpty()
                        || edtHallName.getText().toString().isEmpty()
                ) {
                    showMessage("All the fields are required");
                } else {
                    Hall hall = new Hall();
                    hall.setFacultyId(faculty.getFacultyId());
                    hall.setHallId(edtHallId.getText().toString());
                    hall.setHallName(edtHallName.getText().toString());

                    presenter.addHall(hall);
                }
            });
            builder.setNegativeButton("Cancel", ((dialogInterface, i) -> dialogInterface.dismiss()));

            //
            if (dialogView.getParent() != null) {
                ((ViewGroup) dialogView.getParent()).removeView(dialogView);
            }

            AlertDialog dialog = builder.create();
            dialog.show();
            Window window = dialog.getWindow();
            assert window != null;
            window.setLayout(550, ViewGroup.LayoutParams.WRAP_CONTENT);
        });
        return view;
    }

    private List<Hall> filterList(List<Hall> list, String s) {
        List<Hall> items = new ArrayList<>();

        if (list != null) {
            for (Hall h : list) {
                if (CompareStrings.compare(h.getHallId(), s)) {
                    items.add(h);
                }
            }
        }
        return items;
    }

    @Override
    public void setFaculties(List<Faculty> list) {
        this.faculties = list;
        List<String> facultyNames = new ArrayList<>();
        for (Faculty faculty : faculties) {
            facultyNames.add(faculty.getFacultyName());
        }
        spinnerFaculty.setAdapter(getArrayAdapter(facultyNames));
    }

    @Override
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;

        txtFacultyName.setText(this.faculty.getFacultyName());
    }

    private ArrayAdapter<String> getArrayAdapter(List<String> nameList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_spinner_dropdown_item, nameList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return arrayAdapter;
    }

    @Override
    public void showMessage(String required) {
        if (getActivity() != null)
            Toast.makeText(getActivity(), required, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setHalls(List<Hall> halls) {
        this.list = halls;

        adapter = new HallAdapter(getActivity(), list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(Hall item) {
        this.hall = item;
        presenter.getFacultyById(item.getFacultyId());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_hall, null, false);

        LinearLayout llHallDetails = view.findViewById(R.id.ll_hall_details);
        LinearLayout llHallEditDetails = view.findViewById(R.id.ll_hall_edit_details);

        // Before editing
        // Display details in plain text.
        TextView txtHallId = view.findViewById(R.id.text_hall_id);
        txtHallId.setText(item.getHallId());

        TextView txtHallName = view.findViewById(R.id.text_hall_name);
        txtHallName.setText(item.getHallName());
        txtFacultyName = view.findViewById(R.id.text_faculty_name);

        // editing section
        EditText edtHallId = view.findViewById(R.id.edit_hall_id);
        edtHallId.setText(item.getHallId());

        EditText edtHallName = view.findViewById(R.id.edit_hall_name);
        edtHallName.setText(item.getHallName());

        presenter.getFacultiesForHalls();
        spinnerFaculty = view.findViewById(R.id.change_spinner_faculty);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()),R.style.Theme_Dialogs);
        builder.setView(view);
        builder.setTitle("Edit Hall");
        positiveBtnText = "Edit";

        builder.setPositiveButton(positiveBtnText, null);

        builder.setNegativeButton(R.string.delete, (dialogInterface, i) -> {
            presenter.deleteHall(item);
        });


        dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {
            if (positiveBtnText.equalsIgnoreCase("edit")) {
                llHallEditDetails.setVisibility(View.VISIBLE);
                llHallDetails.setVisibility(View.GONE);

                positiveBtnText = "Save";

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.save);
            } else {
                Hall h = new Hall();
                h.setHallId(edtHallId.getText().toString());
                h.setHallName(edtHallName.getText().toString());
                h.setFacultyId(faculties.get(spinnerFaculty.getSelectedItemPosition()).getFacultyId());
                presenter.updateHall(hall);
            }
        });

        adapter.notifyDataSetChanged();
    }
}
