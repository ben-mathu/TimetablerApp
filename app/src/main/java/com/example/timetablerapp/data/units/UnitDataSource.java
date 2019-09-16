package com.example.timetablerapp.data.units;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public interface UnitDataSource extends DataSource<Unit> {

    void getUnitsByLecturerId(String id, UnitsLoadedCallback callback);

    void getUnitsByStudentId(String strId, UnitsLoadedCallback callback);

    void getUnits(UnitsLoadedCallback callback);

    void getTimetableByStudentId(String studentId, TimetableLoadedCallback callback);

    void getTimetableByLecturerId(String lecturerId, TimetableLoadedCallback callback);

    void getTimetable(TimetableLoadedCallback callback);

    void getAllUnitsByDepartmentId(String departmentId, UnitsLoadedCallback callback);

    void submitRegisteredUnits(String userId, List<Unit> unitList, UnitsRegisteredCallback callback);

    void setRegistrationDeadline(String startDate, String deadline, UnitsRegisteredCallback callback);

    void removeUnits(String userId, List<Unit> unitList, UnitsRegisteredCallback callback);

    void deleteCourse(Unit item, UnitsRegisteredCallback callback);

    void updateCourse(Unit unit, UnitsRegisteredCallback callback);


    interface UnitsRegisteredCallback {
        void successful(String message);
        void unsuccessful(String message);
    }

    interface UnitsLoadedCallback {
        void successful(List<Unit> units);
        void unsuccessful(String message);
    }

    interface TimetableLoadedCallback {
        void successful(List<Timetable> timetableList);
        void unsuccessful(String message);
    }
}
