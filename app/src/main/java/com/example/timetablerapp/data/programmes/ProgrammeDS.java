package com.example.timetablerapp.data.programmes;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.programmes.model.Programme;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public interface ProgrammeDS extends DataSource<Programme> {
    void getAllFromRemote(LoadProgrammesCallBack callBack, String name);

    void getFromLocalDb(LoadProgrammesCallBack callBack);

    void getAllProgrammes(LoadProgrammesCallBack callBack);

    void addProgramme(Programme programme, SuccessfullySavedCallback callback);

    interface LoadProgrammesCallBack {
        void loadProgrammesSuccessfully(List<Programme> programmes);
        void dataNotAvailable(String message);
    }

    interface SuccessfullySavedCallback {
        void success(String message);
        void unSuccessful(String message);
    }
}
