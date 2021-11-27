package com.example.androidproject1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject1.models.ScheduleWorkout;
import com.example.androidproject1.models.Workout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleWorkoutListAdapter  extends RecyclerView.Adapter<ScheduleWorkoutListAdapter.MyViewHolder>  {
    Context context;
    String date;
    ArrayList<ScheduleWorkout> list;

    public ScheduleWorkoutListAdapter(Context context, ArrayList<ScheduleWorkout> list, String date) {
        this.context = context;
        this.date = date;
        this.list = list;
    }

    @NonNull
    @Override
    public ScheduleWorkoutListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.schedule_item, parent,false);
        MyViewHolder vh = new ScheduleWorkoutListAdapter.MyViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleWorkoutListAdapter.MyViewHolder holder, int position) {
        ScheduleWorkout workout = list.get(position);
        holder.workoutName.setText(workout.getWorkoutName());
        holder.workoutDate.setText(workout.getWorkoutDate());
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Schedule1");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete Workout")
                        .setMessage("Do you want to delete this workout?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseReference s = database.child(uid).child(String.valueOf(workout.getID()));
                                s.removeValue();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
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
