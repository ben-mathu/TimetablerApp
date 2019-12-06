package com.example.timetablerapp.login;

import com.example.timetablerapp.R;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerRepo;
import com.example.timetablerapp.data.user.student.StudentRepository;
import com.example.timetablerapp.util.SuccessfulCallback;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 07/05/19 -bernard
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    private LoginView view;
    @Mock
    private StudentRepository userRepository;
    @Mock
    private LecturerRepo lecturerRepo;
    @Mock
    private SuccessfulCallback callback;
    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(userRepository, lecturerRepo, view);
    }

    @Test
    public void login_UsernameIsEmpty_ShowUsernameError() {
        when(view.getUsername()).thenReturn("");
        presenter.login();

        verify(view).showUsernameError(R.string.username_error);
    }

    @Test
    public void login_PasswordIsEmpty_ShowPasswordError() {
        when(view.getUsername()).thenReturn("Jones");
        when(view.getPassword()).thenReturn("");
        presenter.login();

        verify(view).showPasswordError(R.string.password_error);
    }

    @Test
    public void login_UsernameAndPasswordAreCorrect_StartMainActivity() {
        when(view.getUsername()).thenReturn("Jones");
        when(view.getPassword()).thenReturn("password");
        userRepository.validateUser("lecturer", "Jones", "password", "123456", callback);
        presenter.login();

        verify(callback, times(1)).unsuccessful("");
    }
}