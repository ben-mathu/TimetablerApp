package com.example.timetablerapp.data.user.student.model;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.department.model.Department;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.google.gson.annotations.SerializedName;

/**
 * 21/05/19 -bernard
 */
public class StudentResponse {
    @SerializedName("student")
    private Student student;
    @SerializedName(Constants.TABLE_DEPARTMENTS)
    private Department department;
    @SerializedName(Constants.TABLE_CAMPUS)
    private Campus campus;
    @SerializedName(Constants.TABLE_FACULTIES)
    private Faculty faculty;
    @SerializedName(Constants.TABLE_PROGRAMMES)
    private Programme programme;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }
}
