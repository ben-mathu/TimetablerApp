package com.example.timetablerapp.timetable;

import com.example.timetablerapp.MainView;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public interface UnitView  extends MainView {
    void setUnits(List<Unit> units);
}
