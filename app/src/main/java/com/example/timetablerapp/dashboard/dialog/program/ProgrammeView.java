package com.example.timetablerapp.dashboard.dialog.program;

import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;

import java.util.List;

/**
 * 09/09/19 -bernard
 */
public interface ProgrammeView {
    void setProgrammes(List<Programme> programmes);

    void showMessage(String message);

    void setFaculties(List<Faculty> faculties);

    void setDepartments(List<Department> departments);

    void setCampuses(List<Campus> campuses);
}
