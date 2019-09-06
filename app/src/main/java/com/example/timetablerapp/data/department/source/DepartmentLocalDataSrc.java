package com.example.timetablerapp.data.department.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.department.DepartmentDS;
import com.example.timetablerapp.data.department.model.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class DepartmentLocalDataSrc implements DepartmentDS {
    private static final String TAG = DepartmentLocalDataSrc.class.getSimpleName();
    private SQLiteDatabase database;

    public DepartmentLocalDataSrc() {
        this.database = MainApplication.getWritableDatabase();
    }

    @Override
    public void getDepsByIdFromRemote(LoadDepartmentsCallBack callBack, String name) {

    }

    @Override
    public void getFromLocalDb(LoadDepartmentsCallBack callBack) {
        Cursor cursor = database.query(Constants.TABLE_DEPARTMENTS, new String[]{
                TimetablerContract.Department.DEPARTMENT_ID,
                TimetablerContract.Department.DEPARTMENT_NAME,
                TimetablerContract.Department.FACULTY_ID}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            Department department = new Department();

            department.setDepartmentId(cursor.getString(0));
            department.setDepartmentName(cursor.getString(1));
            department.setFacultyId(cursor.getString(2));

            List<Department> departments = new ArrayList<>();
            departments.add(department);

            if (!departments.isEmpty()) {
                callBack.loadDepartmentsSuccessful(departments);
            } else {
                callBack.dataNotAvailable("Department not found.");
            }
        }
    }

    @Override
    public void getAllFromRemote(LoadDepartmentsCallBack callBack) {

    }

    @Override
    public void update(Department item) {

    }

    @Override
    public void delete(Department item) {

    }

    @Override
    public void save(Department item) {
        ContentValues values = new ContentValues();

        values.put(TimetablerContract.Department.DEPARTMENT_ID, item.getDepartmentId());
        values.put(TimetablerContract.Department.DEPARTMENT_NAME, item.getDepartmentName());
        values.put(TimetablerContract.Department.FACULTY_ID, item.getFacultyId());

        long countRow = database.insert(TimetablerContract.Department.TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: # of columns affected: " + countRow);
        }
    }
}
