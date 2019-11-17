package com.example.timetablerapp.registerunits;

import android.app.Activity;
import android.util.Log;

import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.DepartmentRepository;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.UnitsRepo;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 22/05/19 -bernard
 */
public class RegisterUnitsPresenter {
    private static final String TAG = RegisterUnitsPresenter.class.getSimpleName();

    private DepartmentRepository departmentRepo;
    private UnitsRepo unitsRepo;

    private Activity activity;
    private RegisterUnitView view;

    public RegisterUnitsPresenter(Activity activity,
                                  RegisterUnitView view,
                                  DepartmentRepository departmentRepo,
                                  UnitsRepo unitsRepo) {
        this.activity = activity;
        this.view = view;
        this.departmentRepo = departmentRepo;
        this.unitsRepo = unitsRepo;
    }

    public void getDepartment() {
        departmentRepo.getFromLocalDb(new DepartmentDS.LoadDepartmentsCallBack() {
            @Override
            public void loadDepartmentsSuccessful(List<Department> departments) {
                getUnitsByDepartment(departments.get(0));
            }

            @Override
            public void dataNotAvailable(String message) {
                Log.d(TAG, "dataNotAvailable: Data not available.");
            }
        });
    }

    private void getUnitsByDepartment(Department department) {
        unitsRepo.getAllUnitsByDepartmentId(department.getDepartmentId(), new UnitDataSource.UnitsLoadedCallback() {
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

    public void submitUnits(String userId, List<Unit> unitList) {
        unitsRepo.submitRegisteredUnits(userId, unitList, new UnitDataSource.UnitsRegisteredCallback() {
            @Override
            public void successful(String message) {
                view.showMessage(message);
                view.startTimetableActivity();
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }
}
