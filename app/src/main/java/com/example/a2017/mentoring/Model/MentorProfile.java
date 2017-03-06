package com.example.a2017.mentoring.Model;

import java.io.Serializable;

/**
 * Created by 2017 on 26/02/2017.
 */

public class MentorProfile implements Serializable
{
    private int userId;
    private String fname;
    private String lname;
    private long id ;
    private String gender;
    private String phone;
    private String email;
    private int mentor;
    private String major;
    private String address;
    private String company;
    private byte[] imageFile;


    public MentorProfile() {
    }
    public MentorProfile(int user,String fname, String lname, long id,
                         String gender, String phone, String email,
                         int mentor, String major, String address, byte[] imageFile , String company) {

        this.userId=user;
        this.fname = fname;
        this.lname = lname;
        this.id = id;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.mentor = mentor;
        this.major = major;
        this.address = address;
        this.company = company;
        this.imageFile = imageFile;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setMentor(int mentor) {
        this.mentor = mentor;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public String getCompany() {return company;}
    public void setCompany(String company) {this.company = company;}
    public void setEmail(String email) {
        this.email = email;
    }
    public int getMentor() {
        return mentor;
    }
    public void setmentor(int mentor)
    {
        this.mentor=mentor;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public byte[] getImageFile() {
        return imageFile;
    }
    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }

}