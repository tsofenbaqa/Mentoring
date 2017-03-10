package com.example.a2017.mentoring.Model;

/**
 * Created by Pcp on 10/03/2017.
 */

public class MenteeList {

    private String contactName;
    private int userId;

    public MenteeList(String contactName, int userId){
        this.contactName = contactName;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
