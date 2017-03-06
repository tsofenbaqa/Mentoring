package com.example.a2017.mentoring.Model;

import java.io.Serializable;

/**
 * Created by 2017 on 26/02/2017.
 */

public class MenteeProfile implements Serializable
{

    private int userId;
    private String fname;
    private String lname;
    private String gender;
    private String phone;
    private String email;
    private String major;
    private int semesterLeft;
    private String educationStatus;
    private String acadimicinstitution;
    private String address;
    private String note ;
    private int tsofenCourse;
    private int avg;
    private byte[] imageFile;
    private byte[] resumeFile;
    private byte[] gradeSheetFile;
    public MenteeProfile() {
    }
    public MenteeProfile(int user,String fname, String lname, String gender, String phone, String email, String major, int semesterLeft, String educationStatus, String address, String note, int tsofenCourse,String Acadimicinstitution, byte[] imageFile, byte[] resumeFile, byte[] gradeSheetFile , int avg) {

        this.avg = avg;
        this.userId=user;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.major = major;
        this.semesterLeft = semesterLeft;
        this.educationStatus = educationStatus;
        this.address = address;
        this.note = note;
        this.tsofenCourse = tsofenCourse;
        this.imageFile = imageFile;
        this.resumeFile = resumeFile;
        this.gradeSheetFile = gradeSheetFile;
        this.acadimicinstitution=Acadimicinstitution;
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

    public String getAcadimicinstitution() {
        return acadimicinstitution;
    }
    public void setAcadimicinstitution(String acadimicinstitution) {
        this.acadimicinstitution = acadimicinstitution;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public int getSemesterLeft() {
        return semesterLeft;
    }
    public void setSemesterLeft(int semesterLeft) {
        this.semesterLeft = semesterLeft;
    }
    public String getEducationStatus() {
        return educationStatus;
    }
    public void setEducationStatus(String educationStatus) {
        this.educationStatus = educationStatus;
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
    public int getTsofenCourse() {
        return tsofenCourse;
    }
    public void setTsofenCourse(int tsofenCourse) {
        this.tsofenCourse = tsofenCourse;
    }
    public byte[] getImageFile() {
        return imageFile;
    }
    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }
    public byte[] getResumeFile() {
        return resumeFile;
    }
    public void setResumeFile(byte[] resumeFile) {
        this.resumeFile = resumeFile;
    }
    public byte[] getGradeSheetFile() {
        return gradeSheetFile;
    }
    public void setGradeSheetFile(byte[] gradeSheetFile) {
        this.gradeSheetFile = gradeSheetFile;
    }

    public int getAvg()
    {
        return avg;
    }
    public void setAvg(int avg)
    {
        this.avg = avg;
    }
}