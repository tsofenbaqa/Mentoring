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
    private String gender;
    private String phone;
    private String email;
    private String position;
    private String experience;
    private String address;
    private String note ;
    private String company;
    private byte[] imageFile;


    public MentorProfile(int userId, String fname, String lname, String gender, String phone, String email,
                         String position, String experience, String address, String company, String note, byte[] imageFile) {
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.experience = experience;
        this.address = address;

        this.note = note;
        this.company = company;
        this.imageFile = imageFile;
    }

    public MentorProfile(){

    }


    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
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
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public byte[] getImageFile() {
        return imageFile;
    }
    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

}