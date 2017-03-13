package com.example.a2017.mentoring.Services;


import com.example.a2017.mentoring.Model.UserToken;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;
import com.example.a2017.mentoring.Utils.Preferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2017 on 04/02/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    private String token;
    private int id;
    private boolean isLogin;

    @Override
    public void onTokenRefresh()
    {
        token = FirebaseInstanceId.getInstance().getToken();
        Preferences.setMyToken(token,getBaseContext());
        isLogin = Preferences.isLogin(getBaseContext());
        if(isLogin)
        {
            id = Preferences.myId(getBaseContext());
            sendTokenToServer(token,id);

        }
    }

    private void sendTokenToServer(String token,final int id)
    {
        UserToken mytoken = new UserToken(id,token);
        ApiInterfaceRetrofit apiClientRetrofit= ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<UserToken> sendToken = apiClientRetrofit.updateMyToken(mytoken);
        sendToken.enqueue(new Callback<UserToken>()
        {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response)
            {

            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t)
            {

            }
        });
    }

}
