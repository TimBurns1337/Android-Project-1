package com.example.androidproject1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class ScheduleAddWorkoutAdapter  extends RecyclerView.Adapter<ScheduleAddWorkoutAdapter.MyHolder> {
    Context context;
    String date;
    ArrayList<WorkoutPlaylist> list;

    public ScheduleAddWorkoutAdapter(Context context, ArrayList<WorkoutPlaylist> list, String date) {
        this.context = context;
        this.date = date;
        this.list = list;
    }

    @NonNull
    @Override
    public ScheduleAddWorkoutAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.video_item, parent,false);
        return new ScheduleAddWorkoutAdapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAddWorkoutAdapter.MyHolder holder, int position) {
        WorkoutPlaylist workout = list.get(position);
        holder.workoName.setText(workout.getName());
        holder.workoTime.setText(String.valueOf(workout.getTime()));
        holder.date = date;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder implements  View.OnClickListener  {

        TextView workoName, workoTime;
        String date;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            workoName = itemView.findViewById(R.id.workoName);
            workoTime = itemView.findViewById(R.id.workoTime);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("date", date);
            ((ScheduleViewWorkoutActivity)view.getContext()).finish();
//            Intent intent = new Intent(view.getContext(),
//                    ScheduleWorkoutFragment.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            view.getContext().startActivity(intent);
//            ((ScheduleViewWorkoutActivity)view.getContext()).finish();
        }
    }
}
