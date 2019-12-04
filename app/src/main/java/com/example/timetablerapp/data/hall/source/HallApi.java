package com.example.timetablerapp.data.hall.source;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.hall.model.HallResponse;
import com.example.timetablerapp.data.hall.model.HallsResponse;
import com.example.timetablerapp.data.hall.model.RoomsResponse;
import com.example.timetablerapp.data.response.MessageReport;
import com.example.timetablerapp.data.room.model.RoomRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

import static com.example.timetablerapp.data.Constants.HALL_ID;

/**
 * 03/09/19 -bernard
 */
public interface HallApi {
    @GET("halls")
    Call<HallsResponse> getHalls(@Query(Constants.FACULTY_ID) String facultyId);

    @POST("add-room")
    Call<MessageReport> addRoom(@Header("Content-Type") String contentType,
                                @Body RoomRequest request);

    @GET("rooms")
    Call<RoomsResponse> getRooms();

    @GET("hall")
    Call<HallResponse> getHall(@Query(HALL_ID) String hall_id);

    @GET("halls")
    Call<HallsResponse> getHalls();

    @PUT("add-hall")
    Call<MessageReport> addHall(@Header(Constants.CONTENT_TYPE) String contentType,
                                @Body HallResponse req);

    @PUT("delete-hall")
    Call<MessageReport> deleteHall(@Header(Constants.CONTENT_TYPE) String contentType,
                                   @Body HallResponse req);

    @PUT("update-hall")
    Call<MessageReport> update(@Header(Constants.CONTENT_TYPE) String contentType,
                               @Body HallResponse req);
}
