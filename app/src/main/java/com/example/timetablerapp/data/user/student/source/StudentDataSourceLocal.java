package com.example.timetablerapp.data.user.student.source;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.student.StudentDataSource;
import com.example.timetablerapp.data.user.student.model.Student;

/**
 * 06/05/19 -bernard
 */
public class StudentDataSourceLocal implements UserDataSource<Student> {
    private static final String TAG = StudentDataSourceLocal.class.getSimpleName();
    private SQLiteDatabase database;

    public StudentDataSourceLocal() {
        database = MainApplication.getWritableDatabase();
    }

    @Override
    public void userSignUp(UserAuthCallback callBack, Student obj, String pass) {

    }

    @Override
    public void authUser(UserAuthCallback callBack, Student obj) {

    }

    @Override
    public void validateUser(String role, String username, String password, UserAuthCallback callback) {

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
        ContentValues values = new ContentValues();

        // Map items or data to contentvalues
        values.put(TimetablerContract.Student.FIRST_NAME, item.getFname());
        values.put(TimetablerContract.Student.LAST_NAME, item.getLname());
        values.put(TimetablerContract.Student.MIDDLE_NAME, item.getMname());
        values.put(TimetablerContract.Student.USERNAME, item.getUsername());
        values.put(TimetablerContract.Student.PASSWORD, item.getPassword());
        values.put(TimetablerContract.Student.STUDENT_ID, item.getStudentId());
        values.put(TimetablerContract.Student.DEPARTMENT_ID, item.getDepartmentId());
        values.put(TimetablerContract.Student.FACULTY_ID, item.getFacultyId());
        values.put(TimetablerContract.Student.IN_SESSION, item.isInSession());
        values.put(TimetablerContract.Student.PROGRAMME_ID, item.getProgrammeId());
        values.put(TimetablerContract.Student.CAMPUS_ID, item.getCampusId());
        values.put(TimetablerContract.Student.ADMISSION_DATE, item.getAdmissionDate());
        values.put(TimetablerContract.Student.YEAR_OF_STUDY, item.getYearOfStudy());

        long countRow = database.insert(TimetablerContract.Student.TABLE_NAME, null, values);

        if (countRow > 0) {
            Log.d(TAG, "save: Successfully recorded..." + countRow + "record(s)");
        }
    }
}
