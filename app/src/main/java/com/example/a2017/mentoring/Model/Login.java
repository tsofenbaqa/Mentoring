package com.example.a2017.mentoring.Model;

/**
 * Created by android on 22/02/2017.
 */

public class Login
{
    private String Email;
    private String Password;


    public Login()
    {
    }

    public Login(String email, String password)
    {
        Email = email;
        Password = password;
    }
    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }




}