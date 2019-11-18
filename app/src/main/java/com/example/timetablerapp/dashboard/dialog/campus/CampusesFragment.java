package com.example.timetablerapp.dashboard.dialog.campus;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.DashboardPresenter;
import com.example.timetablerapp.dashboard.dialog.OnItemSelectedListener;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.util.CompareStrings;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 06/09/19 -bernard
 */
public class CampusesFragment extends Fragment implements CampusView, OnItemSelectedListener<Campus> {
    private List<Campus> campuses;
    // Classes
    private CampusAdapter adapter;
    private CampusPresenter presenter;

    // Widget
    private RecyclerView recyclerCampuses;
    private AlertDialog dialog;

    // Literals
    private boolean isEditMode = false;
    private String positiveBtnText = "";

    @Override
    public void onStart() {
        super.onStart();
        presenter = new CampusPresenter(this, MainApplication.getCampusRepo());
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

            AlertDialog dialog = builder.create();
            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(550, ViewGroup.LayoutParams.WRAP_CONTENT);
        });
        return view;
    }

    private List<Campus> filterList(List<Campus> list, String s) {
        List<Campus> items = new ArrayList<>();

        if (list != null) {
            for (Campus campus : list) {
                if (CompareStrings.compare(campus.getCampusName(), s)) {
                    items.add(campus);
                }
            }
        }
        return items;
    }

    @Override
    public void setList(List<Campus> campuses) {
        this.campuses = campuses;

        adapter = new CampusAdapter(campuses, getActivity(), this);
        recyclerCampuses.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCampus(Campus campus) {
        if (campuses == null) {
            campuses = new ArrayList<>();
        }
        campuses.add(campus);

        if (adapter == null) {
            adapter = new CampusAdapter(campuses, getActivity(), this);
        } else {
            adapter.setList(campuses);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(Campus campus) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_campus, null, false);
        LinearLayout llCampusDetails = view.findViewById(R.id.ll_campus_details),
                llCampusEditDetails = view.findViewById(R.id.ll_campus_edit_details);
        EditText edtCampusId = view.findViewById(R.id.edit_campus_id);
        edtCampusId.setText(campus.getCampusId());
        edtCampusId.setEnabled(false);
        EditText edtCampusName = view.findViewById(R.id.edit_campus_name);
        edtCampusName.setText(campus.getCampusName());
        TextView txtCampusId = view.findViewById(R.id.text_campus_id);
        txtCampusId.setText(campus.getCampusId());
        TextView txtCampusName = view.findViewById(R.id.text_campus_name);
        txtCampusName.setText(campus.getCampusName());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Dialogs);
        builder.setView(view);
        builder.setTitle("Edit Campus");
        positiveBtnText = "Edit";

        builder.setPositiveButton(positiveBtnText, null);

        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.deleteCampus(campus);
            }
        });

        dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positiveBtnText.equalsIgnoreCase("edit")) {
                    llCampusEditDetails.setVisibility(View.VISIBLE);
                    llCampusDetails.setVisibility(View.GONE);

                    positiveBtnText = "Save";

                    // change dialog's button's text
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(R.string.save);
                } else {
                    Campus c = new Campus();
                    c.setCampusId(edtCampusId.getText().toString());
                    c.setCampusName(edtCampusName.getText().toString());
                    presenter.updateCampus(c);
                    dialog.dismiss();
                }
            }
        });
    }
}
