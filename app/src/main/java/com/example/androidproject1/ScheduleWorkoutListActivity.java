package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.androidproject1.models.Workout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScheduleWorkoutListActivity extends AppCompatActivity {
    DatabaseReference database;
    RecyclerView recyclerView;
    ArrayList<Workout> workouts;
    ScheduleViewWorkoutAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_workout_list);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");

        //connecting fields to the layout
        recyclerView = findViewById(R.id.workoutList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //getting the database reference and creating an array of workouts
        database = FirebaseDatabase.getInstance().getReference("Workout");
        workouts = new ArrayList<>();

        //setting up the adapter
        myAdapter = new ScheduleViewWorkoutAdapter(this, workouts, date);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override

            //looping through nodes
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    //getting the workout from FB to the model
                    Workout workout = dataSnapshot.getValue(Workout.class);
                    //adding the workout to the array list
                    workouts.add(workout);
                }
                //notifying the adapter of changes to keep the UI up-to-date
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}