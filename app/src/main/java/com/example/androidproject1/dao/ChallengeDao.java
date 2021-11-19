package com.example.androidproject1.dao;

import android.util.Log;

import com.example.androidproject1.models.Challenge;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class ChallengeDao {

    private DatabaseReference df;

    public ChallengeDao() {

        df = FirebaseDatabase.getInstance().getReference(Challenge.class.getSimpleName());

    }

    public void addChallege(String userUid, String friendUid, String friendUsername) {

        // handle sent challenges
        HashMap sentMap = new HashMap();

        sentMap.put(friendUid, friendUid); // TODO: what to put as value??
        df.child(userUid).child("sent").updateChildren(sentMap);

        // handle received challenges
        HashMap receivedMap = new HashMap();
        HashMap challengeContentMap = new HashMap();
        challengeContentMap.put("url", "random url");
        challengeContentMap.put("username", friendUsername);
        receivedMap.put(userUid, challengeContentMap);
        df.child(friendUid).child("received").updateChildren(receivedMap);

    }

    public Task<DataSnapshot> getChallengeByUserUid(String userUid) {
        return df.child(userUid).get();
    }

    public void removeChallenge(String userUid, String friendUid) {
        Log.d("userid", userUid);
        Log.d("friendUid", friendUid);
        // remove from current user's received
        df.child(userUid).child("received").child(friendUid).removeValue();

        // removed from senders sent
        df.child(friendUid).child("sent").child(userUid).removeValue();
    }

    //
    public Task<DataSnapshot> getChallegeByUser(String uid) {
        return df.child(uid).get();
    }
}
