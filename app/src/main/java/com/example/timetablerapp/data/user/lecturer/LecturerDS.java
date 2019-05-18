package com.example.timetablerapp.data.user.lecturer;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

/**
 * 08/05/19 -bernard
 */
public interface LecturerDS extends DataSource<LecturerDS.LecturerIsAuthCallBack, Lecturer> {

    void userSignUp(LecturerIsAuthCallBack callBack, Lecturer lecturer);
    void authUser(LecturerIsAuthCallBack callBack, Lecturer lecturer);

    interface LecturerIsAuthCallBack {
        void userIsAuthSuccessfull(String message);
        void authNotSuccessful(String message);
    }
}
