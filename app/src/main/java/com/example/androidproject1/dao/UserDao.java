package com.example.androidproject1.dao;


import com.example.androidproject1.models.*;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * source
 * https://www.youtube.com/watch?v=741QCymuky4&ab_channel=CamboTutorial
 * https://www.youtube.com/watch?v=wa8OrQ_e76M&ab_channel=CodingWithTea
  */

public class UserDao {

    private DatabaseReference df;

    public UserDao() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        df = db.getReference(User.class.getSimpleName());

    }

    public Task<Void> add(User u) {
        // username as key
        return df.child(u.getUsername()).setValue(u);
    }

    public Task<DataSnapshot> getUserByUsername(String username) {

        return df.child(username).get();
    }

    // return max number of random usernames
    public Task<DataSnapshot> getAllUsers() {

        return df.get();
    }

}
