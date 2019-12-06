package com.example.timetablerapp.dashboard.dialog.lecturer;

import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
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

    void setFaculty(Faculty faculty);

    void setDepartment(Department department);

    void setFaculties(List<Faculty> faculties);

    void setDepartments(List<Department> departments);
}
