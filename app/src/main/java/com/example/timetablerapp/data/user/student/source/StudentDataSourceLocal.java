package com.example.timetablerapp.data.user.student.source;

import android.database.sqlite.SQLiteDatabase;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.user.student.StudentDataSource;
import com.example.timetablerapp.data.user.student.model.Student;

/**
 * 06/05/19 -bernard
 */
public class StudentDataSourceLocal implements StudentDataSource {
    private SQLiteDatabase database;

    public StudentDataSourceLocal() {
        database = MainApplication.getWritableDatabase();
    }

    @Override
    public void validateUser(String username, String password) {

    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {

    }

    @Override
    public void update(Student item) {

    }

    @Override
    public void delete(Student item) {

    }

    @Override
    public void save(Student item) {

    }
}
