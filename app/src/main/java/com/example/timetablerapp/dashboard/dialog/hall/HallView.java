package com.example.timetablerapp.dashboard.dialog.hall;

import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.hall.model.Hall;

import java.util.List;

/**
 * 03/12/19
 *
 * @author bernard
 */
public interface HallView {
    void setFaculties(List<Faculty> list);

    void showMessage(String message);

    void setHalls(List<Hall> halls);

    void setFaculty(Faculty faculty);
}
