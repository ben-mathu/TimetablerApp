package com.example.timetablerapp.data.timer_schedule;

import com.example.timetablerapp.data.Constants;
import com.example.timetablerapp.data.settings.model.DeadlineSettings;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * 29/07/19 -bernard
 */
public interface TimerApi {
    @GET("schedule-registration")
    Call<DeadlineSettings> getRegistrationSchedule();

    @GET("schedule-registration")
    Call<DeadlineSettings> getRegistrationSchedule(@Query(Constants.LECTURER_ID) String userId);

    @PUT("schedule-registration")
    Call<String> deadline();
}
