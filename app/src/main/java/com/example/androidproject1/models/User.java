package com.example.androidproject1.models;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    // TODO: Leave the above for now - only allow for update on the below
    private String password;
    private String email;
    private String fname;
    private String firstName;
    private String lastName;
    private String lname;
    private String dob;
    private String sex;
    private String weight;
    private String height;
    private int score;


    private String profileImage;

    public User() {
    }

    // TODO: use this constructor to update user info
    public User(String password, String email, String fname, String lname,
                String dob, String sex, String weight, String height, int score) {
        this.password = password;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.sex = sex;
        this.weight = weight;
        this.height = height;
        this.score = score;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String fname) {
        this.firstName = fname;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lname) {
        this.lastName = lname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
