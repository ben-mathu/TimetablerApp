package com.example.timetablerapp.data.department;

import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.department.source.DepartmentLocalDataSrc;
import com.example.timetablerapp.data.department.source.DepartmentRemoteDataSrc;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class DepartmentRepository implements DepartmentDS {
    private static DepartmentRepository INSTANCE = null;
    private DepartmentLocalDataSrc departmentLocalDataSrc;
    private DepartmentRemoteDataSrc departmentRemoteDataSrc;

    public DepartmentRepository(DepartmentLocalDataSrc departmentLocalDataSrc, DepartmentRemoteDataSrc departmentRemoteDataSrc) {
        this.departmentLocalDataSrc = departmentLocalDataSrc;
        this.departmentRemoteDataSrc = departmentRemoteDataSrc;
    }

    public static DepartmentRepository newInstance(DepartmentLocalDataSrc departmentLocalDataSrc, DepartmentRemoteDataSrc departmentRemoteDataSrc) {
        if (INSTANCE == null) {
            INSTANCE = new DepartmentRepository(departmentLocalDataSrc, departmentRemoteDataSrc);
        }
        return INSTANCE;
    }

    @Override
    public void getDepsByIdFromRemote(LoadDepartmentsCallBack callBack, String id) {
        departmentRemoteDataSrc.getDepsByIdFromRemote(new LoadDepartmentsCallBack() {
            @Override
            public void loadDepartmentsSuccessful(List<Department> departments) {
                callBack.loadDepartmentsSuccessful(departments);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        }, id);
    }

    @Override
    public void getFromLocalDb(LoadDepartmentsCallBack callBack) {
        departmentLocalDataSrc.getFromLocalDb(new LoadDepartmentsCallBack() {
            @Override
            public void loadDepartmentsSuccessful(List<Department> departments) {
                callBack.loadDepartmentsSuccessful(departments);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        });
    }

    @Override
    public void getAllFromRemote(LoadDepartmentsCallBack callBack) {
        departmentRemoteDataSrc.getAllFromRemote(new LoadDepartmentsCallBack() {
            @Override
            public void loadDepartmentsSuccessful(List<Department> departments) {
                callBack.loadDepartmentsSuccessful(departments);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        });
    }

    @Override
    public void addDepartment(Department department, SuccessfulCallback callback) {
        departmentRemoteDataSrc.addDepartment(department, new SuccessfulCallback() {
            @Override
            public void success(String message) {
                callback.success(message);
            }

            @Override
            public void unSuccessful(String message) {
                callback.unSuccessful(message);
            }
        });
    }

    @Override
    public void getDepartmentById(String departmentId, LoadDepartmentCallback callback) {
        departmentRemoteDataSrc.getDepartmentById(departmentId, new LoadDepartmentCallback() {
            @Override
            public void loadDepartment(Department department) {
                callback.loadDepartment(department);
            }

            @Override
            public void unsuccessful(String message) {
                // get from local DS
                getDepartment(departmentId, callback);
            }
        });
    }

    private void getDepartment(String departmentId, LoadDepartmentCallback callback) {
        departmentLocalDataSrc.getDepartmentById(departmentId, new LoadDepartmentCallback() {
            @Override
            public void loadDepartment(Department department) {
                callback.loadDepartment(department);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void update(Department item) {

    }

    @Override
    public void delete(Department item) {

    }

    @Override
    public void save(Department item) {
        departmentLocalDataSrc.save(item);
    }
}
