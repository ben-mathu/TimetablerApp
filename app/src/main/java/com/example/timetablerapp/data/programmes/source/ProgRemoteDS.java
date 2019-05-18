package com.example.timetablerapp.data.programmes.source;

import com.example.timetablerapp.data.programmes.ProgrammeApi;
import com.example.timetablerapp.data.programmes.ProgrammeDS;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 08/05/19 -bernard
 */
public class ProgRemoteDS implements ProgrammeDS {
    @Override
    public void getAllFromRemote(LoadProgrammesCallBack callBack, String name) {
        Call<List<Programme>> call = RetrofitClient.getRetrofit()
                .create(ProgrammeApi.class)
                .getAll(name);

        call.enqueue(new Callback<List<Programme>>() {
            @Override
            public void onResponse(Call<List<Programme>> call, Response<List<Programme>> response) {
                if (response.isSuccessful()) {
                    callBack.loadProgrammesSuccessfully(response.body());
                } else {
                    callBack.dataNotAvailable(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Programme>> call, Throwable t) {
                callBack.dataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void update(Programme item) {

    }

    @Override
    public void delete(Programme item) {

    }

    @Override
    public void save(Programme item) {

    }
}
