package com.example.androidproject1.dao;

import com.example.androidproject1.models.Challenge;
import com.example.androidproject1.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChallengeDao {

    private DatabaseReference df;

    public ChallengeDao() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        df = db.getReference(Challenge.class.getSimpleName());

    }

    public Task<Void> addChallege(Challenge c) {
        // username as key
        return df.push().setValue(c);
    }
}
