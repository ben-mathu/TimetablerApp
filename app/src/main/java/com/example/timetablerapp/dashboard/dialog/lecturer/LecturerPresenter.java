package com.example.timetablerapp.dashboard.dialog.lecturer;

import com.example.timetablerapp.Presenter;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.lecturer.model.LecResponse;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;

import java.util.List;

/**
 * 17/11/19
 *
 * @author <a href="https://github.com/ben-mathu">bernard</a>
 */
public class LecturerPresenter extends Presenter<LecturerView> {
    private LecturerView view;
    private LecturerRepo lecturerRepo;

    public LecturerPresenter(LecturerRepo lecturerRepo) {
        this.lecturerRepo = lecturerRepo;
    }

    public void getLecturers() {
        lecturerRepo.getLecturers(new LecturerDS.LecturersLoadedCallback() {
            @Override
            public void successfullyLoaded(List<Lecturer> list) {
                view.setLecturers(list);
            }

            @Override
            public void unsuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    public void createLecturer(String email, String fname, String mname, String lname) {
        lecturerRepo.createLecturer(email, fname, mname, lname, new LecturerDS.CreatingLecturerCallback() {
            @Override
            public void successfullyCreated(LecResponse response) {
                view.showMessage(response.getMessage());
                view.sendEmail(response);
            }

            @Override
            public void unSuccessful(String message) {
                view.showMessage(message);
            }
        });
    }

    @Override
    public void setView(LecturerView item) {
        this.view = item;
    }
}
