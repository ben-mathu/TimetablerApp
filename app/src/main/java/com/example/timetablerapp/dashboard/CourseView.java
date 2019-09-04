package com.example.timetablerapp.dashboard;

import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.units.model.Unit;

import java.util.List;

/**
 * 03/09/19 -bernard
 */
public interface CourseView {
    void setUnits(List<Unit> units);

    void showMessage(String message);

    void setFaculties(List<Faculty> faculties);

    void setDepartments(List<Department> departments);

    void setProgrammes(List<Programme> programmes);
}
