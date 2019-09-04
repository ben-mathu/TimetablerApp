package com.example.timetablerapp.data.hall.source;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.hall.model.HallResponse;
import com.example.timetablerapp.data.hall.model.RoomResponse;
import com.example.timetablerapp.data.response.SuccessfulReport;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * 03/09/19 -bernard
 */
public interface HallApi {
    @GET("halls")
    Call<HallResponse> getHalls(@Query(Constants.FACULTY_ID) String facultyId);

    @POST("add-room")
    Call<SuccessfulReport> addRoom(@Header("Content-Type") String contentType,
                                   @Body HallRemoteDS.RoomRequest request);

    @GET("rooms")
    Call<RoomResponse> getRooms();
}
