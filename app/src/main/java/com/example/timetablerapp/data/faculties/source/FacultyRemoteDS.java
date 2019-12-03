package com.example.timetablerapp.data.faculties.source;

import android.util.Log;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.faculties.FacultyApi;
import com.example.timetablerapp.data.faculties.FacultyDS;
import com.example.timetablerapp.data.faculties.model.FacultiesResponse;
import com.example.timetablerapp.data.faculties.model.Faculty;
import com.example.timetablerapp.data.faculties.model.FacultyRequest;
import com.example.timetablerapp.data.faculties.model.FacultyResponse;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.utils.RetrofitClient;
import com.example.timetablerapp.util.SuccessfulCallback;

import org.jetbrains.annotations.NotNull;

import java.net.ConnectException;
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
    public void getAllFromRemote(LoadFacultiesCallBack callBack, String campusId) {
        Call<FacultiesResponse> call = RetrofitClient.getRetrofit()
                .create(FacultyApi.class)
                .getAll(campusId);

        call.enqueue(new Callback<FacultiesResponse>() {
            @Override
            public void onResponse(Call<FacultiesResponse> call, Response<FacultiesResponse> response) {
                if (response.isSuccessful()) {
                    List<Faculty> faculties = response.body().getFaculties();
                    callBack.loadingFacultiesSuccessful(faculties);
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
                if (response.isSuccessful() && response.body() != null) {
                    callBack.loadingFacultiesSuccessful(response.body().getList());
                } else {
                    callBack.dataNotAvailable("Data not available.");
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
    public void addFaculty(Faculty faculty, SuccessFulCallback callback) {
        FacultyRequest req = new FacultyRequest(faculty);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(FacultyApi.class)
                .addFaculty("application/json", req);

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
                callback.unSuccess("Error, please contact administrator");
            }
        });
    }

    @Override
    public void getFacultyById(String facultyId, LoadFacultyCallback callback) {
        Call<FacultyRequest> call = RetrofitClient.getRetrofit()
                .create(FacultyApi.class)
                .getFaculty(facultyId);

        call.enqueue(new Callback<FacultyRequest>() {
            @Override
            public void onResponse(Call<FacultyRequest> call, Response<FacultyRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successfullyLoadedFaculty(response.body().getFaculty());
                }
            }

            @Override
            public void onFailure(Call<FacultyRequest> call, Throwable t) {
                if (t instanceof ConnectException) {
                    callback.unsuccessful("Check your connection and try again.");
                } else {
                    callback.unsuccessful("Please contact administrator.");
                }
            }
        });
    }

    @Override
    public void update(Faculty item, SuccessfulCallback callback) {
        FacultyRequest req = new FacultyRequest(item);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(FacultyApi.class)
                .update(Constants.APPLICATION_JSON, req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful("Please try again or contact the administrator.");
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                callback.unsuccessful("Error: " + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void delete(Faculty item, SuccessfulCallback callback) {
        FacultyRequest req = new FacultyRequest(item);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(FacultyApi.class)
                .delete(Constants.APPLICATION_JSON, req);

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
    public void save(Faculty item, SuccessfulCallback callback) {

    }
}
