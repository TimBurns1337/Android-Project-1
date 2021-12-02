package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.androidproject1.dao.UserDao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class TimerActivity extends AppCompatActivity {

    //global fields
    Button timerBtn;
    VideoView videoView;
    DatabaseReference fbDatabaseRef;
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        //getting intent and data
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String time = intent.getStringExtra("time");
        Boolean isChallenge = intent.getBooleanExtra("isChallenge", false);

        //connecting UI to the fields
        timerBtn = findViewById(R.id.timerBtn);
        timer = findViewById(R.id.tvTimer);
        videoView = findViewById(R.id.mainVideo);
        fbDatabaseRef = FirebaseDatabase.getInstance().getReference("WorkoutExercises");

        //setting the timer and video
        setTheTimer(time, isChallenge);
        getTheExerciseVideo(name, time);
    }

    private void getTheExerciseVideo(String name, String time) {

        //creating the listener to get the video data
        fbDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if (snap.child("name").getValue(String.class).equals(name)) {
                        String url = snap.child("video").getValue(String.class);
                        timer.setText(time);
                        videoView.setVideoURI(Uri.parse(url));
                        videoView.setMediaController(new MediaController(TimerActivity.this));
                        videoView.requestFocus();
                        videoView.start();
                        //video loop
                        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.setLooping(true);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }

    private void setTheTimer(String time, Boolean isChallenge) {
        //creating the listener
        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerBtn.setText("Progress");
                timerBtn.setEnabled(false);
                int seconds = Integer.parseInt(time) * 1000;
                CountDownTimer tims = new CountDownTimer(seconds, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timer.setText("seconds remaining: " + millisUntilFinished / 1000);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        timer.setText("done!");
                        timerBtn.setText("Start");
                        // if this view was from Challenge, update the score if needed
                        if (isChallenge) {
                            // get the information of current user
                            UserDao ud = new UserDao();
                            String uid = ud.getCurrentUserUid();

                            ud.df.child(uid).child("score").runTransaction(new Transaction.Handler() {
                                @NonNull
                                @Override
                                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                    if (currentData.getValue() == null) {
                                        currentData.setValue(1);
                                    } else {
                                        currentData.setValue((Long) currentData.getValue() + 1);
                                    }

                                    return Transaction.success(currentData);
                                }

                                @Override
                                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                                    if (error != null) {
                                        System.out.println("Firebase counter increment failed.");
                                    } else {
                                        System.out.println("Firebase counter increment succeeded.");
                                    }

                                }
                            });

                        }

                    }

                }.start();

            }
        });
    }

}