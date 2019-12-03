package com.example.timetablerapp.data.faculties;

import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.faculties.source.FacultyLocalDS;
import com.example.timetablerapp.data.faculties.source.FacultyRemoteDS;
import com.example.timetablerapp.util.SuccessfulCallback;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class FacultiesRepository implements FacultyDS {

    private static FacultiesRepository INSTANCE = null;
    private FacultyLocalDS facultyLocalDS;
    private FacultyRemoteDS facultyRemoteDS;

    private FacultiesRepository(FacultyLocalDS facultyLocalDS, FacultyRemoteDS facultyRemoteDS) {
        this.facultyLocalDS = facultyLocalDS;
        this.facultyRemoteDS = facultyRemoteDS;
    }

    public static FacultiesRepository newInstance(FacultyLocalDS facultyLocalDS, FacultyRemoteDS facultyRemoteDS) {
        if (INSTANCE == null) {
            INSTANCE = new FacultiesRepository(facultyLocalDS, facultyRemoteDS);
        }
        return INSTANCE;
    }

    @Override
    public void getAllFromRemote(LoadFacultiesCallBack callBack, String campusId) {
        facultyRemoteDS.getAllFromRemote(new LoadFacultiesCallBack() {
            @Override
            public void loadingFacultiesSuccessful(List<Faculty> faculties) {
                callBack.loadingFacultiesSuccessful(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        }, campusId);
    }

    @Override
    public void getAllFromRemote(LoadFacultiesCallBack callBack) {
        facultyRemoteDS.getAllFromRemote(new LoadFacultiesCallBack() {
            @Override
            public void loadingFacultiesSuccessful(List<Faculty> faculties) {
                callBack.loadingFacultiesSuccessful(faculties);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        });
    }

    @Override
    public void addFaculty(Faculty faculty, SuccessFulCallback callback) {
        facultyRemoteDS.addFaculty(faculty, new SuccessFulCallback() {
            @Override
            public void success(String message) {
                callback.success(message);
            }

            @Override
            public void unSuccess(String message) {
                callback.unSuccess(message);
            }
        });
    }

    @Override
    public void getFacultyById(String facultyId, LoadFacultyCallback callback) {
        facultyRemoteDS.getFacultyById(facultyId, new LoadFacultyCallback() {
            @Override
            public void successfullyLoadedFaculty(Faculty faculty) {
                callback.successfullyLoadedFaculty(faculty);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
                getFromLocal(facultyId, callback);
            }
        });
    }

    private void getFromLocal(String facultyId, LoadFacultyCallback callback) {
        facultyLocalDS.getFacultyById(facultyId, new LoadFacultyCallback() {
            @Override
            public void successfullyLoadedFaculty(Faculty faculty) {
                callback.successfullyLoadedFaculty(faculty);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void update(Faculty item, SuccessfulCallback callback) {
        facultyRemoteDS.update(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                updateLocalDS(item,callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
                updateLocalDS(item, callback);
            }
        });
    }

    private void updateLocalDS(Faculty item, SuccessfulCallback callback) {
        facultyLocalDS.update(item, callback);
    }

    @Override
    public void delete(Faculty item, SuccessfulCallback callback) {
        facultyRemoteDS.delete(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                deleteLocally(item, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    private void deleteLocally(Faculty item, SuccessfulCallback callback) {
        facultyLocalDS.delete(item, callback);
    }

    @Override
    public void save(Faculty item, SuccessfulCallback callback) {
        facultyLocalDS.save(item, callback);
    }
}
