package com.example.androidproject1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.androidproject1.dao.ChallengeDao;
import com.example.androidproject1.dao.UserDao;
import com.example.androidproject1.models.Challenge;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

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

        if (c == null || c.getReceived() == null) {
            Toast.makeText(getApplicationContext(), "No one sent you any challenges!", Toast.LENGTH_LONG).show();
            return;
        }

        // build the buttons dynamically
        for (HashMap<String, String> content : c.getReceived().values()) {

            LinearLayout llInner = new LinearLayout(this);
            LinearLayout.LayoutParams layoutForInner = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            llInner.setLayoutParams(layoutForInner);

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.weight = 0.1f;
            params1.setMargins(5, 5, 5, 5);
            AppCompatButton button1 = new AppCompatButton(this);

            Drawable dp = getResources().getDrawable(R.drawable.vector_burn);
            dp.setBounds(0, 0, 80, 80);

            button1.setCompoundDrawables(dp, null, null, null);
            button1.setTag(content.get("username"));
            button1.setText(content.get("username"));
            button1.setTextColor(Color.DKGRAY);
            button1.setTextSize(16);
            button1.setBackgroundResource(R.drawable.rectangle_ek1_shape);
            button1.setLayoutParams(params1);
            button1.setPadding(100, 0, 0, 0);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // start challenge and handle the button click
                    Toast.makeText(getApplicationContext(), "Challenge accepted!", Toast.LENGTH_LONG).show();
                    startChallenge();
                    handleButtonClick((AppCompatButton) view);
                    ll.removeView(llInner);
                }
            });
            llInner.addView(button1);

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params2.weight = 0.9f;
            AppCompatButton button2 = new AppCompatButton(this);
            button2.setTag(content.get("username"));
            button2.setText("X");
            button2.setTextColor(Color.RED);
            button2.setLetterSpacing(1);
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

    public void startChallenge() {
        // Challenge is currently set to climbers
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("name" , "Climbers");
        intent.putExtra("time" , "60");
        intent.putExtra("isChallenge" , true);
        startActivity(intent);

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
