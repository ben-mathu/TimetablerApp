package com.example.timetablerapp.data.faculties.source;

import android.util.Log;

import com.example.timetablerapp.data.faculties.FacultyApi;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.FacultiesResponse;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.faculties.model.FacultyResponse;
import com.example.timetablerapp.data.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 08/05/19 -bernard
 */
public class FacultyRemoteDS implements FacultyDS {
    private static final String TAG = FacultyRemoteDS.class.getSimpleName();

    @Override
    public void getAllFromRemote(LoadFacultiesCallBack callBack, String name) {
        Call<FacultiesResponse> call = RetrofitClient.getRetrofit()
                .create(FacultyApi.class)
                .getAll(name);

        call.enqueue(new Callback<FacultiesResponse>() {
            @Override
            public void onResponse(Call<FacultiesResponse> call, Response<FacultiesResponse> response) {
                if (response.isSuccessful()) {
                    List<Faculty> faculties = response.body().getFaculties();
                    callBack.gettinFacultiesSuccessful(faculties);
                } else {
                    callBack.dataNotAvailable(response.message());
                }
            }

            @Override
            public void onFailure(Call<FacultiesResponse> call, Throwable t) {
                callBack.dataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getAllFromRemote(LoadFacultiesCallBack callBack) {
        Call<FacultyResponse> call = RetrofitClient.getRetrofit()
                .create(FacultyApi.class)
                .getAll();

        call.enqueue(new Callback<FacultyResponse>() {
            @Override
            public void onResponse(Call<FacultyResponse> call, Response<FacultyResponse> response) {
                if (response.isSuccessful()) {
                    callBack.gettinFacultiesSuccessful(response.body().getList());
                } else {
                    callBack.dataNotAvailable(response.message());
                }
            }

            @Override
            public void onFailure(Call<FacultyResponse> call, Throwable t) {
                callBack.dataNotAvailable(t.getMessage());
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void update(Faculty item) {

    }

    @Override
    public void delete(Faculty item) {

    }

    @Override
    public void save(Faculty item) {

    }
}
