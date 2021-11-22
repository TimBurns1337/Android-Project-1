package com.example.androidproject1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidproject1.models.ScheduleWorkout;
import com.example.androidproject1.models.Workout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleViewWorkoutAdapter extends RecyclerView.Adapter<ScheduleViewWorkoutAdapter.MyViewHolder>  {

    Context context;
    String date;
    ArrayList<Workout> list;


    public ScheduleViewWorkoutAdapter(Context context, ArrayList<Workout> list, String date) {
        this.context = context;
        this.date = date;
        this.list = list;
    }

    @NonNull
    @Override
    public ScheduleViewWorkoutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent,false);
        return new ScheduleViewWorkoutAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewWorkoutAdapter.MyViewHolder holder, int position) {
        Workout workout = list.get(position);
        holder.workoutName.setText(workout.getWorkoutName());
        holder.workoutDesc.setText(workout.getWorkoutDesc());
        Glide.with(context).load(workout.getWorkoutImg()).centerCrop().into(holder.workoutImg);
        holder.date = date;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Schedule");
        TextView workoutName, workoutDesc;
        ImageView workoutImg;
        String date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workoutName);
            workoutDesc = itemView.findViewById(R.id.workoutDescription);
            workoutImg = itemView.findViewById(R.id.idWorkImg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Map workout = new HashMap();
            workout.put("workout", workoutName.getText().toString());
            workout.put("date", date);
            database.push().setValue(workout);
            ((ScheduleWorkoutListActivity)view.getContext()).finish();
        }
    }
}
