package com.example.timetablerapp.data.user.student.source;

import android.util.Log;

import com.example.timetablerapp.SuccessfulCallback;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.user.RequestParams;
import com.example.timetablerapp.data.user.UserApi;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.student.StudentApi;
import com.example.timetablerapp.data.utils.security_utils.SaltReponse;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.model.StudentRequest;
import com.example.timetablerapp.data.utils.RetrofitClient;

import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 06/05/19 -bernard
 */
public class StudentRemoteDS implements UserDataSource<Student> {

    private static final String TAG = StudentRemoteDS.class.getSimpleName();

    @Override
    public void userSignUp(UserAuthCallback callBack, Student obj, String pass) {
        StudentRequest request = new StudentRequest();
        request.setStudent(obj);

        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(StudentApi.class)
                .signUpStudent("application/json", request);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful()) {
                    callBack.userIsAuthSuccessful("Successfully registered.");
                } else {
                    callBack.authNotSuccessful("An Error occurred, please try again.");
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                callBack.authNotSuccessful("Error: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void authUser(UserAuthCallback callBack, Student obj) {

    }

    @Override
    public void updateUsername(String name, String userId, String role, SuccessfulCallback callback) {
        RequestParams requestParams = new RequestParams(name, userId, role);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(StudentApi.class)
                .updateUsername("application/json", requestParams);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful("Please contact administrator for assistance.");
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof ConnectException) {
                    callback.unsuccessful("Check your internet connection and try again.");
                } else {
                    callback.unsuccessful("Please contact administrator, " + t.getLocalizedMessage());
                }
            }
        });
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
