package com.example.a2017.mentoring.Model;

/**
 * Created by Pcp on 23/02/2017.
 */

public class Request {
        int    mentorID,menteeID;
        String meetingType;
        String meetingDate;
        String meetingTime;
        String meetingTopic;
        String publicFeedback;
        String privateFeedback;


        public Request(int mentorID, int menteeID,String meetingType, String meetingDate, String meetingTime, String meetingTopic,
                       String publicFeedback, String privateFeedback) {
            super();
            this.mentorID = mentorID;
            this.menteeID = menteeID;
            this.meetingType = meetingType;
            this.meetingDate = meetingDate;
            this.meetingTime = meetingTime;
            this.meetingTopic = meetingTopic;
            this.publicFeedback = publicFeedback;
            this.privateFeedback = privateFeedback;
        }

        public String getMeetingType() {
            return meetingType;
        }


        public void setMeetingType(String meetingType) {
            this.meetingType = meetingType;
        }


        public String getMeetingDate() {
            return meetingDate;
        }


        public void setMeetingDate(String meetingDate) {
            this.meetingDate = meetingDate;
        }


        public String getMeetingTime() {
            return meetingTime;
        }


        public void setMeetingTime(String meetingTime) {
            this.meetingTime = meetingTime;
        }


        public String getMeetingTopic() {
            return meetingTopic;
        }


        public void setMeetingTopic(String meetingTopic) {
            this.meetingTopic = meetingTopic;
        }


        public String getPublicFeedback() {
            return publicFeedback;
        }


        public void setPublicFeedback(String publicFeedback) {
            this.publicFeedback = publicFeedback;
        }


        public String getPrivateFeedback() {
            return privateFeedback;
        }


        public void setPrivateFeedback(String privateFeedback) {
            this.privateFeedback = privateFeedback;
        }

    }
