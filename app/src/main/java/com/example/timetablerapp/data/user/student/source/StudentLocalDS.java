package com.example.timetablerapp.data.user.student.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.util.SuccessfulCallback;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.db.TimetablerContract;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.student.model.Student;

import static com.example.timetablerapp.data.db.TimetablerContract.Student.ADMISSION_DATE;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.CAMPUS_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.DEPARTMENT_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.EMAIL;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.FACULTY_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.FIRST_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.IN_SESSION;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.LAST_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.MIDDLE_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.PASSWORD;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.PROGRAMME_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.STUDENT_ID;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.TABLE_NAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.USERNAME;
import static com.example.timetablerapp.data.db.TimetablerContract.Student.YEAR_OF_STUDY;

/**
 * 06/05/19 -bernard
 */
public class StudentLocalDS implements UserDataSource<Student> {
    private static final String TAG = StudentLocalDS.class.getSimpleName();
    private SQLiteDatabase database;

    public StudentLocalDS() {
        database = MainApplication.getWritableDatabase();
    }

    @Override
    public void userSignUp(SuccessfulCallback callBack, Student obj, String pass) {

    }

    @Override
    public void authUser(SuccessfulCallback callBack, Student obj) {

    }

    @Override
    public void updateUsername(String name, String userId, String role, SuccessfulCallback callback) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.USERNAME, name);

        long countRow = database.update(TimetablerContract.Student.TABLE_NAME,
                contentValues,
                Constants.STUDENT_ID + "=?",
                new String[]{userId});

        if (countRow > 0)
            callback.successful("Successfully updated username to " + name);
        else callback.unsuccessful("Username " + name + " was not updated.");
    }

    @Override
    public void validateUser(String role, String username, String password, String userId, SuccessfulCallback callback) {

    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {

    }

    @Override
    public void getDetails(String userId, String userRole, LoadUserDetailsCallback callback) {
        String[] arrCol = new String[]{
                STUDENT_ID,
                FIRST_NAME,
                LAST_NAME,
                MIDDLE_NAME,
                USERNAME,
                PASSWORD,
                YEAR_OF_STUDY,
                PROGRAMME_ID,
                DEPARTMENT_ID,
                CAMPUS_ID,
                FACULTY_ID,
                ADMISSION_DATE,
                IN_SESSION,
        };

        Cursor cursor = database.query(TABLE_NAME,
                arrCol,
                STUDENT_ID + "=?",
                new String[]{userId}, null, null, null);

        if (cursor.moveToFirst()) {
            Student student = new Student();
            student.setFname(cursor.getString(1));
            student.setMname(cursor.getString(2));
            student.setLname(cursor.getString(3));
            student.setUsername(cursor.getString(4));
            student.setPassword(cursor.getString(5));
            student.setYearOfStudy(String.valueOf(cursor.getInt(6)));
            student.setProgrammeId(cursor.getString(7));
            student.setDepartmentId(cursor.getString(8));
            student.setCampusId(cursor.getString(9));
            student.setFacultyId(cursor.getString(10));
            student.setAdmissionDate(cursor.getString(11));
            student.setInSession(cursor.getInt(12) == 1);
            student.setEmail(cursor.getString(13));
            callback.loadData(student);
        }

        if (cursor.isNull(0)) {
            callback.unsuccessful("Could not get your details, please logout, then log back in.");
        }

        cursor.close();
    }

    @Override
    public void changePassword(String userId, String role, LecturerDS.SuccessCallback callback, String hashedNewPasswd) {
        ContentValues values = new ContentValues();
        values.put(PASSWORD, hashedNewPasswd);

        int count = database.update(TABLE_NAME, values, STUDENT_ID + "=?", new String[]{userId});

        Log.d(TAG, "changePassword: table update output: " + count);
    }

    @Override
    public void updateUserDetails(Student obj, SuccessfulCallback callback) {
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, obj.getFname());
        values.put(LAST_NAME, obj.getLname());
        values.put(MIDDLE_NAME, obj.getMname());
        values.put(CAMPUS_ID, obj.getCampusId());
        values.put(IN_SESSION, obj.isInSession());
        values.put(EMAIL, obj.getEmail());

        int count = database.update(TABLE_NAME, values, STUDENT_ID + "=?", new String[]{obj.getStudentId()});

        Log.d(TAG, "updateUserDetails: records changed: " + count);
    }

    @Override
    public void deleteAccount(String userRole, String userId, SuccessfulCallback callback) {
        int count = database.delete(TABLE_NAME, STUDENT_ID + "=?", new String[]{userId});

        Log.d(TAG, "deleteAccount: record count: " + count);

        callback.successful(Constants.DELETED_ACCOUNT_MESSAGE);
    }

    @Override
    public void fetchSettingsFromRemote(FetchSettingsCallback callback) {

    }

    @Override
    public void update(Student item, SuccessfulCallback callback) {

    }

    @Override
    public void delete(Student item, SuccessfulCallback callback) {

    }

    @Override
    public void save(Student item, SuccessfulCallback callback) {
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
