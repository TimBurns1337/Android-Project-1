package com.example.androidproject1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.androidproject1.dao.ChallengeDao;
import com.example.androidproject1.dao.UserDao;
import com.example.androidproject1.models.Challenge;
import com.example.androidproject1.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChallengeRequestActivity extends AppCompatActivity {

    private LinearLayout ll;
    private UserDao userDao;
    private ChallengeDao challengeDao;
    //private String username;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_requests);

        ll = (LinearLayout) findViewById(R.id.content_container);

        // initialize the daos
        userDao = new UserDao();
        challengeDao = new ChallengeDao();

        // get the information of current user
        uid = userDao.getCurrentUserUid();

        challengeDao.getChallengeByUserUid(uid).addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                generateRequestListButtons(dataSnapshot);
            }
        });

    }

    public void generateRequestListButtons(DataSnapshot requestsSnapshot) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.5f

        );

        LinearLayout.LayoutParams paramsTrash = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.5f

        );
        params.setMargins(params.leftMargin, 5, params.topMargin, 5);

        Challenge c = requestsSnapshot.getValue(Challenge.class);

        // TODO
        if (c.getReceived() == null) {
            Toast.makeText(getApplicationContext(), "No one sent you any challenges!", Toast.LENGTH_LONG).show();
            return;
        }


        for (HashMap<String, String> content : c.getReceived().values()) {

            LinearLayout llInner = new LinearLayout(this);
            LinearLayout.LayoutParams layoutForInner = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llInner.setLayoutParams(layoutForInner);

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.weight = 0.2f;
            params1.setMargins(5, 5, 5, 5);
            AppCompatButton button1 = new AppCompatButton(this);
            button1.setTag(content.get("username"));
            button1.setText(content.get("username"));
            button1.setBackgroundResource(R.drawable.rectangle_24_shape);
            button1.setLayoutParams(params1);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Challenge accepted!", Toast.LENGTH_LONG).show();
                    // TODO play the video and handle completed workout
                    handleButtonClick((AppCompatButton) view);
                    ll.removeView(llInner);
                }
            });
            llInner.addView(button1);

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params2.weight = 0.8f;
            //params2.setMargins(5, 5, 5, 5);
            AppCompatButton button2 = new AppCompatButton(this);
            button2.setTag(content.get("username"));
            button2.setText("Clear");
            button2.setBackgroundResource(R.drawable.clear);
            button2.setLayoutParams(params2);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Request has been removed.", Toast.LENGTH_LONG).show();
                    handleButtonClick((AppCompatButton) view);
                    ll.removeView(llInner);
                }
            });

            llInner.addView(button2);

            ll.addView(llInner);


        }
    }

    public void handleButtonClick(AppCompatButton btn) {
        String receivedUsername = btn.getTag().toString();

        userDao.getUserByUsername(receivedUsername).addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String receivedUid = dataSnapshot.getChildren().iterator().next().getKey();
                ChallengeDao cd = new ChallengeDao();
                cd.removeChallenge(uid, receivedUid);

            }
        });

    }

}
