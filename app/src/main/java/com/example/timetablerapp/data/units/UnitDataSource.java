package com.example.timetablerapp.data.units;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.timetable.model.Timetable;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public interface UnitDataSource extends DataSource<UnitDataSource.UnitsLoadedCallback, Unit> {

    void getUnitsByLecturerId(String id, UnitsLoadedCallback callback);

    void getUnitsByStudentId(String strId, UnitsLoadedCallback callback);

    void getTimetableByStudentId(String studentId, TimetableLoadedCallback callback);

    void getTimetableByLecturerId(String lecturerId, TimetableLoadedCallback callback);

    void getTimetable(TimetableLoadedCallback callback);

    interface UnitsLoadedCallback {
        void successful(List<Unit> units);
        void unsuccessful(String message);
    }

    interface TimetableLoadedCallback {
        void successful(List<Timetable> timetableList);
        void unsuccessful(String message);
    }
}
