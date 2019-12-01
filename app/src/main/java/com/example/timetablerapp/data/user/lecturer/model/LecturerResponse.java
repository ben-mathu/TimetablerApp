package com.example.timetablerapp.data.user.lecturer.model;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.google.gson.annotations.SerializedName;

/**
 * 08/05/19 -bernard
 */
public class LecturerResponse {
    @SerializedName("lecturer")
    private Lecturer lecturer;
    @SerializedName(Constants.TABLE_FACULTIES)
    private Faculty faculty;
    @SerializedName(Constants.TABLE_DEPARTMENTS)
    private Department department;
    @SerializedName(Constants.TABLE_CAMPUS)
    private Campus campus;

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }
}
