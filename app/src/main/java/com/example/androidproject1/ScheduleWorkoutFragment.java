package com.example.androidproject1;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.androidproject1.models.ScheduleWorkout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleWorkoutFragment extends Fragment {

    private ScheduleWorkoutViewModel mViewModel;
    RecyclerView recyclerView;
    DatabaseReference database;
    ScheduleWorkoutListAdapter myAdapter;
    ArrayList<ScheduleWorkout> workouts;

    public static ScheduleWorkoutFragment newInstance() {
        return new ScheduleWorkoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_workout_fragment, container, false);

        CalendarView calendar = (CalendarView) view.findViewById(R.id.calendarview);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final String[] selectedDate = {sdf.format(new Date(calendar.getDate()))};

        recyclerView = (RecyclerView)view.findViewById(R.id.scheduleWorkout);
        database = FirebaseDatabase.getInstance().getReference("Schedule1");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        workouts = new ArrayList<>();
        myAdapter = new ScheduleWorkoutListAdapter(getActivity(), workouts, selectedDate[0]);
        recyclerView.setAdapter(myAdapter);



        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workouts.clear();
                for (DataSnapshot users : snapshot.getChildren()){
                    if (users.getKey().equals(uid)) {
                        for (DataSnapshot dsWorkout : users.getChildren()) {
                            String id = dsWorkout.getKey();
                            String workout = dsWorkout.child("workout").getValue().toString();
                            String date = dsWorkout.child("date").getValue().toString();
                            ScheduleWorkout sw = new ScheduleWorkout(workout, date, id);
                            workouts.add(sw);
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        calendar.setMinDate(System.currentTimeMillis() - 1000);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                month = month + 1;
                selectedDate[0] = String.valueOf(day + "/" + month + "/" + year);

                //CHANGE DATA IN RECYCLER


            }
        });


        Button btn = (Button) view.findViewById(R.id.sched_workout);
        btn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){

            Intent i = new Intent(getActivity(),ScheduleWorkoutListActivity.class);
            // TODO: get data from textview
            i.putExtra("date", selectedDate[0]);

            startActivity(i);
        }
        });

        return view;
    }
}