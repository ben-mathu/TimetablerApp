package com.example.timetablerapp.data.settings.model;

import com.google.gson.annotations.SerializedName;

/**
 * 13/06/19 -bernard
 */
public class DeadlineSettings {
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("deadline")
    private String deadline;
    @SerializedName("activity")
    private boolean isActive = false;

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
