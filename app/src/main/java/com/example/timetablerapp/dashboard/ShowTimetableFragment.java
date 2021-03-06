package com.example.timetablerapp.dashboard;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 23/05/19 -bernard
 */
public class ShowTimetableFragment extends Fragment implements DashboardView {
    private static final String TAG = ShowTimetableFragment.class.getSimpleName();

    private DashboardPresenter presenter;

    private RecyclerView recyclerViewTimetable;
    private TimetableAdapter adapter;

    private String role = "";

    private int screenOrientation;

    public static ShowTimetableFragment newInstance() {
        return new ShowTimetableFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Initialize presenter onStart
        presenter = new DashboardPresenter(this, MainApplication.getUnitRepo());

        role = MainApplication.getSharedPreferences()
                .getString(Constants.ROLE, "");

        if (role.equalsIgnoreCase("student")) {
            presenter.getTimetableByStudentId(
                    MainApplication.getSharedPreferences()
                            .getString(Constants.USER_ID, "")
            );
        } else if (role.equalsIgnoreCase("lecturer")) {
            presenter.getTimetableByLecturerId(
                    MainApplication.getSharedPreferences()
                            .getString(Constants.USER_ID, "")
            );
        } else if (role.equalsIgnoreCase("admin")){
            presenter.getTimetable();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_timetable, container, false);
        recyclerViewTimetable = view.findViewById(R.id.recycler_timetable);

        screenOrientation = getResources().getConfiguration().orientation;

        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerViewTimetable.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerViewTimetable.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }
        return view;
    }

    @Override
    public void setUnits(List<Unit> units) {
        // left blank intentionally - not essential for this fragment
    }

    @Override
    public void showTimetable(List<Timetable> timetableList) {
        if (timetableList != null) {
            if (!timetableList.isEmpty()) {
                adapter = new TimetableAdapter(timetableList, getActivity());
                recyclerViewTimetable.setAdapter(adapter);
            }
        }
    }

    @Override
    public void setSalt(String salt) {
        // left blank intentionally - not essential for this fragment
    }

    @Override
    public void showMessage(String message) {
        if (getActivity() != null)
            Toast.makeText(getActivity(), "Message: " + message, Toast.LENGTH_SHORT).show();
    }
}
