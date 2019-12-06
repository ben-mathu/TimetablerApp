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
import com.example.timetablerapp.util.SuccessfulCallback;

import java.util.ArrayList;
import java.util.List;

import static com.example.timetablerapp.data.db.TimetablerContract.Department.DEPARTMENT_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Department.DEPARTMENT_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Department.FACULTY_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Department.TABLE_NAME;

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

        cursor.close();
    }

    @Override
    public void getAllFromRemote(LoadDepartmentsCallBack callBack) {

    }

    @Override
    public void addDepartment(Department department, SuccessfulCallback successfulCallback) {

    }

    @Override
    public void getDepartmentById(String departmentId, LoadDepartmentCallback callback) {
        String[] arrCol = new String[]{
                TimetablerContract.Department.DEPARTMENT_ID,
                TimetablerContract.Department.DEPARTMENT_NAME,
                TimetablerContract.Department.FACULTY_ID};
        Cursor cursor = database.query(TimetablerContract.Department.TABLE_NAME, arrCol, null, null, null,null, null);

        try {
            Department department = new Department();
            if (cursor.moveToFirst()) {
                department.setDepartmentId(cursor.getString(0));
                department.setDepartmentName(cursor.getString(1));
                department.setFacultyId(cursor.getString(2));
            }

            callback.loadDepartment(department);
        } catch (NullPointerException e) {
            callback.unsuccessful("There are no data entry.");
        } finally {
            cursor.close();
        }
    }

    @Override
    public void update(Department item, SuccessfulCallback callback) {
        ContentValues values = new ContentValues();

        values.put(DEPARTMENT_NAME, item.getDepartmentName());
        values.put(FACULTY_ID, item.getFacultyId());

        long count = database.update(TABLE_NAME, values, DEPARTMENT_ID + "=?", new String[]{item.getDepartmentId()});

        if (count > 0)
            Log.d(TAG, "update: row count: " + count);
    }

    @Override
    public void delete(Department item, SuccessfulCallback callback) {
        database.delete(TABLE_NAME, DEPARTMENT_ID + "=?",
                new String[]{item.getDepartmentId()});
    }

    @Override
    public void save(Department item, SuccessfulCallback callback) {
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
