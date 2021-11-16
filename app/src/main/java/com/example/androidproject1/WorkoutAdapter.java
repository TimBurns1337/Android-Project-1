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

import com.example.androidproject1.models.Workout;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.MyViewHolder> {

    Context context;

    ArrayList<Workout> list;

    public WorkoutAdapter(Context context, ArrayList<Workout> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Workout workout = list.get(position);
        holder.workoutName.setText(workout.getWorkoutName());
        holder.workoutDesc.setText(workout.getWorkoutDesc());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView workoutName, workoutDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            workoutName = itemView.findViewById(R.id.workoutName);
            workoutDesc = itemView.findViewById(R.id.workoutDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int postion = getAdapterPosition();
            Toast.makeText(v.getContext(), "postion"+postion, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext() , ViewWorkoutActivity.class);
            intent.putExtra("name" , workoutName.getText());
            v.getContext().startActivity(intent);

        }
    }
}
