package com.example.timetablerapp.data.user.lecturer.source;

import com.example.timetablerapp.data.response.SuccessfulReport;
import com.example.timetablerapp.data.user.lecturer.LecturerDS;
import com.example.timetablerapp.data.user.lecturer.model.Lecturer;
import com.example.timetablerapp.data.user.lecturer.model.LecturerRequest;
import com.example.timetablerapp.data.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 08/05/19 -bernard
 */
public class LecturerRemoteDS implements LecturerDS {
    @Override
    public void userSignUp(LecturerIsAuthCallBack callBack, Lecturer lecturer) {
        LecturerRequest lecturerRequest = new LecturerRequest();
        lecturerRequest.setLecturer(lecturer);

        Call<SuccessfulReport> call = RetrofitClient.getRetrofit()
                .create(LecturerApi.class)
                .signUpLec("application/json", lecturerRequest);

        call.enqueue(new Callback<SuccessfulReport>() {
            @Override
            public void onResponse(Call<SuccessfulReport> call, Response<SuccessfulReport> response) {
                if (response.isSuccessful()) {
                    callBack.userIsAuthSuccessfull(response.body().getMessage());
                } else {
                    callBack.authNotSuccessful(response.message());
                }
            }

            @Override
            public void onFailure(Call<SuccessfulReport> call, Throwable t) {
                callBack.authNotSuccessful(t.getMessage());
            }
        });
    }

    @Override
    public void authUser(LecturerIsAuthCallBack callBack, Lecturer lecturer) {

    }

    @Override
    public void update(Lecturer item) {

    }

    @Override
    public void delete(Lecturer item) {

    }

    @Override
    public void save(Lecturer item) {

    }
}
