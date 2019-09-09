package com.example.timetablerapp.data.programmes.source;

import android.support.v7.widget.RecyclerView;

import com.example.timetablerapp.data.programmes.ProgrammeApi;
import com.example.timetablerapp.data.programmes.ProgrammeDS;
import com.example.timetablerapp.data.programmes.model.Programme;
import com.example.timetablerapp.data.programmes.model.ProgrammeRequest;
import com.example.timetablerapp.data.programmes.model.ProgrammesResponse;
import com.example.timetablerapp.data.response.MessageReport;
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
    public void getAllFromRemote(LoadProgrammesCallBack callBack, String departmentId) {
        Call<ProgrammesResponse> call = RetrofitClient.getRetrofit()
                .create(ProgrammeApi.class)
                .getAll(departmentId);

        call.enqueue(new Callback<ProgrammesResponse>() {
            @Override
            public void onResponse(Call<ProgrammesResponse> call, Response<ProgrammesResponse> response) {
                if (response.isSuccessful()) {
                    callBack.loadProgrammesSuccessfully(response.body().getProgrammes());
                } else {
                    callBack.dataNotAvailable(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProgrammesResponse> call, Throwable t) {
                callBack.dataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getFromLocalDb(LoadProgrammesCallBack callBack) {

    }

    @Override
    public void getAllProgrammes(LoadProgrammesCallBack callback) {
        Call<ProgrammesResponse> call = RetrofitClient.getRetrofit()
                .create(ProgrammeApi.class)
                .getAllProgrammes();

        call.enqueue(new Callback<ProgrammesResponse>() {
            @Override
            public void onResponse(Call<ProgrammesResponse> call, Response<ProgrammesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.loadProgrammesSuccessfully(response.body().getProgrammes());
                } else {
                    callback.dataNotAvailable(response.message());
                }
            }

            @Override
            public void onFailure(Call<ProgrammesResponse> call, Throwable t) {
                callback.dataNotAvailable("Error," + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void addProgramme(Programme programme, SuccessfullySavedCallback callback) {
        ProgrammeRequest req = new ProgrammeRequest(programme);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(ProgrammeApi.class)
                .addProgramme("application/json", req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body().getMessage());
                } else {
                    callback.unSuccessful(response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                callback.unSuccessful("Error: " + t.getLocalizedMessage());
                t.printStackTrace();
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
