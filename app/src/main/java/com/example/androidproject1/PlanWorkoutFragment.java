package com.example.androidproject1;

import androidx.lifecycle.ViewModelProvider;

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
import android.widget.TextView;

import com.example.androidproject1.models.Plan;
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

public class PlanWorkoutFragment extends Fragment {
    RecyclerView recycler;
    DatabaseReference database;
    PlanWorkoutAdapter myAdapter;
    ArrayList<Plan> plansList;
    Date today = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    TextView month;
    TextView day;
    TextView year;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_workout_fragment, container, false);
        month = view.findViewById(R.id.month);
        day = view.findViewById(R.id.day);
        year = view.findViewById(R.id.year);
        month.setText(monthFormat.format(today));
        day.setText(dayFormat.format(today));
        year.setText(yearFormat.format(today));

        recycler = (RecyclerView)view.findViewById(R.id.recycler);
        database = FirebaseDatabase.getInstance().getReference("Schedule1");
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        plansList = new ArrayList<>();
        myAdapter = new PlanWorkoutAdapter(getActivity(), plansList);
        recycler.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plansList.clear();
                for (DataSnapshot plans : snapshot.getChildren()){
                    if (plans.getKey().equals(uid)) {
                        for (DataSnapshot dsWorkout : plans.getChildren()) {
                            String id = dsWorkout.getKey();
                            String workout = dsWorkout.child("workout").getValue().toString();
                            String date = dsWorkout.child("date").getValue().toString();

                            if (sdf.format(today).equals(date)) {
                                Plan plan = new Plan(workout, id);
                                plansList.add(plan);
                            }
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

}