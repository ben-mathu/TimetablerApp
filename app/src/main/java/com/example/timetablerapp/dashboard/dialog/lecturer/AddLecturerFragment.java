package com.example.timetablerapp.dashboard.dialog.lecturer;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.DashboardPresenter;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

import java.util.ArrayList;
import java.util.List;

/**
 * 01/09/19 -bernard
 */
public class AddLecturerFragment extends Fragment implements LecturerView {

    private List<Lecturer> list;
    private LecturerAdapter adapter;

    private DashboardPresenter presenter;
    private Button btnCreateUser;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    public void onStart() {
        super.onStart();
        presenter = new DashboardPresenter(this, MainApplication.getLecturerRepo());

        presenter.getLecturers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_lecturer, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_lecturers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Lecturer> filteredList = filterList(list, s);
                if (adapter != null) {
                    adapter.setList(filteredList);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        btnCreateUser = view.findViewById(R.id.button_add_lecturer);
        btnCreateUser.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Dialogs);

            View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.lecturer_fields, null, false);
            EditText edtFName = dialogView.findViewById(R.id.edit_first_name),
                    edtMName = dialogView.findViewById(R.id.edit_middle_name),
                    edtLName = dialogView.findViewById(R.id.edit_last_name),
                    edtEmail = dialogView.findViewById(R.id.edit_email);
            builder.setView(dialogView);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (edtEmail.getText().toString().isEmpty()
                            || edtFName.getText().toString().isEmpty()
                            || edtLName.getText().toString().isEmpty()
                            || edtMName.getText().toString().isEmpty()
                    ) {
                        showMessage("All the fields are required");
                    } else {
                        presenter.createLecturer(edtEmail.getText().toString(), edtFName.getText().toString(),
                                edtLName.getText().toString(), edtMName.getText().toString()
                        );
                    }
                }
            });

            builder.setCancelable(true);
            AlertDialog dialog = builder.create();
            dialog.show();

            Window window = dialog.getWindow();
            window.setLayout(550, ViewGroup.LayoutParams.WRAP_CONTENT);
        });
        return view;
    }

    private List<Lecturer> filterList(List<Lecturer> list, String s) {
        List<Lecturer> items = new ArrayList<>();

        if (list != null) {
            for (Lecturer lecturer : list) {
                if (lecturer.getFirstName().contains(s) || lecturer.getMiddleName().contains(s)
                        || lecturer.getLastName().contains(s) || lecturer.getId().contains(s)
                ) {

                    items.add(lecturer);
                }
            }
        }

        return items;
    }

    @Override
    public void setLecturers(List<Lecturer> list) {
        if (list != null) {
            if (!list.isEmpty()) {
                this.list = list;
                adapter = new LecturerAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void showMessage(String message) {
        try {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmail(LecResponse response) {
        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_EMAIL, new String[]{response.getEmail()})
                .putExtra(Intent.EXTRA_SUBJECT, "Account Creation")
                .putExtra(Intent.EXTRA_TEXT, response.getMessage() + "\nName: " + response.getFname() + " " + response.getMname() + " " + response.getLname()
                        + "\nAccess code: " + response.getCode())
                .setType("message/rfc822");

        try {
            startActivity(Intent.createChooser(intent, "Send email:"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
