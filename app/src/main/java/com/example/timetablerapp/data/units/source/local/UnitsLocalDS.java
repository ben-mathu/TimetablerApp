package com.example.timetablerapp.data.units.source.local;

import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public class UnitsLocalDS implements UnitDataSource {
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

    }

    @Override
    public void getUnitsByStudentId(String strId, UnitsLoadedCallback callback) {

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
}
