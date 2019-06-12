package com.example.timetablerapp.data.programmes;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.programmes.model.Programme;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public interface ProgrammeDS extends DataSource<ProgrammeDS.LoadProgrammesCallBack, Programme, Void> {
    void getAllFromRemote(LoadProgrammesCallBack callBack, String name);

    interface LoadProgrammesCallBack {
        void loadProgrammesSuccessfully(List<Programme> programmes);
        void dataNotAvailable(String message);
    }
}
