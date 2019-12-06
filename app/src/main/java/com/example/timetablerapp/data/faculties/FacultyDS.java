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

    void addFaculty(Faculty faculty, SuccessFulCallback callback);

    void getFacultyById(String facultyId, LoadFacultyCallback loadFacultyCallBack);


    interface LoadFacultiesCallBack {
        void loadingFacultiesSuccessful(List<Faculty> faculties);
        void dataNotAvailable(String message);
    }

    interface SuccessFulCallback {
        void success(String message);
        void unSuccess(String message);
    }

    interface LoadFacultyCallback {
        void successfullyLoadedFaculty(Faculty faculty);
        void unsuccessful(String message);
    }
}
