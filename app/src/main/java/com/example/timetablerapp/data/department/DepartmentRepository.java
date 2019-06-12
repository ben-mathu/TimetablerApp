package com.example.timetablerapp.data.department;

import android.content.ContentValues;
import android.os.Bundle;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.department.source.DepartmentLocalDataSrc;
import com.example.timetablerapp.data.department.source.DepartmentRemoteDataSrc;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.Faculty;

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
    public void getAllFromRemote(LoadDepartmentsCallBack callBack, String id) {
        departmentRemoteDataSrc.getAllFromRemote(new LoadDepartmentsCallBack() {
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
