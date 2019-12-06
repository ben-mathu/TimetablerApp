package com.example.timetablerapp.dashboard.dialog.faculty;

import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.faculties.model.Faculty;

import java.util.List;

/**
 * 07/09/19 -bernard
 */
public interface FacultyView {
    void setCampusList(List<Campus> campuses);

    void showMessage(String message);

    void setFaculties(List<Faculty> faculties);
}
