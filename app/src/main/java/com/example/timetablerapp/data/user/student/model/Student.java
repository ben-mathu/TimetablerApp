package com.example.timetablerapp.data.user.student.model;

import com.google.gson.annotations.SerializedName;

/**
 * 07/05/19 -bernard
 */
public class Student {
    @SerializedName("student_id")
    private String studentId;
    @SerializedName("f_name")
    private String fname;
    @SerializedName("l_name")
    private String lname;
    @SerializedName("m_name")
    private String mname;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("department_id")
    private String departmentId;
    @SerializedName("programme_id")
    private String programmeId;
    @SerializedName("campus_id")
    private String campusId;
    @SerializedName("faculty_id")
    private String facultyId;
    @SerializedName("year_of_study")
    private String yearOfStudy;
    @SerializedName("admission_date")
    private String admissionDate;
    @SerializedName("in_session")
    private boolean inSession = false;
    @SerializedName("email")
    private String email;

    public Student(String studentId,
                   String fname, String lname,
                   String mname, String username,
                   String password, String departmentId,
                   String programmeId, String campusId,
                   String facultyId, String yearOfStudy,
                   String admissionDate, boolean inSession, String email) {
        this.studentId = studentId;
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.username = username;
        this.password = password;
        this.departmentId = departmentId;
        this.programmeId = programmeId;
        this.campusId = campusId;
        this.facultyId = facultyId;
        this.yearOfStudy = yearOfStudy;
        this.admissionDate = admissionDate;
        this.inSession = inSession;
        this.email = email;
    }

    public Student() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public String getCampusId() {
        return campusId;
    }

    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public boolean isInSession() {
        return inSession;
    }

    public void setInSession(boolean inSession) {
        this.inSession = inSession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
