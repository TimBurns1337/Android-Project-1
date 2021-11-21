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

import com.example.androidproject1.models.WorkoutPlaylist;

import java.util.ArrayList;

public class ViewWorkoutAdapter extends RecyclerView.Adapter<ViewWorkoutAdapter.MyHolder> {

    Context context;

    ArrayList<WorkoutPlaylist> list;

    public ViewWorkoutAdapter(Context context, ArrayList<WorkoutPlaylist> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewWorkoutAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.video_item, parent,false);
        return new ViewWorkoutAdapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWorkoutAdapter.MyHolder holder, int position) {
        WorkoutPlaylist workout = list.get(position);
       holder.workoName.setText(workout.getName());
       holder.workoTime.setText(String.valueOf(workout.getTime()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView workoName, workoTime;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            workoName = itemView.findViewById(R.id.workoName);
            workoTime = itemView.findViewById(R.id.workoTime);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int postion = getAdapterPosition();
            Toast.makeText(v.getContext(), "postion"+postion, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext() , TimerActivity.class);
            intent.putExtra("name" , workoName.getText());
            v.getContext().startActivity(intent);
        }

    }
}
