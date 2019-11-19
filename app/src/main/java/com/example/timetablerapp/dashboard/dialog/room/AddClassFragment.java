package com.example.timetablerapp.dashboard.dialog.room;

import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.Switch;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.DashboardPresenter;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.room.Room;
import com.example.timetablerapp.util.CompareStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * 01/09/19 -bernard
 */
public class AddClassFragment extends Fragment implements RoomView {
    private List<Room> list;
    private List<Faculty> faculties;
    private List<Hall> halls;

    private RoomAdapter adapter;

    private RoomPresenter presenter;
    private AlertDialog.Builder builder;
    private Spinner spinnerFaculty, spinnerHall;
    private Button btnAddCourse;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Faculty faculty;

    private String facultyName;
    private String hallName;
    private Hall hall;

    @Override
    public void onStart() {
        super.onStart();
        presenter = new RoomPresenter(this,
                MainApplication.getFacultyRepo(),
                MainApplication.getHallRepo());
        presenter.getRooms();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        presenter =  new RoomPresenter(this,
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
                List<Room> filteredList = filterList(list, s);
                if (adapter != null) {
                    adapter.setList(filteredList);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.room_fields, null, false);
        EditText edtRoomId = dialogView.findViewById(R.id.edit_RoomId),
                edtVolume = dialogView.findViewById(R.id.edit_Volume),
                edtPassCode = dialogView.findViewById(R.id.edit_pass_code);

        spinnerFaculty = dialogView.findViewById(R.id.spinner_faculty);
        spinnerFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                facultyName = parent.getItemAtPosition(position).toString();
                faculty = faculties.get(position);
                presenter.getHalls(faculties.get(position).getFacultyId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                facultyName = parent.getSelectedItem().toString();
                faculty = faculties.get(parent.getSelectedItemPosition());
                presenter.getHalls(faculties.get(parent.getSelectedItemPosition()).getFacultyId());
            }
        });

        spinnerHall = dialogView.findViewById(R.id.spinner_halls);
        spinnerHall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                hallName = parent.getItemAtPosition(position).toString();
                hall = halls.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hallName = parent.getSelectedItem().toString();
                hall = halls.get(parent.getSelectedItemPosition());
            }
        });

        Switch switchAvailability = dialogView.findViewById(R.id.switch_availability),
                switchLab = dialogView.findViewById(R.id.switch_lab);
        edtPassCode.setVisibility(View.VISIBLE);

        btnAddCourse = view.findViewById(R.id.button_add_item);
        btnAddCourse.setOnClickListener(v -> {

            presenter.getFacultiesForHalls();
            builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Dialogs);
            builder.setCancelable(true);
            builder.setView(dialogView);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (edtRoomId.getText().toString().isEmpty()
                            || edtVolume.getText().toString().isEmpty()
                    ) {
                        showMessage("All the fields are required");
                    } else {
                        Room room = new Room();
                        room.setAvailability(switchAvailability.isChecked());
                        room.setFacultyId(faculty.getFacultyId());
                        room.setHall_id(hall.getHallId());
                        room.setId(edtRoomId.getText().toString());
                        room.setLab(switchLab.isChecked());
                        room.setVolume(edtVolume.getText().toString());

                        presenter.addRoom(room, edtPassCode.getText().toString());
                    }
                }
            });

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

    private List<Room> filterList(List<Room> list, String s) {
        List<Room> items = new ArrayList<>();

        if (list != null) {
            for (Room room : list) {
                if (CompareStrings.compare(room.getId(), s)) {
                    items.add(room);
                }
            }
        }
        return items;
    }

    @Override
    public void setRooms(List<Room> rooms) {
        this.list = rooms;

        adapter = new RoomAdapter(getActivity(), rooms);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setHalls(List<Hall> halls) {
        this.halls = halls;
        List<String> hallNames = new ArrayList<>();
        for (Hall hall : halls) {
            hallNames.add(hall.getHallName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, hallNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHall.setAdapter(arrayAdapter);
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
}
