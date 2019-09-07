package com.example.timetablerapp.dashboard.dialog.campus;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.DashboardPresenter;
import com.example.timetablerapp.data.campuses.model.Campus;

import java.util.ArrayList;
import java.util.List;

/**
 * 06/09/19 -bernard
 */
public class CampusesFragment extends Fragment implements CampusView {
    private List<Campus> campuses;
    // Classes
    private CampusAdapter adapter;
    private DashboardPresenter presenter;

    // Widget
    private RecyclerView recyclerCampuses;

    @Override
    public void onStart() {
        super.onStart();
        presenter = new DashboardPresenter(this,
                MainApplication.getCampusRepo());
        presenter.getCampuses();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, null, false);

        recyclerCampuses = view.findViewById(R.id.recycler_view);
        recyclerCampuses.setLayoutManager(new LinearLayoutManager(getActivity()));

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Campus> filteredList = filterList(campuses, s);
                if (adapter != null) {
                    adapter.setList(filteredList);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        Button btnAddCampus = view.findViewById(R.id.button_add_item);
        btnAddCampus.setText(R.string.add_campus);
        btnAddCampus.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.dialog_campus, null, false);
            EditText edtCampusName = dialogView.findViewById(R.id.edit_campus_name);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Dialogs)
                    .setPositiveButton("Send", (dialogInterface, i) -> {
                        Campus campus = new Campus("", edtCampusName.getText().toString());
                        presenter.addCampus(campus);
                    }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.setTitle("Add Campus");
            builder.setView(dialogView);
            builder.setCancelable(false);
            builder.create().show();

        });
        return view;
    }

    private List<Campus> filterList(List<Campus> list, String s) {
        List<Campus> items = new ArrayList<>();

        if (list != null) {
            for (Campus campus : list) {
                if (campus.getCampusId().contains(s)) {
                    items.add(campus);
                }
            }
        }
        return items;
    }

    @Override
    public void setList(List<Campus> campuses) {
        this.campuses = campuses;

        adapter = new CampusAdapter(campuses, getActivity());
        recyclerCampuses.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
