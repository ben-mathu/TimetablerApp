package com.example.timetablerapp.timetable;

import com.example.timetablerapp.BasePresenter;
import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.UnitsRepo;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public class UnitsPresenter {
    private UnitView view;
    private UnitsRepo unitsRepo;

    public UnitsPresenter(UnitView view, UnitsRepo unitsRepo) {
        this.view = view;
        this.unitsRepo = unitsRepo;
    }

    public void getUnitsByLecturerId(String strId) {
        unitsRepo.getUnitsByLecturerId(strId, new UnitDataSource.UnitsLoadedCallback() {
            @Override
            public void successful(List<Unit> units) {
                view.setUnits(units);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getUnitsByStudentId(String strId) {
        unitsRepo.getUnitsByStudentId(strId, new UnitDataSource.UnitsLoadedCallback() {
            @Override
            public void successful(List<Unit> units) {
                view.setUnits(units);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }
}
