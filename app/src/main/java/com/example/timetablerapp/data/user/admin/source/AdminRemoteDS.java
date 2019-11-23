package com.example.timetablerapp.data.user.admin.source;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.timetablerapp.util.SuccessfulCallback;
import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.user.RequestParams;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.admin.AdminApi;
import com.example.timetablerapp.data.user.admin.AdminDAO;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.admin.model.AdminRequest;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.student.model.UserResponse;
import com.example.timetablerapp.data.utils.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 22/05/19 -bernard
 */
public class AdminRemoteDS implements UserDataSource<Admin> {
    private static final String TAG = AdminRemoteDS.class.getSimpleName();

    @Override
    public void userSignUp(UserAuthCallback callBack, Admin obj, String pass) {

        AdminRequest request = new AdminRequest();
        request.setAdmin(obj);
        request.setDbPassword(pass);

        Call<MessageReport> call  = RetrofitClient.getRetrofit()
                .create(AdminApi.class)
                .register("application/json", request);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NonNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.userIsAuthSuccessful(response.body().getMessage());
                } else {
                    callBack.authNotSuccessful("An error has occurred, please try again");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                callBack.authNotSuccessful("An error has occurred");
                Log.e(TAG, "onFailure: ", t);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void authUser(UserAuthCallback callBack, Admin obj) {

    }

    @Override
    public void updateUsername(String name, String userId, String role, SuccessfulCallback callback) {
        RequestParams requestParams = new RequestParams(name, userId, role);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(AdminApi.class)
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
    public void getDetails(String userId, String userRole, LoadUserDetailsCallback callback) {
        RequestParams requestParams = new RequestParams("", userId, userRole);
        Call<AdminDAO> call = RetrofitClient.getRetrofit()
                .create(AdminApi.class)
                .getDetails("application/json", requestParams);

        call.enqueue(new Callback<AdminDAO>() {
            @Override
            public void onResponse(@NotNull Call<AdminDAO> call, @NotNull Response<AdminDAO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.loadData(response.body().getAdmin());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AdminDAO> call, @NotNull Throwable t) {
                if (t instanceof ConnectException) {
                    callback.unsuccessful("Check your internet settings and try again.");
                } else {
                    callback.unsuccessful("Please contact the administrator.");
                }
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
                .create(AdminApi.class)
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
    public void updateUserDetails(Admin obj, SuccessfulCallback callback) {
        AdminRequest req = new AdminRequest();
        req.setAdmin(obj);
        req.setDbPassword("benard");
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(AdminApi.class)
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
    public void deleteAccount(String userRole, String userId, SuccessfulCallback callback) {
        RequestParams req = new RequestParams("", userId, userRole);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(AdminApi.class)
                .deleteAccount(Constants.APPLICATION_JSON, req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful(Constants.OTHER_ISSUE + response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                if (t instanceof ConnectException) {
                    callback.unsuccessful(Constants.CHECK_CONNECTION);
                } else {
                    callback.unsuccessful(Constants.OTHER_ISSUE + t.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void validateUser(String role, String username, String password, String userId, UserAuthCallback callback) {

    }

    @Override
    public void sendUserRole(GetSaltCallBack callBack, String role) {

    }

    @Override
    public void fetchSettingsFromRemote(FetchSettingsCallback callback) {

    }

    @Override
    public void update(Admin item) {

    }

    @Override
    public void delete(Admin item) {

    }

    @Override
    public void save(Admin item) {

    }
}
