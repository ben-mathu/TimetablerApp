package com.example.timetablerapp.signup;

import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.student.model.Student;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public interface SignUpContract {
    interface View {

        void showFaculties(List<Faculty> faculties);

        void showCampuses(List<Campus> campuses);

        void showMessages(String message);

        void showDepartments(List<Department> departments);

        void showProgrammes(List<Programme> programmes);

        void startLoginActiity();
    }

    interface Presenter {
        void getDepartments(String facultyName);
        void getProgrammes(String departmentName);
        void getCampuses();
        void getFaculties(String campusName);

        void registerUser(Lecturer lec);

        void registerUser(Student student, Department department, Faculty faculty, Campus campus, Programme programme);

        void registerUser(Admin admin, String password);

        void getFaculties();
    }
}
