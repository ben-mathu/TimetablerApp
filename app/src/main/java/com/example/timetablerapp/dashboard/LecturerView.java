package com.example.timetablerapp.dashboard;

import com.example.timetablerapp.data.user.lecturer.model.LecRequest;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

import java.util.List;

/**
 * 02/09/19 -bernard
 */
public interface LecturerView {
    void setLecturers(List<Lecturer> list);
    void showMessage(String message);

    void sendEmail(LecResponse response);
}
