package com.example.a2017.mentoring.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.a2017.mentoring.R;


public class login extends AppCompatActivity {

   Button signin_btn;
    EditText et_username, et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        signin_btn = (Button) findViewById(R.id.signin_btn);
        et_username = (EditText) findViewById(R.id.etusername);
        et_password = (EditText) findViewById(R.id.etpassword);


        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if (username.equals("admin") && (password.equals("1234"))) {

                    Toast.makeText(login.this, "login success", Toast.LENGTH_LONG).show();
                    Intent i2 = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(i2);

                } else {
                    Toast.makeText(login.this, "Wrong username Or Password", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
