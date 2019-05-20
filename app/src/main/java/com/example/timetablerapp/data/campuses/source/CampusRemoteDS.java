package com.example.timetablerapp.data.campuses.source;

import com.example.timetablerapp.data.campuses.CampusApi;
import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.campuses.model.CampusesReponse;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 08/05/19 -bernard
 */
public class CampusRemoteDS implements CampusesDS {
    @Override
    public void getAllFromRemote(LoadCampusesCallBack callBack) {
        Call<CampusesReponse> call = RetrofitClient.getRetrofit()
                .create(CampusApi.class)
                .getAll();

        call.enqueue(new Callback<CampusesReponse>() {
            @Override
            public void onResponse(Call<CampusesReponse> call, Response<CampusesReponse> response) {
                if (response.isSuccessful()) {
                    List<Campus> campuses = response.body().getCampuses();
                    callBack.loadCampusesSuccessful(campuses);
                } else {
                    callBack.dataNotAvailable(response.message());
                }
            }

            @Override
            public void onFailure(Call<CampusesReponse> call, Throwable t) {
                callBack.dataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void update(Campus item) {

    }

    @Override
    public void delete(Campus item) {

    }

    @Override
    public void save(Campus item) {

    }
}
