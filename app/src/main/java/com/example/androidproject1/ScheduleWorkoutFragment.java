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
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        CalendarView calendar = (CalendarView) view.findViewById(R.id.calendarview);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final String[] selectedDate = {sdf.format(new Date(calendar.getDate()))};;
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                month = month + 1;
                selectedDate[0] = String.valueOf(day + "/" + month + "/" + year);
            }
        });


        Button btn = (Button) view.findViewById(R.id.sched_workout);
        btn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){

            Intent i = new Intent(getActivity(),ScheduleWorkoutListActivity.class);
            // TODO: get data from textview
            i.putExtra("date", selectedDate[0]);

            startActivity(i);
        }
    });

        return view;}
}