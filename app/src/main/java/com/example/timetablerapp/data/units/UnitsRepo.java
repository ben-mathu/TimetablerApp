package com.example.timetablerapp.data.units;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.data.units.source.local.UnitsLocalDS;
import com.example.timetablerapp.data.units.source.remote.UnitsRemoteDS;

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
    public void update(Unit item) {

    }

    @Override
    public void delete(Unit item) {

    }

    @Override
    public void save(Unit item) {

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
}
