package com.example.timetablerapp.data.user.student.source;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.user.UserApi;
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
public class StudentDataSourceRemote implements StudentDataSource {

    @Override
    public void validateUser(String username, String password) {
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
        String out = "";
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
