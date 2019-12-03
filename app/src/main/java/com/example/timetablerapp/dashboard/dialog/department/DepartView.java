package com.example.timetablerapp.dashboard.dialog.department;

import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;

import java.util.List;

/**
 * 08/09/19 -bernard
 */
public interface DepartView {
    void setDepartments(List<Department> departments);

    void showMessage(String message);

    void setFaculties(List<Faculty> faculties);

    void setCampusList(List<Campus> campuses);

    void setFaculty(Faculty faculty);
}
