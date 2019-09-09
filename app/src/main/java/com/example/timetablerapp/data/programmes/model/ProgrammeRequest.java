package com.example.timetablerapp.data.programmes.model;

import com.google.gson.annotations.SerializedName;

/**
 * 09/09/19 -bernard
 */
public class ProgrammeRequest {
    @SerializedName("programme")
    private Programme programme;

    public ProgrammeRequest(Programme programme) {
        this.programme = programme;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }
}
