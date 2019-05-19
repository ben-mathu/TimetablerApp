package com.example.timetablerapp.data.user;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.student.model.Student;

/**
 * 19/05/19 -bernard
 */
public interface UserDataSource<T> extends DataSource<UserDataSource.UserAuthCallback, T> {
    void userSignUp(UserAuthCallback callBack, T obj);
    void authUser(UserDataSource.UserAuthCallback callBack, T obj);

    interface UserAuthCallback {
        void userIsAuthSuccessfull(String message);
        void authNotSuccessful(String message);
    }

    void validateUser(String role, String username, String password, UserAuthCallback callback);

    void sendUserRole(GetSaltCallBack callBack, String role);

    interface GetSaltCallBack {
        void successful(String salt);
        void unsuccessful(String message);

    }
}
