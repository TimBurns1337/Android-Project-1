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

    public String getCurrentUserUid() {

        String uid = fa.getCurrentUser().getUid();
        return uid;
    }

    public Task<DataSnapshot> getUserByUid(String uid) {

        return df.child(uid).get();
    }


    public Task<DataSnapshot> getUserByUsername(String username) {

        return df.orderByChild("username").equalTo(username).get();

    }

    // return max number of random usernames
    public Task<DataSnapshot> getAllUsers() {

        return df.get();
    }



}
