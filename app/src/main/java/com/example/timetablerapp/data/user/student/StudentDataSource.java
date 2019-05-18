package com.example.timetablerapp.data.user.student;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.user.student.model.Student;

/**
 * 06/05/19 -bernard
 */
public interface StudentDataSource extends DataSource<StudentDataSource.GetSaltCallBack, Student> {
    void validateUser(String username, String password);

    void sendUserRole(GetSaltCallBack callBack, String role);

    interface GetSaltCallBack {
        void successful(String salt);
        void unsuccessful(String message);

    }
}
