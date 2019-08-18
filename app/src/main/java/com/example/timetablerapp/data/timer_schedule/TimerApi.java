package com.example.timetablerapp.data.timer_schedule;

import com.example.timetablerapp.data.settings.model.DeadlineSettings;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * 29/07/19 -bernard
 */
public interface TimerApi {
    @GET("schedule-registration")
    Call<DeadlineSettings> getRegistrationSchedule();

    @PUT("schedule-registration")
    Call<String> deadline();
}
