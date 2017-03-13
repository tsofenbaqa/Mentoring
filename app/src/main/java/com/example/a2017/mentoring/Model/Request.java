package com.example.a2017.mentoring.Model;

/**
 * Created by Pcp on 23/02/2017.
 */

//public class Request {
//        //int    mentorID;
//        int    menteeID;
//        String meetingTitle;
//      //  String meetingType;
//        String meetingDate_time;
////        String meetingTime;
//        String meetingTopic;
//        String meetingType;
//        String date;
//
//    public String getMeetingType() {
//        return meetingType;
//    }
//
//    public void setMeetingType(String meetingType) {
//        this.meetingType = meetingType;
//    }
////        String publicFeedback;
////        String privateFeedback;
//
//
//
//
//    public Request(String title,String topic,String date){
//        this.meetingTitle = title;
//        this.meetingTopic = topic;
//       // this.meetingDate  = date;
//    }
////    public Request(int mentorID, int menteeID, String meetingType,String meetingTitle, String meetingDate, String meetingTime, String meetingTopic,
////                   String publicFeedback, String privateFeedback) {
////            super();
////          //  this.mentorID = mentorID;
////            this.menteeID = menteeID;
////          //  this.meetingType = meetingType;
////           // this.meetingDate = meetingDate;
////          //  this.meetingTime = meetingTime;
////            this.meetingTitle = meetingTitle;
////            this.meetingTopic = meetingTopic;
////          //  this.publicFeedback = publicFeedback;
////          //  this.privateFeedback = privateFeedback;
////
////        }
//    public Request(int menteeID,String meetingTitle, String meetingTopic,String meetingType) {
//        super();
//        this.menteeID = menteeID;
//        this.meetingTitle = meetingTitle;
//        this.meetingTopic = meetingTopic;
//        this.meetingType = meetingType;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
////        public String getMeetingType() {
////            return meetingType;
////        }
////        public void setMeetingType(String meetingType) {
////            this.meetingType = meetingType;
////        }
////        public String getMeetingDate() {
////            return meetingDate;
////        }
////        public void setMeetingDate(String meetingDate) {
////            this.meetingDate = meetingDate;
////        }
////        public String getMeetingTime() {
////            return meetingTime;
////        }
////        public void setMeetingTime(String meetingTime) {
////            this.meetingTime = meetingTime;
////        }
//        public String getMeetingTopic() {
//            return meetingTopic;
//        }
//        public void setMeetingTopic(String meetingTopic) {
//            this.meetingTopic = meetingTopic;
//        }
//
//    public String getMeetingDate_time() {
//        return meetingDate_time;
//    }
//
//    public void setMeetingDate_time(String meetingDate_time) {
//        this.meetingDate_time = meetingDate_time;
//    }
//
//    public int getMenteeID() {
//        return menteeID;
//    }
//
//    public void setMenteeID(int menteeID) {
//        this.menteeID = menteeID;
//    }
//
////        public String getPublicFeedback() {
////            return publicFeedback;
////        }
////        public void setPublicFeedback(String publicFeedback) {this.publicFeedback = publicFeedback;}
////        public String getPrivateFeedback() {
////            return privateFeedback;
////        }
//       // public void setPrivateFeedback(String privateFeedback) {this.privateFeedback = privateFeedback;}
//        public String getMeetingTitle() {return meetingTitle;}
//        public void setMeetingTitle(String meetingTitle) {this.meetingTitle = meetingTitle;}
//
//    }

public class Request {

//    int mentorID;
    int    menteeID;
    String meetingType;
    String meetingDate_time;
    String meetingTopic;
    String date;


    public Request(int menteeID ,String meetingType, String meetingDate_time, String meetingTopic) {
        super();
        this.menteeID = menteeID;
        this.meetingType = meetingType;
        this.meetingDate_time = meetingDate_time;
        this.meetingTopic = meetingTopic;

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
