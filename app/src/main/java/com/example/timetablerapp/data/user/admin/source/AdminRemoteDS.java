package com.example.timetablerapp.data.user.admin.source;

import android.util.Log;

import com.example.timetablerapp.data.response.SuccessfulReport;
import com.example.timetablerapp.data.user.UserDataSource;
import com.example.timetablerapp.data.user.admin.AdminApi;
import com.example.timetablerapp.data.user.admin.model.Admin;
import com.example.timetablerapp.data.user.admin.model.AdminRequest;
import com.example.timetablerapp.data.utils.RetrofitClient;

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

        Call<SuccessfulReport> call  = RetrofitClient.getRetrofit()
                .create(AdminApi.class)
                .register("application/json", request);

        call.enqueue(new Callback<SuccessfulReport>() {
            @Override
            public void onResponse(Call<SuccessfulReport> call, Response<SuccessfulReport> response) {
                if (response.isSuccessful()) {
                    callBack.userIsAuthSuccessful(response.body().getMessage());
                } else {
                    callBack.authNotSuccessful("An error has occurred, please try again");
                }
            }

            @Override
            public void onFailure(Call<SuccessfulReport> call, Throwable t) {
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
