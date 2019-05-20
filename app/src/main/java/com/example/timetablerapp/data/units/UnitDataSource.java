package com.example.timetablerapp.data.units;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 19/05/19 -bernard
 */
public interface UnitDataSource extends DataSource<UnitDataSource.UnitsLoadedCallback, Unit> {

    void getUnitsByLecturerId(String id, UnitsLoadedCallback callback);

    interface UnitsLoadedCallback {
        void successful(List<Unit> units);
        void unsuccessful(String message);
    }
}
