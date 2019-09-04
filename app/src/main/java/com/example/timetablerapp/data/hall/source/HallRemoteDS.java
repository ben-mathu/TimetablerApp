package com.example.timetablerapp.data.hall.source;

import com.example.timetablerapp.data.hall.HallDS;
import com.example.timetablerapp.data.hall.model.Hall;
import com.example.timetablerapp.data.hall.model.HallResponse;
import com.example.timetablerapp.data.hall.model.RoomResponse;
import com.example.timetablerapp.data.response.SuccessfulReport;
import com.example.timetablerapp.data.room.Room;
import com.example.timetablerapp.data.utils.RetrofitClient;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 03/09/19 -bernard
 */
public class HallRemoteDS {
    public void getHalls(String facultyId, HallDS.HallLoadedCallback hallLoadedCallback) {
        Call<HallResponse> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .getHalls(facultyId);

        call.enqueue(new Callback<HallResponse>() {
            @Override
            public void onResponse(Call<HallResponse> call, Response<HallResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hallLoadedCallback.loadingHallsSuccessful(response.body().getList());
                } else {
                    hallLoadedCallback.dataNotAvailable("Data not available");
                }
            }

            @Override
            public void onFailure(Call<HallResponse> call, Throwable t) {
                hallLoadedCallback.dataNotAvailable("An error occurred");
            }
        });
    }

    public void getRooms(HallDS.RoomsLoadedCallback roomsLoadedCallback) {
        Call<RoomResponse> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .getRooms();

        call.enqueue(new Callback<RoomResponse>() {
            @Override
            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    roomsLoadedCallback.loadingRoomsSuccessful(response.body().getList());
                } else {
                    roomsLoadedCallback.dataNotAvailable("Data is not available");
                }
            }

            @Override
            public void onFailure(Call<RoomResponse> call, Throwable t) {
                roomsLoadedCallback.dataNotAvailable("An error occurred, please contact administrator");
            }
        });
    }

    public void addRooms(Room room, String passcode, HallDS.Success success) {
        RoomRequest request = new RoomRequest(room, passcode);
        Call<SuccessfulReport> call = RetrofitClient.getRetrofit()
                .create(HallApi.class)
                .addRoom("application/json", request);

        call.enqueue(new Callback<SuccessfulReport>() {
            @Override
            public void onResponse(Call<SuccessfulReport> call, Response<SuccessfulReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    success.success(response.body().getMessage());
                } else {
                    success.unsuccess("Please try again");
                }
            }

            @Override
            public void onFailure(Call<SuccessfulReport> call, Throwable t) {
                success.unsuccess("An error has occurred, please contact administrator");
            }
        });
    }

    public class RoomRequest {
        @SerializedName("room")
        private final Room room;
        @SerializedName("passcode")
        private final String passcode;

        public RoomRequest(Room room, String passcode) {
            this.room = room;
            this.passcode = passcode;
        }
    }
}
