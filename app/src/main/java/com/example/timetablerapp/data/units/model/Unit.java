package com.example.timetablerapp.data.units.model;

import com.example.timetablerapp.data.Constants;
import com.google.gson.annotations.SerializedName;

/**
 * 19/05/19 -bernard
 */
public class Unit {
    @SerializedName(Constants.UNIT_ID)
    private String id;
    @SerializedName(Constants.UNIT_NAME)
    private String unitName;
    @SerializedName(Constants.PROGRAMME_ID)
    private String programmeId;
    @SerializedName(Constants.FACULTY_ID)
    private String facultyId;
    @SerializedName(Constants.DEPARTMENT_ID)
    private String departmentId;
    @SerializedName(Constants.IS_PRACTICAL)
    private boolean isPractical = false;
    @SerializedName(Constants.COMMON)
    private boolean isCommon;
    @SerializedName(Constants.IS_REMOVED)
    private boolean isRemoved;

    private boolean isSelected;

    public Unit(String id, String unitName, String programmeId, String facultyId, String departmentId, boolean isPractical, boolean isCommon, boolean isSelected) {
        this.id = id;
        this.unitName = unitName;
        this.programmeId = programmeId;
        this.facultyId = facultyId;
        this.departmentId = departmentId;
        this.isPractical = isPractical;
        this.isCommon = isCommon;
        this.isSelected = isSelected;
        isRemoved = false;
    }

    public Unit() {
        isRemoved = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String    id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public boolean isPractical() {
        return isPractical;
    }

    public void setPractical(boolean practical) {
        isPractical = practical;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isCommon() {
        return isCommon;
    }

    public void setCommon(boolean common) {
        isCommon = common;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }
}
