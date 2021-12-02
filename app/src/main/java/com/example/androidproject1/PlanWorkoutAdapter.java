package com.example.androidproject1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject1.models.Plan;
import com.example.androidproject1.models.ScheduleWorkout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PlanWorkoutAdapter extends RecyclerView.Adapter<PlanWorkoutAdapter.MyViewHolder> {
    Context context;
    String date;
    ArrayList<Plan> list;

    public PlanWorkoutAdapter(Context context, ArrayList<Plan> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PlanWorkoutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.plan_item, parent,false);
        PlanWorkoutAdapter.MyViewHolder vh = new PlanWorkoutAdapter.MyViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanWorkoutAdapter.MyViewHolder holder, int position) {
        //setting the UI
        Plan plan = list.get(position);
        holder.planName.setText(plan.getName());
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
                                DatabaseReference s = database.child(uid).child(String.valueOf(plan.getId()));
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
        TextView planName;

        public MyViewHolder(@NonNull View itemView) {
            //connecting to the UI
            super(itemView);
            planName = itemView.findViewById(R.id.planName);
        }
    }
}
