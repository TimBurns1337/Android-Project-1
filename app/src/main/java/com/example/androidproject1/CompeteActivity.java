package com.example.androidproject1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.androidproject1.dao.ChallengeDao;
import com.example.androidproject1.dao.UserDao;
import com.example.androidproject1.models.Challenge;
import com.example.androidproject1.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class CompeteActivity  extends AppCompatActivity {

    private LinearLayout ll;
    private FirebaseAuth firebaseAuth;
    private String userEmail; // using email to identify users

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compete);

        // grab random users from the user table and show each one

        ll = (LinearLayout) findViewById(R.id.content_container);

        UserDao userDao = new UserDao();
        userDao.getAllUsers().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                generateFriendListButtons(dataSnapshot);

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        // TODO check for a way to get the username? right now it's using uid
        userEmail = firebaseAuth.getCurrentUser().getUid();

    }

    public void generateFriendListButtons(DataSnapshot dataSnapshot){

        ArrayList<String> friendList = new ArrayList<String>();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(params.leftMargin, 5, params.topMargin, 5);

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            User friend = ds.getValue(User.class);
            Log.d("CompeteActivity", friend.getUsername());

            AppCompatButton acp = new AppCompatButton(this);
            acp.setTag(friend.getEmail());
            acp.setText(friend.getUsername());
            acp.setBackgroundResource(R.drawable.rectangle_25_ek1_shape);
            acp.setLayoutParams(params);
            acp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    friendButtonClick((AppCompatButton) view);
                    // after handling the click, remove button from list on this view
                    // note: the names will show up again if you go back and forth
                    ll.removeView(view);
                    
                }
            });

            ll.addView(acp);

        }

    }

    // Challenge created
    public void friendButtonClick(AppCompatButton btn) {

        // TODO check if there's a challenge already for this user and the friend that was clicked
        ChallengeDao cd = new ChallengeDao();
        Challenge c = new Challenge(userEmail, String.valueOf(btn.getText()));
        cd.addChallege(c);



    }

}
