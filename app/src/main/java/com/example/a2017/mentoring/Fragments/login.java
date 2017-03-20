package com.example.a2017.mentoring.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a2017.mentoring.Activitys.MainActivity;
import com.example.a2017.mentoring.Model.Login;
import com.example.a2017.mentoring.Model.UserToken;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;
import com.example.a2017.mentoring.Utils.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class login extends Fragment
{

    Button signin_btn;
    EditText et_username, et_password;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login,container,false);
        signin_btn = (Button) view.findViewById(R.id.signin_btn);
        et_username = (EditText)view.findViewById(R.id.etusername);
        et_password = (EditText) view.findViewById(R.id.etpassword);
        progressBar = (ProgressBar)view.findViewById(R.id.login_progressBar);
        signinBtn();
        return view;
    }

    private void signinBtn()
    {
        signin_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if(! username.isEmpty() && !password.isEmpty())
                {
                    Login login = new Login(username,password,false);
                    sendLoginToServer(login);
                }
                else if(username.isEmpty()){
                    et_username.setError(getText(R.string.user_empty));
                }
                else {
                et_password.setError(getText(R.string.pass_empty));
                }

            }
        });
    }
    private void sendLoginToServer(Login login) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<Login> loginUser = retrofit.LoginUser(login);
        loginUser.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response)
            {
                Log.d("onResponse: ", "done");
                if (response.code() == 200 || response.code() == 204)
                {
                    Intent i2 = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(i2);
                    String token = Preferences.getMyToken(getContext());
                    Log.d("token:",token);
                    int id = response.body().getId();
                    sendTokenToServer(token,id);
                    Preferences.setLogin(true, getContext());
                    Preferences.setMyId(id, getContext());
                    boolean isProfileUpdated = response.body().isProfileUpdated();
                    Preferences.setProfileUpdate(isProfileUpdated, getContext());
                    if (response.body().getType().equals("mentee")) {
                        Preferences.setMentee(true, getContext());
                    } else {
                        Preferences.setMentee(false, getContext());
                    }
                } else if (response.code() == 404) {
                    Toast.makeText(getActivity(), getText(R.string.userNOTexist), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), getText(R.string.error_general), Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getActivity(), getText(R.string.failure), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

        private void sendTokenToServer(String token,final int id)
        {
            UserToken mytoken = new UserToken(id,token);
            ApiInterfaceRetrofit apiClientRetrofit= ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
            Call<UserToken> sendToken = apiClientRetrofit.submitMyToken(mytoken);
            sendToken.enqueue(new Callback<UserToken>()
            {
                @Override
                public void onResponse(Call<UserToken> call, Response<UserToken> response)
                {
                    if(response.code()==200 || response.code() == 204)
                    {

                    }
                    else
                    {

                    }
                }

                @Override
                public void onFailure(Call<UserToken> call, Throwable t)
                {

                }
            });
        }
    }






