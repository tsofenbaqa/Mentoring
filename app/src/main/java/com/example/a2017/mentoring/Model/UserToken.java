package com.example.a2017.mentoring.Model;

/**
 * Created by 2017 on 07/02/2017.
 */
public class UserToken
{
    private int id;
    private String token;

    public UserToken()
    {
    }

    public UserToken(int id, String token)
    {
        this.id = id;
        this.token = token;
    }

    public int getPhoneNumber()
    {
        return id;
    }

    public void setPhoneNumber(int id)
    {
        this.id = id;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }
}
