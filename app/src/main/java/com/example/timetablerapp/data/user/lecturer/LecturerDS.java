package com.example.timetablerapp.data.user.lecturer;

import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public interface LecturerDS {
    void deleteLecturer(Lecturer lecturer, SuccessCallback callback);

    interface LecturersLoadedCallback {
        void successfullyLoaded(List<Lecturer> list);
        void unsuccessful(String message);
    }

    interface CreatingLecturerCallback {
        void successfullyCreated(LecResponse res);
        void unSuccessful(String message);
    }

    interface SuccessCallback {
        void success(String message);
        void unsuccessful(String message);
    }
}
