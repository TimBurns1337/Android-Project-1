package com.example.androidproject1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject1.models.ScheduleWorkout;
import com.example.androidproject1.models.Workout;

import java.util.ArrayList;

public class ScheduleWorkoutListAdapter  extends RecyclerView.Adapter<ScheduleWorkoutListAdapter.MyViewHolder>  {
    Context context;
    ArrayList<ScheduleWorkout> list;

    public ScheduleWorkoutListAdapter(Context context, ArrayList<ScheduleWorkout> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ScheduleWorkoutListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.schedule_item, parent,false);
        return new ScheduleWorkoutListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleWorkoutListAdapter.MyViewHolder holder, int position) {
        ScheduleWorkout workout = list.get(position);
        holder.workoutName.setText(workout.getWorkoutName());
        holder.workoutDate.setText(workout.getWorkoutDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView workoutName, workoutDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workoutName);
            workoutDate = itemView.findViewById(R.id.workoutDate);
        }
    }
}
