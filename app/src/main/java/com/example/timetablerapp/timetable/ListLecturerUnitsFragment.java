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
import android.widget.Toast;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.R;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.timetable.adapter_utils.UnitsAdapter;

import java.util.List;

/**
 * 19/05/19 -bernard
 *
 * List units associated with the lecturer
 */
public class ListLecturerUnitsFragment extends Fragment implements UnitView {

    private UnitsAdapter adapter;
    private UnitsPresenter presenter;

    private RecyclerView listView;

    private String role = "";

    public ListLecturerUnitsFragment() {
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();

        Fragment fragment = new ListLecturerUnitsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new UnitsPresenter(this, MainApplication.getUnitRepo());

        role = MainApplication.getSharedPreferences().getString(Constants.ROLE, "");

        if (role.equalsIgnoreCase("lecturer")) {
            presenter.getUnitsByLecturerId(
                    MainApplication.getSharedPreferences()
                        .getString(Constants.LECTURER_ID, "")
            );
        } else if (role.equalsIgnoreCase("student")) {
            presenter.getUnitsByStudentId(
                    MainApplication.getSharedPreferences()
                    .getString(Constants.STUDENT_ID, "")
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_units, container, false);

        listView = view.findViewById(R.id.list_units);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void setSalt(String salt) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUnits(List<Unit> units) {
        adapter = new UnitsAdapter(units);
        listView.setAdapter(adapter);
    }
}
