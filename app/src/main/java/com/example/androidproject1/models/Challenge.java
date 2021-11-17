package com.example.androidproject1.models;

import java.io.Serializable;

public class Challenge implements Serializable {

    private String user; // user's UserID
    private String friend; // friend's UserID

    public Challenge(){}

    public Challenge(String u, String f) {
        this.user = u;
        this.friend = f;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }
}
