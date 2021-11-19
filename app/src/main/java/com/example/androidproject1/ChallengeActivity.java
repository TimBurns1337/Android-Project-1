package com.example.androidproject1;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.androidproject1.dao.ChallengeDao;
import com.example.androidproject1.dao.UserDao;
import com.example.androidproject1.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChallengeActivity extends AppCompatActivity {

    private LinearLayout ll;
    private UserDao userDao;
    private ChallengeDao challengeDao;
    private String username;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        ll = (LinearLayout) findViewById(R.id.content_container);

        // initialize the daos
        userDao = new UserDao();
        challengeDao = new ChallengeDao();

        // get the information of current user
        uid = userDao.getCurrentUserUid();

        userDao.getUserByUid(uid).addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                username = u.getUsername();
            }
        });

        userDao.getAllUsers().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot ds1) {

                // filter the users, only show those without mapping
                challengeDao.getChallegeByUser(uid).addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot ds2) {

                        generateFriendListButtons(ds1, ds2);
                    }
                });

            }
        });
    }

    public void generateFriendListButtons(DataSnapshot userSnapshot, DataSnapshot challengeSnapshot){

        // build a set for the sent challenges,
        Set sentChallenges = new HashSet();

        for (DataSnapshot ds : challengeSnapshot.child("sent").getChildren()) {
            sentChallenges.add(ds.getKey());
        }

        ArrayList<String> friendList = new ArrayList<String>();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(params.leftMargin, 5, params.topMargin, 5);

        for (DataSnapshot ds : userSnapshot.getChildren()) {
            // dont show current user and dont include existing sent challenges in the list
            if (ds.getKey().equals(uid) || sentChallenges.contains(ds.getKey())) {
                continue;
            }

            User friend = ds.getValue(User.class);
            AppCompatButton acp = new AppCompatButton(this);

            acp.setText(friend.getUsername());
            acp.setBackgroundResource(R.drawable.rectangle_25_ek1_shape);
            acp.setLayoutParams(params);
            acp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    friendButtonClick((AppCompatButton) view);
                    // after handling the click, remove button from list on this view
                    ll.removeView(view);
                    
                }
            });

            ll.addView(acp);

        }

    }

    // Challenge created
    public void friendButtonClick(AppCompatButton btn) {

        String friendUsername = btn.getText().toString();

        userDao.getUserByUsername(friendUsername).addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String friendUid = dataSnapshot.getChildren().iterator().next().getKey();
                new ChallengeDao().addChallege(uid, friendUid, username);

            }
        });

    }

}
