package com.example.a2017.mentoring.RetrofitApi;

import com.example.a2017.mentoring.Model.Login;
import com.example.a2017.mentoring.Model.Register;
import com.example.a2017.mentoring.Model.*;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 2017 on 04/02/2017.
 */

public interface ApiInterfaceRetrofit
{
    @POST("addNewUser")
    Call<String> addNewUser(@Body Register register);

    @POST("loginUser")
    Call<Login> LoginUser(@Body Login login);

    @POST("updateMenteeProfile")
    Call<Void> updateMenteeProfile(@Body MenteeProfile menteeProfile);

    @POST("updateMentorProfile")
    Call<Void> updateMentorProfile(@Body MentorProfile mentorProfile);

    @GET("getMenteeProfile/{id}")
    Call<MenteeProfile> getMenteeProfile(@Path("id") int id );

    @GET("getMentorProfile/{id}")
    Call<MentorProfile> getMentorProfiles(@Path("id") int id );

    @GET("getRegister/{id}")
    Call<Register> getRegister(@Path("id") int id );

    @GET("getMenteeList/{id}")
    Call<ArrayList<MenteeList>> getMenteeList(@Path("id") int id );


}
