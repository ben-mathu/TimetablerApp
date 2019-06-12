package com.example.timetablerapp.timetable;

import com.example.timetablerapp.BasePresenter;
import com.example.timetablerapp.data.timetable.model.Timetable;
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

    public void getTimetableByStudentId(String studentId) {
        unitsRepo.getTimetableByStudentId(studentId, new UnitDataSource.TimetableLoadedCallback() {

            @Override
            public void successful(List<Timetable> timetablelist) {
                view.showTimetable(timetablelist);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getTimetableByLecturerId(String lecturerId) {
        unitsRepo.getTimetableByLecturerId(lecturerId, new UnitDataSource.TimetableLoadedCallback() {
            @Override
            public void successful(List<Timetable> timetableList) {

            }

            @Override
            public void unsuccessful(String message) {

            }
        });
    }

    public void getTimetable() {
        unitsRepo.getTimetable(new UnitDataSource.TimetableLoadedCallback() {
            @Override
            public void successful(List<Timetable> timetableList) {
                view.showTimetable(timetableList);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void setDeadline(String startDate, String deadline) {
        unitsRepo.setRegistrationDeadline(startDate, deadline, new UnitDataSource.UnitsRegisteredCallback() {
            @Override
            public void successful(String message) {
                view.showMessage(message);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }
}
