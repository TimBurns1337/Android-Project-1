package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.androidproject1.models.Workout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkoutListActivity extends AppCompatActivity {

    //global fields
    RecyclerView workoutRV;
    DatabaseReference fbDatabaseReference;
    WorkoutAdapter workoutAdapter;
    ArrayList<Workout> workouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        //connecting fields to the layout
        workoutRV = findViewById(R.id.workoutList);
        workoutRV.setHasFixedSize(true);
        workoutRV.setLayoutManager(new LinearLayoutManager(this));

        //getting the database reference and creating an array of workouts
        fbDatabaseReference = FirebaseDatabase.getInstance().getReference("Workout");
        workouts = new ArrayList<>();

        //setting up the adapter
        workoutAdapter = new WorkoutAdapter(this,workouts);
        workoutRV.setAdapter(workoutAdapter);

        //getting the data from FB
        getWorkoutsFromFB();
    }

    private void getWorkoutsFromFB() {
        //creating a value listener
        fbDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //looping through nodes
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    //getting the workout from FB to the model
                    Workout workout = dataSnapshot.getValue(Workout.class);
                    workout.setId(dataSnapshot.getKey());
                    //adding the workout to the array list
                    workouts.add(workout);


                }
                //notifying the adapter of changes to keep the UI up-to-date
                workoutAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}