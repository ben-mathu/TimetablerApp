package com.example.timetablerapp.dashboard.dialog.more;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.campus.CampusesFragment;
import com.example.timetablerapp.dashboard.dialog.department.DepartmentsFragment;
import com.example.timetablerapp.dashboard.dialog.faculty.FacultiesFragment;
import com.example.timetablerapp.data.Constants;

/**
 * 07/09/19 -bernard
 */
public class MoreFragment extends Fragment {

    private Fragment fragment;

    private Button btnAddDep, btnAddFaculty, btnAddCampus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, null, false);
        FragmentManager fm = getActivity().getSupportFragmentManager();

        btnAddCampus = view.findViewById(R.id.button_add_campus);
        btnAddCampus.setOnClickListener(v -> {
            fragment = fm.findFragmentByTag(Constants.TAG_CAMPUS);

            if (fragment == null) {
                fragment = new CampusesFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment, Constants.TAG_CAMPUS)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnAddDep = view.findViewById(R.id.button_add_department);
        btnAddDep.setOnClickListener(v -> {
            fragment = fm.findFragmentByTag(Constants.TAG_DEPARTMENT);

            if (fragment == null) {
                fragment = new DepartmentsFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment, Constants.TAG_DEPARTMENT)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnAddFaculty = view.findViewById(R.id.button_add_faculty);
        btnAddFaculty.setOnClickListener(v -> {
            fragment = fm.findFragmentByTag(Constants.TAG_FACULTY);

            if (fragment == null) {
                fragment = new FacultiesFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment, Constants.TAG_FACULTY)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}
