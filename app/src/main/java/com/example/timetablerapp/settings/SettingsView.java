package com.example.timetablerapp.settings;

import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.lecturer.model.LecturerResponse;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.model.StudentResponse;

/**
 * 20/11/19
 *
 * define view methods
 * @author <a href="https://github.com/ben-mathu">bernard</a>
 */
public interface SettingsView {
    void showMessage(String message);

    void setAdminDetails(Admin admin);

    void setStudentDetails(StudentResponse student);

    void setLecturerDetails(LecturerResponse obj);
}
