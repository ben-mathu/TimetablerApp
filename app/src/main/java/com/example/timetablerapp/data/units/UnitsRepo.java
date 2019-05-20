package com.example.timetablerapp.data.units;

import com.example.timetablerapp.data.DataSource;
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
}
