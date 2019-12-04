package com.example.timetablerapp.data.hall.source;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.hall.HallDS;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.hall.model.HallResponse;
import com.example.timetablerapp.data.hall.model.HallsResponse;
import com.example.timetablerapp.data.hall.model.RoomsResponse;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.room.model.Room;
import com.example.timetablerapp.data.room.model.RoomRequest;
import com.example.timetablerapp.data.utils.RetrofitClient;
import com.example.timetablerapp.util.SuccessfulCallback;

import org.jetbrains.annotations.NotNull;

import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 03/09/19 -bernard
 */
public class HallRemoteDS implements HallDS{
    public void getHalls(String facultyId, HallDS.HallLoadedCallback hallLoadedCallback) {
        Call<HallsResponse> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .getHalls(facultyId);

        call.enqueue(new Callback<HallsResponse>() {
            @Override
            public void onResponse(@NotNull Call<HallsResponse> call, @NotNull Response<HallsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hallLoadedCallback.loadingHallsSuccessful(response.body().getList());
                } else {
                    hallLoadedCallback.dataNotAvailable("Data not available");
                }
            }

            @Override
            public void onFailure(@NotNull Call<HallsResponse> call, @NotNull Throwable t) {
                hallLoadedCallback.dataNotAvailable("An error occurred");
            }
        });
    }

    public void getRooms(HallDS.RoomsLoadedCallback roomsLoadedCallback) {
        Call<RoomsResponse> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .getRooms();

        call.enqueue(new Callback<RoomsResponse>() {
            @Override
            public void onResponse(@NotNull Call<RoomsResponse> call, @NotNull Response<RoomsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    roomsLoadedCallback.loadingRoomsSuccessful(response.body().getList());
                } else {
                    roomsLoadedCallback.dataNotAvailable("Data is not available");
                }
            }

            @Override
            public void onFailure(@NotNull Call<RoomsResponse> call, @NotNull Throwable t) {
                roomsLoadedCallback.dataNotAvailable("An error occurred, please contact administrator");
            }
        });
    }

    @Override
    public void getHall(String hall_id, LoadHallCallback callback) {
        Call<HallResponse> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .getHall(hall_id);

        call.enqueue(new Callback<HallResponse>() {
            @Override
            public void onResponse(@NotNull Call<HallResponse> call, @NotNull Response<HallResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.loadHall(response.body().getHall());
                } else {
                    callback.unsuccessful("Hall was not found, please contact the administrator.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<HallResponse> call, @NotNull Throwable t) {
                if (t instanceof ConnectException)
                    callback.unsuccessful("Check your connection and try again.");
                else callback.unsuccessful("Please contact the administrator.");
            }
        });
    }

    @Override
    public void getHalls(HallLoadedCallback hallLoadedCallback) {
        Call<HallsResponse> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .getHalls();

        call.enqueue(new Callback<HallsResponse>() {
            @Override
            public void onResponse(Call<HallsResponse> call, Response<HallsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hallLoadedCallback.loadingHallsSuccessful(response.body().getList());
                } else {
                    hallLoadedCallback.dataNotAvailable("please try again or contact the administrator");
                }
            }

            @Override
            public void onFailure(Call<HallsResponse> call, Throwable t) {
                if (t instanceof ConnectException)
                    hallLoadedCallback.dataNotAvailable("Check your connection and try again.");
                else
                    hallLoadedCallback.dataNotAvailable("Please contact the administrator.");
            }
        });
    }

    @Override
    public void addHall(Hall hall, SuccessfulCallback callback) {
        HallResponse req = new HallResponse();
        req.setHall(hall);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .addHall(Constants.APPLICATION_JSON, req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful("Please try again.");
                }
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                if (t instanceof ConnectException)
                    callback.unsuccessful("Check your connection.");
                else
                    callback.unsuccessful("Please contact the administrator.");
            }
        });
    }

    public void addRooms(Room room, String passcode, HallDS.Success success) {
        RoomRequest request = new RoomRequest(room, passcode);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .addRoom("application/json", request);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    success.success(response.body().getMessage());
                } else {
                    success.unsuccess("Please try again");
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                success.unsuccess("An error has occurred, please contact administrator");
            }
        });
    }

    @Override
    public void update(Hall item, SuccessfulCallback callback) {
        HallResponse req = new HallResponse();
        req.setHall(item);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .update(Constants.APPLICATION_JSON, req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null)
                    callback.successful(response.body().getMessage());
                else
                    callback.unsuccessful("Please try again.");
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                if (t instanceof ConnectException)
                    callback.unsuccessful("Check your internet.");
                else
                    callback.unsuccessful("Please contact the administrator.");
            }
        });
    }

    @Override
    public void delete(Hall item, SuccessfulCallback callback) {
        HallResponse req = new HallResponse();
        req.setHall(item);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .deleteHall(Constants.APPLICATION_JSON, req);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(Call<MessageReport> call, Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null)
                    callback.successful(response.body().getMessage());
                else
                    callback.unsuccessful("Please try again.");
            }

            @Override
            public void onFailure(Call<MessageReport> call, Throwable t) {
                if (t instanceof ConnectException)
                    callback.successful("Check you internet");
                else
                    callback.unsuccessful("Please contact administrator.");
            }
        });
    }

    @Override
    public void save(Hall item, SuccessfulCallback callback) {

    }
}
