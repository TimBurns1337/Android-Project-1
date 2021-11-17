package com.example.androidproject1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject1.models.Workout;
import com.example.androidproject1.models.WorkoutPlaylist;

import java.util.ArrayList;

public class detailedWorkoutAdapter extends RecyclerView.Adapter<detailedWorkoutAdapter.MyHolder> {

    Context context;

    ArrayList<WorkoutPlaylist> list;

    public detailedWorkoutAdapter(Context context, ArrayList<WorkoutPlaylist> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public detailedWorkoutAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.video_item, parent,false);
        return new detailedWorkoutAdapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull detailedWorkoutAdapter.MyHolder holder, int position) {
        WorkoutPlaylist workout = list.get(position);
       holder.workoName.setText(workout.getName());
       holder.workoTime.setText(String.valueOf(workout.getTime()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView workoName, workoTime;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            workoName = itemView.findViewById(R.id.workoName);
            workoTime = itemView.findViewById(R.id.workoTime);
        }

    }
}
