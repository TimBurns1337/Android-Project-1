package com.example.androidproject1.models;

public class Workout {

    private String workoutName;
    private String workoutImg;
    private String workoutDesc;
    private String id;

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutDesc() {
        return workoutDesc;
    }

    public void setWorkoutDesc(String workoutDesc) {
        this.workoutDesc = workoutDesc;
    }

    public String getWorkoutImg() {
        return workoutImg;
    }

    public void setWorkoutImg(String workoutImg) {
        this.workoutImg = workoutImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
