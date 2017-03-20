package com.example.a2017.mentoring.Model;


import java.io.Serializable;

public class Request implements Serializable{

    int    id;
    int    mentorID;
    int    menteeID;
    String meetingType;
    String meetingDate_time;
    String meetingTopic;
    String mentor_notes;
    String mentee_notes;
    String mentor_private;
    String mentee_private;


    public Request(int id,int mentorID, int menteeID,
                   String meetingType, String meetingDate_time,
                   String meetingTopic,
                   String mentor_notes, String mentee_notes,
                   String mentor_private, String mentee_private) {

        this.id=id;
        this.mentorID = mentorID;
        this.menteeID = menteeID;
        this.meetingType = meetingType;
        this.meetingDate_time = meetingDate_time;
        this.meetingTopic = meetingTopic;
        this.mentor_notes = mentor_notes;
        this.mentee_notes = mentee_notes;
        this.mentor_private = mentor_private;
        this.mentee_private = mentee_private;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getMentorID() {return mentorID;}
    public void setMentorID(int mentorID) {this.mentorID = mentorID;}

    public String getMentor_notes() {
        return mentor_notes;
    }

    public void setMentor_notes(String mentor_notes) {
        this.mentor_notes = mentor_notes;
    }

    public String getMentee_notes() {
        return mentee_notes;
    }

    public void setMentee_notes(String mentee_notes) {
        this.mentee_notes = mentee_notes;
    }

    public String getMentor_private() {
        return mentor_private;
    }

    public void setMentor_private(String mentor_private) {
        this.mentor_private = mentor_private;
    }

    public String getMentee_private() {
        return mentee_private;
    }

    public void setMentee_private(String mentee_private) {
        this.mentee_private = mentee_private;
    }

    public String getMeetingDate_time() {
        return meetingDate_time;
    }

    public void setMeetingDate_time(String meetingDate_time) {
        this.meetingDate_time = meetingDate_time;
    }

    public void setMenteeID(int menteeID) {
        this.menteeID = menteeID;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public String getMeetingTopic() {
        return meetingTopic;
    }

    public void setMeetingTopic(String meetingTopic) {
        this.meetingTopic = meetingTopic;
    }

    public int getMenteeID() {
        return menteeID;
    }

}