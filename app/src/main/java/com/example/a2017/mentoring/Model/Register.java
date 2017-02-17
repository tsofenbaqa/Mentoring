package com.example.a2017.mentoring.Model;

public class Register {

    private String Email;
    private String Password;
    private String FirstName;
    private String LastName;
    private String Type;
    private String Phone;



    public Register(String email ,String password, String firstName, String lastName, String type, String phone){

        Email=email;
        Password=password;
        FirstName=firstName;
        LastName=lastName;
        Type=type;
        Phone=phone;
    }

    public Register()
    {

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



    public void setPassword(String password) {
        Password = password;
    }



    public String getFirstName() {
        return FirstName;
    }



    public void setFirstName(String firstName) {
        FirstName = firstName;
    }



    public String getLastName() {
        return LastName;
    }



    public void setLastName(String lastName) {
        LastName = lastName;
    }



    public String getType() {
        return Type;
    }



    public void setType(String type) {
        Type = type;
    }



    public String getPhone() {
        return Phone;
    }



    public void setPhone(String phone) {
        Phone = phone;
    }




}