package com.example.timetablerapp.data.units.source.remote;

import android.util.Log;

import com.example.timetablerapp.data.timetable.TimetableApi;
import com.example.timetablerapp.data.timetable.model.TimetableResponse;
import com.example.timetablerapp.data.units.UnitApi;
import com.example.timetablerapp.data.units.UnitDataSource;
import com.example.timetablerapp.data.units.model.Unit;
import com.example.timetablerapp.data.units.model.UnitRequest;
import com.example.timetablerapp.data.units.model.UnitResponse;
import com.example.timetablerapp.data.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 19/05/19 -bernard
 */
public class UnitsRemoteDS implements UnitDataSource {
    private static final String TAG = UnitsRemoteDS.class.getSimpleName();
    @Override
    public void update(Unit item) {

    }

    @Override
    public void delete(Unit item) {

    }

    @Override
    public void save(Unit item) {

    }

    @Override
    public void getUnitsByLecturerId(String id, UnitsLoadedCallback callback) {
        UnitRequest request = new UnitRequest();
        request.setLecturerId(id);
        Call<UnitResponse> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
                .getUnits(id);

        call.enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                if (response.isSuccessful()) {
                    List<Unit> unitList = response.body().getUnitList();
                    if (unitList != null) {
                        callback.successful(unitList);
                    }
                } else {
                    callback.unsuccessful("Units are not available, please contact admin");
                }
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.unsuccessful("An error occurred, please contact admin.");
            }
        });
    }

    @Override
    public void getUnitsByStudentId(String strId, UnitsLoadedCallback callback) {
        Call<UnitResponse> call = RetrofitClient.getRetrofit()
                .create(UnitApi.class)
                .getUnitsByStudentId(strId);

        call.enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                if (response.isSuccessful()) {
                    List<Unit> unitList = response.body().getUnitList();
                    if (unitList != null) {
                        callback.successful(unitList);
                    }
                } else {
                    callback.unsuccessful("Units are not available, please contact admin");
                }
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.unsuccessful("An error occurred, please contact admin.");
            }
        });
    }

    @Override
    public void getTimetableByStudentId(String studentId, TimetableLoadedCallback callback) {
        Call<TimetableResponse> call = RetrofitClient.getRetrofit()
                .create(TimetableApi.class)
                .getTimetableByStudentId(studentId);

        call.enqueue(new Callback<TimetableResponse>() {
            @Override
            public void onResponse(Call<TimetableResponse> call, Response<TimetableResponse> response) {
                if (response.isSuccessful()) {
                    callback.successful(response.body().getTimetableList());
                } else {
                    callback.unsuccessful("An error occurred, please contact the administrator.");
                }
            }

            @Override
            public void onFailure(Call<TimetableResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                callback.unsuccessful("An error occurred, please contact the administrator.");
            }
        });
    }

    @Override
    public void getTimetableByLecturerId(String lecturerId, TimetableLoadedCallback callback) {

    }

    @Override
    public void getTimetable(TimetableLoadedCallback callback) {

    }
}
