package com.example.timetablerapp.data.user.student.model;

import com.google.gson.annotations.SerializedName;

/**
 * 21/05/19 -bernard
 */
public class StudentRequest {
    @SerializedName("student")
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
