package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class TimerActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        videoView = findViewById(R.id.mainVideo);
        videoView.setVideoURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/calisthenics-83123.appspot.com/o/Side_Kicks.mp4?alt=media&token=beaabc94-0768-4407-b2a2-bf5372a7ff0e"));
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();

    }
}