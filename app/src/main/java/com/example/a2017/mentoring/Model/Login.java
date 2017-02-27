package com.example.a2017.mentoring.Model;

/**
 * Created by android on 22/02/2017.
 */

public class Login
{
    private String Email;
    private String Password;
    private String Type;
    private int Id;


    public Login()
    {
    }

    public Login(String type, int id) {
        Type = type;
        Id = id;
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


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
