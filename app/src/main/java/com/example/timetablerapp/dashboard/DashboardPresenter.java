package com.example.timetablerapp.dashboard;

import com.example.timetablerapp.Presenter;
import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.hall.HallDS;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.programmes.ProgrammeDS;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.room.Room;
import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.UnitsRepo;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public class DashboardPresenter extends Presenter<DashboardView> {
    private DashboardView view;
    private UnitsRepo unitsRepo;

    public DashboardPresenter(UnitsRepo unitsRepo) {
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
                view.showTimetable(timetableList);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
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

    @Override
    public void setView(DashboardView item) {
        this.view = item;
    }
}
