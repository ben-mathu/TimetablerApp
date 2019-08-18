package com.example.timetablerapp.data.user;

import com.example.timetablerapp.data.utils.security_utils.SaltReponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 07/05/19 -bernard
 */
public interface UserApi {
    @GET("roles")
    Call<SaltReponse> getSalt(@Query("user_role") String role);
}
