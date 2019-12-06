package com.example.timetablerapp.data.programmes;

import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.programmes.source.ProgLocalDS;
import com.example.timetablerapp.data.programmes.source.ProgRemoteDS;
import com.example.timetablerapp.util.SuccessfulCallback;

import java.util.List;

/**
 * 08/05/19 -bernard
 */
public class ProgrammesRepository implements ProgrammeDS {

    private static ProgrammesRepository INSTANCE = null;
    private ProgLocalDS progLocalDS;
    private ProgRemoteDS progRemoteDS;

    public ProgrammesRepository(ProgLocalDS progLocalDs, ProgRemoteDS progRemoteDS) {
        this.progLocalDS = progLocalDs;
        this.progRemoteDS = progRemoteDS;
    }

    public static ProgrammesRepository newInstance(ProgLocalDS progLocalDS, ProgRemoteDS progRemoteDS) {
        if (INSTANCE == null) {
            INSTANCE = new ProgrammesRepository(progLocalDS, progRemoteDS);
        }
        return INSTANCE;
    }

    @Override
    public void getAllFromRemote(LoadProgrammesCallBack callBack, String departmentId) {
        progRemoteDS.getAllFromRemote(new LoadProgrammesCallBack() {
            @Override
            public void loadProgrammesSuccessfully(List<Programme> programmes) {
                callBack.loadProgrammesSuccessfully(programmes);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        }, departmentId);
    }

    @Override
    public void getFromLocalDb(LoadProgrammesCallBack callBack) {

    }

    @Override
    public void getAllProgrammes(LoadProgrammesCallBack callBack) {
        progRemoteDS.getAllProgrammes(new LoadProgrammesCallBack() {
            @Override
            public void loadProgrammesSuccessfully(List<Programme> programmes) {
                callBack.loadProgrammesSuccessfully(programmes);
            }

            @Override
            public void dataNotAvailable(String message) {
                callBack.dataNotAvailable(message);
            }
        });
    }

    @Override
    public void addProgramme(Programme programme, SuccessfullySavedCallback callback) {
        progRemoteDS.addProgramme(programme, new SuccessfullySavedCallback() {
            @Override
            public void success(String message) {
                callback.success(message);
            }

            @Override
            public void unSuccessful(String message) {
                callback.unSuccessful(message);
            }
        });
    }

    @Override
    public void update(Programme item, SuccessfulCallback callback) {
        progRemoteDS.update(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                updateLocalDS(item, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    private void updateLocalDS(Programme item, SuccessfulCallback callback) {
        progLocalDS.update(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void delete(Programme item, SuccessfulCallback callback) {
        progRemoteDS.delete(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
                deleteFromLocalDS(item, callback);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    private void deleteFromLocalDS(Programme item, SuccessfulCallback callback) {
        progLocalDS.delete(item, new SuccessfulCallback() {
            @Override
            public void successful(String message) {
                callback.successful(message);
            }

            @Override
            public void unsuccessful(String message) {
                callback.unsuccessful(message);
            }
        });
    }

    @Override
    public void save(Programme item, SuccessfulCallback callback) {
        progLocalDS.save(item, callback);
    }
}
