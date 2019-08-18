package com.example.timetablerapp.data.faculties;

import com.example.timetablerapp.data.DataSource;
import com.example.timetablerapp.data.faculties.model.Faculty;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public interface FacultyDS extends DataSource<Faculty> {
    void getAllFromRemote(LoadFacultiesCallBack callBack, String name);
    void getAllFromRemote(LoadFacultiesCallBack callBack);


    interface LoadFacultiesCallBack {
        void loadingFacultiesSuccessful(List<Faculty> faculties);
        void dataNotAvailable(String message);
    }
}
