package com.example.timetablerapp.timetable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 23/05/19 -bernard
 */
public class ShowTimetableFragment extends Fragment implements UnitView {
    private static final String TAG = ShowTimetableFragment.class.getSimpleName();

    private UnitsPresenter presenter;

    private RecyclerView recyclerView;
    private TimetableAdapter adapter;

    private String role = "";

    public static ShowTimetableFragment newInstance() {
        return new ShowTimetableFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        role = MainApplication.getSharedPreferences()
                .getString(Constants.ROLE, "");

        presenter = new UnitsPresenter(this, MainApplication.getUnitRepo());

        if (role.equalsIgnoreCase("student")) {
            presenter.getTimetableByStudentId(
                    MainApplication.getSharedPreferences()
                        .getString(Constants.STUDENT_ID, "")
            );
        } else if (role.equalsIgnoreCase("lecturer")) {
            presenter.getTimetableByLecturerId(
                    MainApplication.getSharedPreferences()
                        .getString(Constants.LECTURER_ID, "")
            );
        } else if (role.equalsIgnoreCase("admin")){
            presenter.getTimetable();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_timetable, container, false);
        recyclerView = view.findViewById(R.id.recycler_timetable);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void setUnits(List<Unit> units) {

    }

    @Override
    public void showTimetable(List<Timetable> timetablelist) {
        adapter = new TimetableAdapter(timetablelist, getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setSalt(String salt) {

    }

    @Override
    public void showMessage(String message) {

    }
}
