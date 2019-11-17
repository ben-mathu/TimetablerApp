package com.example.timetablerapp.showunits;

import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 22/08/19 -bernard
 */
public interface RegisteredUnitsView {
    void showMessage(String message);

    void startTimetableActivity();

    void setUnits(List<Unit> units);
}
