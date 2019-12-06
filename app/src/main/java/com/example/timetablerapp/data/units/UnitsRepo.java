package com.example.timetablerapp.data.units;

import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.data.units.source.local.UnitsLocalDS;
import com.example.timetablerapp.data.units.source.remote.UnitsRemoteDS;
import com.example.timetablerapp.util.SuccessfulCallback;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public class UnitsRepo implements UnitDataSource {
    private static UnitsRepo INSTANCE;
    private final UnitsLocalDS unitsLocalDS;
    private final UnitsRemoteDS unitsRemoteDS;

    public UnitsRepo(UnitsLocalDS unitsLocalDS, UnitsRemoteDS unitsRemoteDS) {

        this.unitsLocalDS = unitsLocalDS;
        this.unitsRemoteDS = unitsRemoteDS;
    }

    public static UnitsRepo newInstance(UnitsLocalDS unitsLocalDS, UnitsRemoteDS unitsRemoteDS) {
        if (INSTANCE == null) {
            INSTANCE = new UnitsRepo(unitsLocalDS, unitsRemoteDS);
        }
        return INSTANCE;
    }

    @Override
    public void update(Unit item, SuccessfulCallback callback) {

    }

    @Override
    public void delete(Unit item, SuccessfulCallback callback) {

    }

    @Override
    public void save(Unit item, SuccessfulCallback callback) {

    }

    @Override
    public void getUnitsByLecturerId(String id, UnitsLoadedCallback callback) {
        unitsRemoteDS.getUnitsByLecturerId(id, new UnitsLoadedCallback() {
            @Override
            public void successful(List<Unit> units) {
                callback.successful(units);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void getUnitsByStudentId(String strId, UnitsLoadedCallback callback) {
        unitsRemoteDS.getUnitsByStudentId(strId, new UnitsLoadedCallback() {
            @Override
            public void successful(List<Unit> units) {
                callback.successful(units);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void getUnits(UnitsLoadedCallback callback) {
        unitsRemoteDS.getUnits(new UnitsLoadedCallback() {
            @Override
            public void successful(List<Unit> units) {
                callback.successful(units);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    public void getCourses(UnitsLoadedCallback callback) {
        unitsRemoteDS.getCourses(new UnitsLoadedCallback() {
            @Override
            public void successful(List<Unit> units) {
                callback.successful(units);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void getTimetableByStudentId(String studentId, TimetableLoadedCallback callback) {
        unitsRemoteDS.getTimetableByStudentId(studentId, new TimetableLoadedCallback() {
            @Override
            public void successful(List<Timetable> timetableList) {
                callback.successful(timetableList);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void getTimetableByLecturerId(String lecturerId, TimetableLoadedCallback callback) {
        unitsRemoteDS.getTimetableByLecturerId(lecturerId, new TimetableLoadedCallback() {
            @Override
            public void successful(List<Timetable> timetableList) {
                callback.successful(timetableList);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void getTimetable(TimetableLoadedCallback callback) {
        unitsRemoteDS.getTimetable(new TimetableLoadedCallback() {
            @Override
            public void successful(List<Timetable> timetableList) {
                callback.successful(timetableList);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void getAllUnitsByDepartmentId(String departmentId, UnitsLoadedCallback callback) {
        unitsRemoteDS.getAllUnitsByDepartmentId(departmentId, new UnitsLoadedCallback() {
            @Override
            public void successful(List<Unit> units) {
                callback.successful(units);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void submitRegisteredUnits(String userId, List<Unit> unitList, UnitsRegisteredCallback callback) {
        unitsRemoteDS.submitRegisteredUnits(userId, unitList, new UnitsRegisteredCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void setRegistrationDeadline(String startDate, String deadline, UnitsRegisteredCallback callback) {
        unitsRemoteDS.setRegistrationDeadline(startDate, deadline, new UnitsRegisteredCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void removeUnits(String userId, List<Unit> unitList, UnitsRegisteredCallback callback) {
        unitsRemoteDS.removeUnits(userId, unitList, new UnitDataSource.UnitsRegisteredCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void deleteCourse(Unit item, UnitsRegisteredCallback callback) {
        unitsRemoteDS.deleteCourse(item, new UnitsRegisteredCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void updateCourse(Unit unit, UnitsRegisteredCallback callback) {
        unitsRemoteDS.updateCourse(unit, new UnitsRegisteredCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    public void addCourse(Unit unit, String passcode, UnitsRegisteredCallback callback) {
        unitsRemoteDS.addCourse(unit, passcode, new UnitsRegisteredCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }
}
