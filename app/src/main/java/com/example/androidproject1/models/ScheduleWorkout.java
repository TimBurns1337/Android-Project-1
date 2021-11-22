package com.example.androidproject1.models;

public class ScheduleWorkout {

    private String workoutName;
    private String workoutDate;

    public ScheduleWorkout() {
    }

    public ScheduleWorkout(String workoutName, String workoutDate) {
        this.workoutName = workoutName;
        this.workoutDate = workoutDate;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }
}
