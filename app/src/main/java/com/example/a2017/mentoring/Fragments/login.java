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
import com.example.a2017.mentoring.Model.Register;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.ApiClientRetrofit;
import com.example.a2017.mentoring.RetrofitApi.ApiInterfaceRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class login extends Fragment {

   Button signin_btn;
    EditText et_username, et_password;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login,container,false);
        signin_btn = (Button) view.findViewById(R.id.signin_btn);
        et_username = (EditText)view.findViewById(R.id.etusername);
        et_password = (EditText) view.findViewById(R.id.etpassword);


        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if (username.equals("admin") && (password.equals("1234"))) {

                    Intent i2 = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(i2);

                } else  if (!et_username.equals("admin")){
                    et_username.setError("wrong username");

                }
                else {
                    et_password.setError("wrong password");
                }



            }
        });
        return view;
    }

    }




