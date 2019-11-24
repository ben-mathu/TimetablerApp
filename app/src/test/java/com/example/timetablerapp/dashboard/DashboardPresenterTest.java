package com.example.timetablerapp.dashboard;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.dashboard.dialog.lecturer.LecturerView;
import com.example.timetablerapp.data.units.UnitsRepo;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

/**
 * 02/09/19 -bernard
 */
public class DashboardPresenterTest {

    @Mock
    private UnitsRepo unitsRepo;
    @Mock
    private DashboardView view;
    private DashboardPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new DashboardPresenter(view, unitsRepo);
        unitsRepo = MainApplication.getUnitRepo();
    }

    @Test
    public void getLecturers_ListIsEmpty_ShowMessageError() {
        final LecturerDS.LecturersLoadedCallback callback = mock(LecturerDS.LecturersLoadedCallback.class);
        presenter.getUnitsByStudentId("jsjrw22");

        verify(callback, only()).unsuccessful(ArgumentMatchers.any());
    }
}