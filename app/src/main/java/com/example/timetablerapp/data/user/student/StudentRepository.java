package com.example.timetablerapp.data.user.student;


import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.source.StudentDataSourceLocal;
import com.example.timetablerapp.data.user.student.source.StudentDataSourceRemote;

/**
 * 06/05/19 -bernard
 */
public class StudentRepository implements StudentDataSource {
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
    public void validateUser(String username, String password) {
        userDataSourceRemote.validateUser(username, password);
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

    }
}
