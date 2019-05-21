package com.example.timetablerapp.data.user.student;


import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.source.StudentDataSourceLocal;
import com.example.timetablerapp.data.user.student.source.StudentDataSourceRemote;

/**
 * 06/05/19 -bernard
 */
public class StudentRepository implements UserDataSource<Student> {
    private static StudentRepository INSTANCE = null;
    private StudentDataSourceRemote userDataSourceRemote;
    private StudentDataSourceLocal userDataSourceLocal;


    public StudentRepository(StudentDataSourceRemote userDataSourceRemote, StudentDataSourceLocal userDataSourceLocal) {
        this.userDataSourceLocal = userDataSourceLocal;
        this.userDataSourceRemote = userDataSourceRemote;
    }

    public static StudentRepository newInstance(StudentDataSourceRemote userDataSourceRemote, StudentDataSourceLocal userDataSourceLocal) {
        if (INSTANCE == null) {
            INSTANCE = new StudentRepository(userDataSourceRemote, userDataSourceLocal);
        }
        return INSTANCE;
    }

    @Override
    public void userSignUp(UserAuthCallback callBack, Student obj) {
        save(obj);
        userDataSourceRemote.userSignUp(new UserAuthCallback() {
            @Override
            public void userIsAuthSuccessfull(String message) {
                callBack.userIsAuthSuccessfull(message);
            }

            @Override
            public void authNotSuccessful(String message) {
                callBack.authNotSuccessful(message);
            }
        }, obj);

    }

    @Override
    public void authUser(UserAuthCallback callBack, Student obj) {

    }

    @Override
    public void validateUser(String role, String username, String password, UserAuthCallback callback) {
        userDataSourceRemote.validateUser(role, username, password, new UserAuthCallback() {
            @Override
            public void userIsAuthSuccessfull(String message) {
                callback.userIsAuthSuccessfull(message);
            }

            @Override
            public void authNotSuccessful(String message) {
                callback.authNotSuccessful(message);
            }
        });
    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {
        userDataSourceRemote.sendUserRole(new GetSaltCallBack() {
            @Override
            public void successful(String salt) {
                callBack.successful(salt);
            }

            @Override
            public void unsuccessful(String message) {
                callBack.unsuccessful(message);
            }
        }, role);
    }

    @Override
    public void update(Student item) {

    }

    @Override
    public void delete(Student item) {

    }

    @Override
    public void save(Student item) {
        userDataSourceLocal.save(item);
    }
}
