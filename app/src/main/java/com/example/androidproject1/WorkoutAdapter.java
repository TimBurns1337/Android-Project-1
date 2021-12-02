package com.example.androidproject1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        //setting the UI
        Workout workout = list.get(position);
        holder.workoutName.setText(workout.getWorkoutName());
        holder.workoutDesc.setText(workout.getWorkoutDesc());
        holder.workoutID.setText(workout.getId());
        holder.workoutID.setVisibility(View.INVISIBLE);
        Glide.with(context).load(workout.getWorkoutImg()).centerCrop().into(holder.workoutImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView workoutName, workoutDesc, workoutID;
        ImageView workoutImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //connecting to the UI
            workoutName = itemView.findViewById(R.id.workoutName);
            workoutDesc = itemView.findViewById(R.id.workoutDescription);
            workoutImg = itemView.findViewById(R.id.idWorkImg);
            workoutID = itemView.findViewById(R.id.workID);
            //creating a listener when you click on the card
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //send name of thew workout and ID to the next intent
            Intent intent = new Intent(v.getContext() , WorkoutOverviewActivity.class);
            intent.putExtra("name" , workoutID.getText());
            intent.putExtra("name2" , workoutName.getText());
            v.getContext().startActivity(intent);

        }
    }
}
