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
import com.example.androidproject1.models.WorkoutPlaylist;

import java.util.ArrayList;

public class WorkoutOverviewAdapter extends RecyclerView.Adapter<WorkoutOverviewAdapter.MyHolder> {

    Context context;
    ArrayList<WorkoutPlaylist> list;

    public WorkoutOverviewAdapter(Context context, ArrayList<WorkoutPlaylist> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WorkoutOverviewAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.video_item, parent,false);
        return new WorkoutOverviewAdapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutOverviewAdapter.MyHolder holder, int position) {
        //setting up the UI
        WorkoutPlaylist workout = list.get(position);
       holder.workoName.setText(workout.getName());
       holder.workoTime.setText(String.valueOf(workout.getTime()));
       //using Glide to get a picture
        Glide.with(context).load(workout.getImg()).centerCrop().into(holder.workImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        //fields
        TextView workoName, workoTime;
        ImageView workImg;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //connecting UI to the fields
            workoName = itemView.findViewById(R.id.workoName);
            workoTime = itemView.findViewById(R.id.workoTime);
            workImg = itemView.findViewById(R.id.workImage);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            //creating a listener
            int postion = getAdapterPosition();
            Intent intent = new Intent(v.getContext() , TimerActivity.class);
            intent.putExtra("name" , workoName.getText());
            intent.putExtra("time" , workoTime.getText());
            v.getContext().startActivity(intent);
        }

    }
}
