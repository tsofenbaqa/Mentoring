package com.example.a2017.mentoring.Model;

import java.io.Serializable;

/**
 * Created by 2017 on 26/02/2017.
 */

public class MenteeProfile implements Serializable
{
    private String fname;
    private String lname;
    private long id ;
    private String gender;
    private String phone;
    private String email;
    private String mentor;
    private String major;
    private String semesterLeft;
    private String educationStatus;
    private String address;
    private String note ;
    private String tsofenCourse;
    private String courseRegistrationDate;
    private byte[] imageFile;
    private byte[] resumeFile;
    private byte[] gradeSheetFile;

    public MenteeProfile() {
    }

    public MenteeProfile(String fname, String lname, long id, String gender, String phone, String email, String mentor, String major, String semesterLeft, String educationStatus, String address, String note, String tsofenCourse, String courseRegistrationDate, byte[] imageFile, byte[] resumeFile, byte[] gradeSheetFile) {
        this.fname = fname;
        this.lname = lname;
        this.id = id;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.mentor = mentor;
        this.major = major;
        this.semesterLeft = semesterLeft;
        this.educationStatus = educationStatus;
        this.address = address;
        this.note = note;
        this.tsofenCourse = tsofenCourse;
        this.courseRegistrationDate = courseRegistrationDate;
        this.imageFile = imageFile;
        this.resumeFile = resumeFile;
        this.gradeSheetFile = gradeSheetFile;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSemesterLeft() {
        return semesterLeft;
    }

    public void setSemesterLeft(String semesterLeft) {
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

    public String getTsofenCourse() {
        return tsofenCourse;
    }

    public void setTsofenCourse(String tsofenCourse) {
        this.tsofenCourse = tsofenCourse;
    }

    public String getCourseRegistrationDate() {
        return courseRegistrationDate;
    }

    public void setCourseRegistrationDate(String courseRegistrationDate) {
        this.courseRegistrationDate = courseRegistrationDate;
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
}
