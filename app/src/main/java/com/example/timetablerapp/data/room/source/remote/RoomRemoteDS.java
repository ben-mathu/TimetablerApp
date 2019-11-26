package com.example.timetablerapp.data.room.source.remote;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.room.RoomAPI;
import com.example.timetablerapp.data.room.model.Room;
import com.example.timetablerapp.data.room.model.RoomResponse;
import com.example.timetablerapp.data.room.source.RoomDS;
import com.example.timetablerapp.data.utils.RetrofitClient;
import com.example.timetablerapp.util.SuccessfulCallback;

import org.jetbrains.annotations.NotNull;

import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 26/11/19
 *
 * @author bernard
 */
public class RoomRemoteDS implements RoomDS {
    @Override
    public void update(Room item, SuccessfulCallback callback) {
        RoomResponse response = new RoomResponse();
        response.setRoom(item);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(RoomAPI.class)
                .updateRoom(Constants.APPLICATION_JSON, response);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                if (t instanceof ConnectException)
                    callback.successful("Check your connection and try again.");
                else
                    callback.unsuccessful("Please contact the administrator " + t.getLocalizedMessage() + ".");
            }
        });
    }

    @Override
    public void delete(Room item, SuccessfulCallback callback) {
        RoomResponse response = new RoomResponse();
        response.setRoom(item);
        Call<MessageReport> call = RetrofitClient.getRetrofit()
                .create(RoomAPI.class)
                .deleteRoom(Constants.APPLICATION_JSON, response);

        call.enqueue(new Callback<MessageReport>() {
            @Override
            public void onResponse(@NotNull Call<MessageReport> call, @NotNull Response<MessageReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.successful(response.body().getMessage());
                } else {
                    callback.unsuccessful("Please contact the administrator, " + response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<MessageReport> call, @NotNull Throwable t) {
                if (t instanceof ConnectException)
                    callback.unsuccessful("Check your connection and try again.");
                else callback.unsuccessful("Please contact the administrator.\n" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void save(Room item, SuccessfulCallback callback) {

    }
}
