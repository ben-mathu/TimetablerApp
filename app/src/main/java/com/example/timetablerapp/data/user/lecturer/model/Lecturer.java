package com.example.timetablerapp.data.user.lecturer.model;

import com.example.timetablerapp.data.Constants;
import com.google.gson.annotations.SerializedName;

/**
 * 08/05/19 -bernard
 */
public class Lecturer {
    @SerializedName("lecturer_id")
    private String id;
    @SerializedName("f_name")
    private String firstName;
    @SerializedName("l_name")
    private String lastName;
    @SerializedName("m_name")
    private String middleName;
    @SerializedName("username")
    private String username;
    @SerializedName(Constants.EMAIL)
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("faculty_id")
    private String facultyId;
    @SerializedName("department_id")
    private String departmentId;
    @SerializedName("in_session")
    private boolean inSesson;

    public Lecturer(String id, String firstName,
                    String lastName, String middleName,
                    String username, String email, String password,
                    String departmentId, String facultyId,
                    boolean inSesson) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.departmentId = departmentId;
        this.facultyId = facultyId;
        this.inSesson = inSesson;
    }

    public Lecturer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public boolean isInSesson() {
        return inSesson;
    }

    public void setInSesson(boolean inSesson) {
        this.inSesson = inSesson;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
