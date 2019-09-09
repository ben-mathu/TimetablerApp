package com.example.timetablerapp.data.campuses;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.campuses.model.Campus;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public interface CampusesDS extends DataSource<Campus> {
    void getAllFromRemote(LoadCampusesCallBack callBack);

    void addCampus(Campus campus, SuccessfullySavedCallback callback);

    void updateCampus(Campus campus, SuccessfullySavedCallback callback);

    void deleteRemote(Campus campus, SuccessfullySavedCallback callback);

    interface LoadCampusesCallBack {
        void loadCampusesSuccessful(List<Campus> campuses);
        void dataNotAvailable(String message);
    }

    interface SuccessfullySavedCallback {
        void success(String message);
        void unSuccess(String message);
    }
}
