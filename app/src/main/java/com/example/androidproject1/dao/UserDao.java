package com.example.androidproject1.dao;


import android.annotation.SuppressLint;
import android.util.Log;

import com.example.androidproject1.models.*;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

/**
 * Data access layer for User table
 *
 * source
 * https://www.youtube.com/watch?v=741QCymuky4&ab_channel=CamboTutorial
 * https://www.youtube.com/watch?v=wa8OrQ_e76M&ab_channel=CodingWithTea
  */

public class UserDao {

    public DatabaseReference df;
    public FirebaseAuth fa;

    public UserDao() {

        df = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());
        fa = FirebaseAuth.getInstance();
    }

    // Method to retrieve user's UID
    public String getCurrentUserUid() {

        String uid = fa.getCurrentUser().getUid();
        return uid;
    }

    // Method to retrieve user information by UID
    public Task<DataSnapshot> getUserByUid(String uid) {

        return df.child(uid).get();
    }

    // Method to retrieve user information by username
    public Task<DataSnapshot> getUserByUsername(String username) {

        return df.orderByChild("username").equalTo(username).get();

    }

    // Method to retrieve all user information
    public Task<DataSnapshot> getAllUsers() {

        return df.get();
    }

    // Method to retrieve user information by score (top 3)
    public Task<DataSnapshot> getTopThreeUsers(){

        return df.orderByChild("score").limitToLast(3).get();
    }

    // Method to retrieve user information sorted by score
    public Task<DataSnapshot> getAllUsersOrderedByScore(){

        return df.orderByChild("score").get();
    }



}
