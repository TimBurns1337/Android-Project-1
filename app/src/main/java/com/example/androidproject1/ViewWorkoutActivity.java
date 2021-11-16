package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ViewWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);

        //geting data from adapter
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        TextView textView = findViewById(R.id.workoutNameBig);
        textView.setText(name);
        Toast.makeText(this, "name : " +name , Toast.LENGTH_SHORT).show();
    }
}