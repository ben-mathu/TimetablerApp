package com.example.timetablerapp.dashboard.dialog.campus;

import com.example.timetablerapp.data.campuses.model.Campus;

import java.util.List;

/**
 * 06/09/19 -bernard
 */
public interface CampusView {
    void setList(List<Campus> campuses);

    void showMessage(String message);
}
