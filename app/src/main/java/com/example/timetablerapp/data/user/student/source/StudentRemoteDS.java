package com.example.timetablerapp.data.user.student.source;

import android.util.Log;

import com.example.timetablerapp.util.SuccessfulCallback;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.user.RequestParams;
import com.example.timetablerapp.data.user.UserApi;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.student.StudentApi;
import com.example.timetablerapp.data.user.student.model.StudentResponse;
import com.example.timetablerapp.data.user.student.model.UserResponse;
import com.example.timetablerapp.data.utils.security_utils.SaltReponse;
import com.example.timetablerapp.data.user.student.model.Student;
import com.example.timetablerapp.data.user.student.model.StudentRequest;
import com.example.timetablerapp.data.utils.RetrofitClient;

import org.jetbrains.annotations.NotNull;

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
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful()) {
                    callBack.userIsAuthSuccessful("Successfully registered.");
                } else {
                    callBack.authNotSuccessful("An Error occurred, please try again.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
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
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful("Please contact administrator for assistance.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
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
            public void onResponse(@NotNull Call<SaltReponse> call, @NotNull Response<SaltReponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String salt = response.body().getSalt();
                    callBack.successful(salt);
                } else {
                    callBack.unsuccessful(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SaltReponse> call, @NotNull Throwable t) {
                callBack.unsuccessful(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getDetails(String userId, String userRole, LoadUserDetailsCallback callback) {
        RequestParams req = new RequestParams("", userId, userRole);
        Call<StudentResponse> call = RetrofitClient.getRetrofit()
                .create(StudentApi.class)
                .getDetails("application/json", req);

        call.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(@NotNull Call<StudentResponse> call, @NotNull Response<StudentResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    callback.loadData(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<StudentResponse> call, @NotNull Throwable t) {
                if (t instanceof ConnectException)
                    callback.unsuccessful("Check your connection and try again.");
                else
                    callback.unsuccessful("Please contact the admin to resolve the issue.");
            }
        });
    }

    @Override
    public void changePassword(String userId, String role, LecturerDS.SuccessCallback callback, String hashedNewPasswd) {
        UserResponse req = new UserResponse();
        req.setPassword(hashedNewPasswd);
        req.setUserId(userId);
        req.setRole(role);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(StudentApi.class)
                .changePassword("application/json", req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body().getMessage());
                } else {
                    callback.unsuccessful("Please contact the administrator");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: Error: " + t.getMessage(), t);

                if (t instanceof ConnectException) {
                    callback.unsuccessful("Check you internet connection settings, then try again.");
                } else {
                    callback.unsuccessful("Please contact the administrator to resolve the problem: " + t.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void updateUserDetails(Student obj, SuccessfulCallback callback) {
        StudentRequest req = new StudentRequest();
        req.setStudent(obj);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(StudentApi.class)
                .updateUserDetails("application/json", req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(Constants.MESSAGE_SUCCESS);
                } else {
                    callback.unsuccessful("Please try again, or contact the administrator.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                if (t instanceof ConnectException)
                    callback.unsuccessful(Constants.CHECK_CONNECTION);
                else
                    callback.unsuccessful(Constants.OTHER_ISSUE + t.getLocalizedMessage());
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
