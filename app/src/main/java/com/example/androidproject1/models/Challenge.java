package com.example.androidproject1.models;

import java.io.Serializable;
import java.util.HashMap;

public class Challenge implements Serializable {

    // map of uid that challenges were sent to and received from
    HashMap<String, String> sent;
    HashMap<String, HashMap<String, String>> received;
    String userUid;

    public Challenge(){}

    public Challenge(String u, HashMap<String, String> s, HashMap<String,  HashMap<String, String>> r) {
        this.userUid = u;
        this.sent = s;
        this.received = r;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public HashMap<String, String> getSent() {
        return sent;
    }

    public void setSent(HashMap<String, String> sent) {
        this.sent = sent;
    }

    public HashMap<String,  HashMap<String, String>> getReceived() {
        return received;
    }

    public void setReceived(HashMap<String,  HashMap<String, String>> received) {
        this.received = received;
    }

}
