package com.example.timetablerapp.data.department;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.FacultyDS;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public interface DepartmentDS extends DataSource<Department> {
    void getDepsByIdFromRemote(LoadDepartmentsCallBack callBack, String name);

    void getFromLocalDb(LoadDepartmentsCallBack callBack);

    void getAllFromRemote(LoadDepartmentsCallBack callBack);

    void addDepartment(Department department, SuccessfulCallback successfulCallback);

    void getDepartmentById(String departmentId, LoadDepartmentCallback callback);

    interface LoadDepartmentsCallBack {
        void loadDepartmentsSuccessful(List<Department> departments);
        void dataNotAvailable(String message);
    }

    interface SuccessfulCallback {
        void success(String message);
        void unSuccessful(String message);
    }

    interface LoadDepartmentCallback {
        void loadDepartment(Department department);
        void unsuccessful(String message);
    }
}
