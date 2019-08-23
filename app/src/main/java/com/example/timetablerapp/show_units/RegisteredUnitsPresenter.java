package com.example.timetablerapp.show_units;

import android.content.Context;

import com.example.timetablerapp.data.units.UnitApi;
import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.UnitsRepo;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.register_units.adapter_utils.UnitsAdapter;

import java.util.List;

/**
 * 22/08/19 -bernard
 */
class RegisteredUnitsPresenter {
    private RegisteredUnitsView view;
    private Context context;
    private UnitsRepo unitsRepo;

    public RegisteredUnitsPresenter(Context context, RegisteredUnitsView view, UnitsRepo unitRepo) {
        this.view = view;
        this.context = context;
        this.unitsRepo = unitRepo;
    }

    public void submitUnits(String userId, List<Unit> unitList, UnitsAdapter adapter) {
        unitsRepo.removeUnits(userId, unitList, new UnitDataSource.UnitsRegisteredCallback() {
            @Override
            public void successful(String message) {
                view.showMessage(message);

                adapter.removeUnitsFromList(unitList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void getUnits(String userId, String userType) {
        if (userType.equalsIgnoreCase("student")) {
            unitsRepo.getUnitsByStudentId(userId, new UnitDataSource.UnitsLoadedCallback() {
                @Override
                public void successful(List<Unit> units) {
                    view.setUnits(units);
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        } else if (userType.equalsIgnoreCase("lecturer")) {
            unitsRepo.getUnitsByLecturerId(userId, new UnitDataSource.UnitsLoadedCallback() {
                @Override
                public void successful(List<Unit> units) {
                    view.setUnits(units);
                }

                @Override
                public void unsuccessful(String message) {
                    view.showMessage(message);
                }
            });
        } else if (userType.equalsIgnoreCase("admin")) {
            unitsRepo.getUnits(new UnitDataSource.UnitsLoadedCallback() {
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
    }
}
