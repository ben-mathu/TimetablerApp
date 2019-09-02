package com.example.timetablerapp.dashboard;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import okhttp3.Response;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

/**
 * 02/09/19 -bernard
 */
public class DashboardPresenterTest {

    @Mock
    private LecturerRepo lecturerRepo;
    @Mock
    private LecturerView view;
    private DashboardPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new DashboardPresenter(view, lecturerRepo);
        lecturerRepo = MainApplication.getLecturerRepo();
    }

    @Test
    public void getLecturers_ListIsEmpty_ShowMessageError() {
        final LecturerDS.LecturersLoadedCallback callback = mock(LecturerDS.LecturersLoadedCallback.class);
        presenter.getLecturers();

        verify(callback, only()).unsuccessful(ArgumentMatchers.any());
    }
}