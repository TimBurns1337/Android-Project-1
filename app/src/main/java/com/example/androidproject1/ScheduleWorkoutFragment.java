package com.example.androidproject1;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ScheduleWorkoutFragment extends Fragment {

    private ScheduleWorkoutViewModel mViewModel;

    public static ScheduleWorkoutFragment newInstance() {
        return new ScheduleWorkoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_workout_fragment, container, false);



//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ScheduleWorkoutViewModel.class);
//        // TODO: Use the ViewModel
//    }

    Button btn = (Button) view.findViewById(R.id.sched_workout);
        btn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent i = new Intent(getActivity(),ScheduleWorkoutListActivity.class);
            // TODO: get data from textview


            startActivity(i);
        }
    });

        return view;}
}