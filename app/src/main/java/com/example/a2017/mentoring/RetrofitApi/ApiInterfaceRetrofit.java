package com.example.a2017.mentoring.RetrofitApi;

import com.example.a2017.mentoring.Model.Register;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 2017 on 04/02/2017.
 */

public interface ApiInterfaceRetrofit
{
    @POST("addNewUser")
    Call<String> addNewUser(@Body Register register);

    @POST("x")
    Call<String> addNewUser(@Body String x);
}
