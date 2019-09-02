package com.example.timetablerapp.dashboard;

import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.UnitsRepo;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.lecturer.model.LecRequest;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public class DashboardPresenter {
    private DashboardView view;
    private LecturerView lecView;
    private UnitsRepo unitsRepo;
    private LecturerRepo lecturerRepo;

    public DashboardPresenter(DashboardView view, UnitsRepo unitsRepo) {
        this.view = view;
        this.unitsRepo = unitsRepo;
    }

    public DashboardPresenter(LecturerView view, LecturerRepo lecturerRepo) {
        this.lecView = view;
        this.lecturerRepo = lecturerRepo;
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

    public void getLecturers() {
        lecturerRepo.getLecturers(new LecturerDS.LecturersLoadedCallback() {

            @Override
            public void successfullyLoaded(List<Lecturer> list) {
                lecView.setLecturers(list);
            }

            @Override
            public void unsuccessful(String message) {
                lecView.showMessage(message);
            }
        });
    }

    public void createLecturer(String email, String fname, String mname, String lname) {
        lecturerRepo.createLecturer(email, fname, mname, lname, new LecturerDS.CreatingLecturerCallback() {
            @Override
            public void successfullyCreated(LecResponse response) {
                lecView.showMessage(response.getMessage());
                lecView.sendEmail(response);
            }

            @Override
            public void unSuccessful(String message) {
                lecView.showMessage(message);
            }
        });
    }
}
