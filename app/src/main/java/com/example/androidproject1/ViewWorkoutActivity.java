package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidproject1.models.Workout;
import com.example.androidproject1.models.WorkoutPlaylist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewWorkoutActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    ViewWorkoutAdapter myAdapter;
    ArrayList<WorkoutPlaylist> workouts;
    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);

        //geting data from adapter
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        TextView textView = findViewById(R.id.detWorkoutName);
        textView.setText(name);
        ImageView imageView = findViewById(R.id.workImgFull);


        recyclerView = findViewById(R.id.workoutView);
        database = FirebaseDatabase.getInstance().getReference("Workout");

        DatabaseReference work = database.child(name);
        DatabaseReference database2 = FirebaseDatabase.getInstance().getReference("WorkoutExercises");

        work.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workout = dataSnapshot.getValue(Workout.class);
                Glide.with(ViewWorkoutActivity.this).load(workout.getWorkoutImg()).centerCrop().into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        workouts = new ArrayList<>();
        myAdapter = new ViewWorkoutAdapter(this,workouts);

        recyclerView.setAdapter(myAdapter);

        database.child(name).child("workout_playlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    WorkoutPlaylist workout = dataSnapshot.getValue(WorkoutPlaylist.class);
                    workouts.add(workout);
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if (!workouts.isEmpty()) {
                        for (WorkoutPlaylist workout:workouts
                             ) {
                            if (snap.child("name").getValue(String.class).equals(workout.getName())) {
                                String url = snap.child("video").getValue(String.class);
                                workout.setImg(url);
                            }
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });

    }
}