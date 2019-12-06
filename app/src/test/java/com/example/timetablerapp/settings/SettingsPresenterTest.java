package com.example.timetablerapp.settings;

import com.example.timetablerapp.data.campuses.CampusesRepository;
import com.example.timetablerapp.data.user.admin.AdminRepo;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.student.StudentRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * 24/11/19
 *
 * @author bernard
 */
@RunWith(MockitoJUnitRunner.class)
public class SettingsPresenterTest {
    @Mock
    private SettingsView view;
    @Mock
    private LecturerRepo lecturerRepo;
    @Mock
    private StudentRepository studentRepo;
    @Mock
    private AdminRepo adminRepo;
    @Mock
    private CampusesRepository campusRepo;

    private SettingsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new SettingsPresenter(view, adminRepo, lecturerRepo, studentRepo, campusRepo);
    }

    @Test
    public void updateUser_InvalidFullName_ShowError() {
        presenter.updateUser("938485", "mathu@gmail.com", "admin", "98ioweir", true);

        verify(view).showMessage("Your name is invalid.");
    }

    @Test
    public void updateUser_InvalidEmail_ShowError() {
        presenter.updateUser("Benard Mathu", "mathu", "admin", "98ioweir", true);

        verify(view).showMessage("Your email is invalid.");
    }
}