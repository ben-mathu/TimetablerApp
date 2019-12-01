package com.example.timetablerapp.data.room;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.room.model.RoomResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

/**
 * 26/11/19
 *
 * @author bernard
 */
public interface RoomAPI {
    @PUT("delete-room")
    Call<MessageReport> deleteRoom(@Header (Constants.CONTENT_TYPE) String contentType,
                                   @Body RoomResponse response);

    @PUT("update-room")
    Call<MessageReport> updateRoom(@Header(Constants.CONTENT_TYPE) String contentType, @Body RoomResponse response);
}
