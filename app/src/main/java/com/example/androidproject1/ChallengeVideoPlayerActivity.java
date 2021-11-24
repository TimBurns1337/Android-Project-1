package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.example.androidproject1.dao.ChallengeDao;
import com.example.androidproject1.dao.UserDao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class ChallengeVideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;

    private UserDao userDao;
    private ChallengeDao challengeDao;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_video_player);

        // initialize the daos
        userDao = new UserDao();
        challengeDao = new ChallengeDao();

        // get the information of current user
        uid = userDao.getCurrentUserUid();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        videoView = findViewById(R.id.mainVideo);
        // short video
        videoView.setVideoURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/calisthenics-83123.appspot.com/o/Side_Kicks.mp4?alt=media&token=beaabc94-0768-4407-b2a2-bf5372a7ff0e"));

        // long video
        //videoView.setVideoURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/calisthenics-83123.appspot.com/o/Challenge_FB_Workout.mp4?alt=media&token=046b20b8-b6ac-4bda-aa6c-c3b4ff9b4250"));

        // disabled video controls
        //videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.setBackgroundTintMode(PorterDuff.Mode.DARKEN);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                // add point to user
                UserDao ud = new UserDao();

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

                // go back to previous page
                finish();
            }
        });

        videoView.start();

    }
}