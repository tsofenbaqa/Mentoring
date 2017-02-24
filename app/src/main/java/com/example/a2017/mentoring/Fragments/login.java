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
import android.widget.Toast;

import com.example.a2017.mentoring.Activitys.MainActivity;
import com.example.a2017.mentoring.Model.Login;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login,container,false);
        signin_btn = (Button) view.findViewById(R.id.signin_btn);
        et_username = (EditText)view.findViewById(R.id.etusername);
        et_password = (EditText) view.findViewById(R.id.etpassword);
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
                    Login login = new Login(username,password);
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
    private void sendLoginToServer(Login login)
    {
        ApiInterfaceRetrofit retrofit = ApiClientRetrofit.getClient().create(ApiInterfaceRetrofit.class);
        Call<String> loginUser = retrofit.LoginUser(login);
        loginUser.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                Log.d( "onResponse: ","done");
                if(response.code() == 200 ||response.code() == 204)
                {
                    Intent i2 = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(i2);

                    Preferences.setLogin(true,getContext());
                }
                else if(response.code() == 404 )
                {
                    Toast.makeText(getActivity(),getText(R.string.user_exist) , Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(),getText(R.string.error_general) , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Toast.makeText(getActivity(),getText(R.string.failure) , Toast.LENGTH_LONG).show();
            }
        });
    }

    }




