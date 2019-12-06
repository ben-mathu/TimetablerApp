package com.example.timetablerapp.dashboard;

import com.example.timetablerapp.MainView;
import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public interface DashboardView extends MainView {
    void setUnits(List<Unit> units);

    void showTimetable(List<Timetable> timetablelist);
}
