package com.example.timetablerapp.data.user.student.source;

import com.example.timetablerapp.data.response.SuccessfulReport;
import com.example.timetablerapp.data.user.UserApi;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.student.StudentApi;
import com.example.timetablerapp.data.utils.security_utils.SaltReponse;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.model.StudentRequest;
import com.example.timetablerapp.data.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 06/05/19 -bernard
 */
public class StudentRemoteDS implements UserDataSource<Student> {

    @Override
    public void userSignUp(UserAuthCallback callBack, Student obj, String pass) {
        StudentRequest request = new StudentRequest();
        request.setStudent(obj);

        Call<SuccessfulReport> call = RetrofitClient.getRetrofit()
                .create(StudentApi.class)
                .signUpStudent("application/json", request);

        call.enqueue(new Callback<SuccessfulReport>() {
            @Override
            public void onResponse(Call<SuccessfulReport> call, Response<SuccessfulReport> response) {
                if (response.isSuccessful()) {
                    callBack.userIsAuthSuccessful("Successfully registered.");
                } else {
                    callBack.authNotSuccessful("An Error occurred, please try again.");
                }
            }

            @Override
            public void onFailure(Call<SuccessfulReport> call, Throwable t) {
                callBack.authNotSuccessful("Error: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void authUser(UserAuthCallback callBack, Student obj) {

    }

    @Override
    public void validateUser(String role, String username, String password, String userId, UserAuthCallback callback) {
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
    public void fetchSettingsFromRemote(FetchSettingsCallback callback) {

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
