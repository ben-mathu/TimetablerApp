package com.example.timetablerapp.data.campuses.source;

import android.util.Log;

import com.example.timetablerapp.data.campuses.CampusApi;
import com.example.timetablerapp.data.campuses.CampusesDS;
import com.example.timetablerapp.data.campuses.model.Campus;
import com.example.timetablerapp.data.campuses.model.CampusRequest;
import com.example.timetablerapp.data.campuses.model.CampusesReponse;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 08/05/19 -bernard
 */
public class CampusRemoteDS implements CampusesDS {
    private static final String TAG = CampusRemoteDS.class.getSimpleName();

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
    public void addCampus(Campus campus, SuccessfullySavedCallback callback) {
        CampusRequest req = new CampusRequest(campus);
        Call<CampusRequest> call = RetrofitClient.getRetrofit()
                .create(CampusApi.class)
                .addCampus("application/json", req);

        call.enqueue(new Callback<CampusRequest>() {
            @Override
            public void onResponse(Call<CampusRequest> call, Response<CampusRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successItem(response.body().getCampus());
                } else {
                    callback.unSuccess(response.message());
                }
            }

            @Override
            public void onFailure(Call<CampusRequest> call, Throwable t) {
                callback.unSuccess("An error has occurred, please contact administrator");
            }
        });
    }

    @Override
    public void updateCampus(Campus campus, SuccessfullySavedCallback callback) {
        CampusRequest req = new CampusRequest(campus);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(CampusApi.class)
                .updateCampus("application/json", req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body().getMessage());
                } else {
                    callback.unSuccess(response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                callback.unSuccess("Error: " + t.getLocalizedMessage());
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void deleteRemote(Campus campus, SuccessfullySavedCallback callback) {
        CampusRequest req = new CampusRequest(campus);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(CampusApi.class)
                .deleteCampus("application/json", req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body().getMessage());
                } else {
                    callback.unSuccess(response.message());
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                callback.unSuccess("Error: " + t.getLocalizedMessage());
                Log.e(TAG, "onFailure: ", t);
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
