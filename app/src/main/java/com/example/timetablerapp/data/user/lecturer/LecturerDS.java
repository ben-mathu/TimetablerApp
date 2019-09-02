package com.example.timetablerapp.data.user.lecturer;

import com.example.timetablerapp.data.user.lecturer.model.LecRequest;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public interface LecturerDS {
    interface LecturersLoadedCallback {
        void successfullyLoaded(List<Lecturer> list);
        void unsuccessful(String message);
    }

    interface CreatingLecturerCallback {
        void successfullyCreated(LecResponse res);
        void unSuccessful(String message);
    }
}
