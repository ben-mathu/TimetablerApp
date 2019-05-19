package com.example.timetablerapp.data.user.student.source;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.user.UserApi;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.student.StudentDataSource;
import com.example.timetablerapp.data.user.security_utils.SaltReponse;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 06/05/19 -bernard
 */
public class StudentDataSourceRemote implements UserDataSource<Student> {

    @Override
    public void userSignUp(UserAuthCallback callBack, Student obj) {

    }

    @Override
    public void authUser(UserAuthCallback callBack, Student obj) {

    }

    @Override
    public void validateUser(String role, String username, String password, UserAuthCallback callback) {
    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {
        Call<SaltReponse> call = RetrofitClient.getRetrofit()
                .create(UserApi.class)
                .getSalt(role);

        call.enqueue(new Callback<SaltReponse>() {
            @Override
            public void onResponse(Call<SaltReponse> call, Response<SaltReponse> response) {
                if (response.isSuccessful()) {
                    String salt = response.body().getSalt();
                    callBack.successful(salt);
                } else {
                    callBack.unsuccessful(response.message());
                }
            }

            @Override
            public void onFailure(Call<SaltReponse> call, Throwable t) {
                callBack.unsuccessful(t.getMessage());
                t.printStackTrace();
            }
        });
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
