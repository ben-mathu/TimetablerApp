package com.example.timetablerapp.data.department;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.FacultyDS;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public interface DepartmentDS extends DataSource<DepartmentDS.LoadDepartmentsCallBack, Department> {
    void getAllFromRemote(LoadDepartmentsCallBack callBack, String name);

    interface LoadDepartmentsCallBack {
        void loadDepartmentsSuccessful(List<Department> departments);
        void dataNotAvailable(String message);
    }
}
