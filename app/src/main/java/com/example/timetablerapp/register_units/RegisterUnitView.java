package com.example.timetablerapp.register_units;

import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 10/06/19 -bernard
 */
public interface RegisterUnitView {
    void setUnits(List<Unit> units);

    void showMessage(String message);

    void startTimetableActivity();
}
