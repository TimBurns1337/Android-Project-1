package com.example.androidproject1;

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

import com.bumptech.glide.Glide;
import com.example.androidproject1.models.Workout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TimerActivity extends AppCompatActivity {

    Button timerBtn;
    VideoView videoView;
    DatabaseReference database;
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String time = intent.getStringExtra("time");
        timerBtn = findViewById(R.id.timerBtn);
        timer = findViewById(R.id.tvTimer);
        videoView = findViewById(R.id.mainVideo);
        database = FirebaseDatabase.getInstance().getReference("WorkoutExercises");

        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int seconds = Integer.parseInt(time) * 1000;
                CountDownTimer tims = new CountDownTimer(seconds, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timer.setText("seconds remaining: " + millisUntilFinished / 1000);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        timer.setText("done!");
                    }

                }.start();

            }
        });


        database.addListenerForSingleValueEvent(new ValueEventListener() {
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

}