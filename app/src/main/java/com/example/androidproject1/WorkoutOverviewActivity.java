package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
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

public class WorkoutOverviewActivity extends AppCompatActivity {

    //global fields
    RecyclerView workoutOverviewRV;
    DatabaseReference fbDatabaseRef;
    WorkoutOverviewAdapter workoutOverviewAdapter;
    ArrayList<WorkoutPlaylist> workouts;
    ImageView workoutPicture;
    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);

        //getting the data from adapter
        Intent intent = getIntent();
        String workoutID = intent.getStringExtra("name");
        String workoutName = intent.getStringExtra("name2");

        //setting up name tof the workout and Image
        TextView textView = findViewById(R.id.detWorkoutName);
        textView.setText(workoutName);
        workoutPicture = findViewById(R.id.workImgFull);
        workoutOverviewRV = findViewById(R.id.workoutView);

        //getting the Firebase References
        fbDatabaseRef = FirebaseDatabase.getInstance().getReference("Workout");
        DatabaseReference workoutRef = fbDatabaseRef.child(workoutID);
        DatabaseReference workoutExercisesRef = FirebaseDatabase.getInstance().getReference("WorkoutExercises");

        //getting the image
        getWorkoutNameAndImage(workoutPicture, workoutRef);

        //setting up the adataper
        workoutOverviewRV.setHasFixedSize(true);
        workoutOverviewRV.setLayoutManager(new LinearLayoutManager(this));
        workouts = new ArrayList<>();
        workoutOverviewAdapter = new WorkoutOverviewAdapter(this,workouts);
        workoutOverviewRV.setAdapter(workoutOverviewAdapter);

        //getting the exercises
        getTheExercises(workoutID, workoutExercisesRef);

    }

    private void getTheExercises(String workoutID, DatabaseReference workoutExercisesRef) {
        //getting the workout data
        fbDatabaseRef.child(workoutID).child("workout_playlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    WorkoutPlaylist workout = dataSnapshot.getValue(WorkoutPlaylist.class);
                    workouts.add(workout);
                }
                workoutOverviewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //getting exercises
        workoutExercisesRef.addValueEventListener(new ValueEventListener() {
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
                workoutOverviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }

    private void getWorkoutNameAndImage(ImageView imageView, DatabaseReference workoutRef) {
        workoutRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workout = dataSnapshot.getValue(Workout.class);
                Glide.with(WorkoutOverviewActivity.this).load(workout.getWorkoutImg()).centerCrop().into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }
}