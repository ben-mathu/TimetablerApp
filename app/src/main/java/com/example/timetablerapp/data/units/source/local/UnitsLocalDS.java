package com.example.timetablerapp.data.units.source.local;

import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.util.SuccessfulCallback;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public class UnitsLocalDS implements UnitDataSource {
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

    }

    @Override
    public void getUnitsByStudentId(String strId, UnitsLoadedCallback callback) {

    }

    @Override
    public void getUnits(UnitsLoadedCallback callback) {

    }

    @Override
    public void getTimetableByStudentId(String studentId, TimetableLoadedCallback callback) {

    }

    @Override
    public void getTimetableByLecturerId(String lecturerId, TimetableLoadedCallback callback) {

    }

    @Override
    public void getTimetable(TimetableLoadedCallback callback) {

    }

    @Override
    public void getAllUnitsByDepartmentId(String departmentId, UnitsLoadedCallback callback) {

    }

    @Override
    public void submitRegisteredUnits(String userId, List<Unit> unitList, UnitsRegisteredCallback callback) {

    }

    @Override
    public void setRegistrationDeadline(String startDate, String deadline, UnitsRegisteredCallback callback) {

    }

    @Override
    public void removeUnits(String userId, List<Unit> unitList, UnitsRegisteredCallback callback) {

    }

    @Override
    public void deleteCourse(Unit item, UnitsRegisteredCallback callback) {

    }

    @Override
    public void updateCourse(Unit unit, UnitsRegisteredCallback callback) {

    }
}
