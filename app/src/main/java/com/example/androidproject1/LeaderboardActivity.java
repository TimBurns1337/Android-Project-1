package com.example.androidproject1;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject1.dao.UserDao;
import com.example.androidproject1.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

public class LeaderboardActivity  extends AppCompatActivity {

    private UserDao userDao;
    private String uid;
    private String username;
    private TextView firstPlace;
    private TextView secondPlace;
    private TextView thirdPlace;
    private TextView userRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        firstPlace = findViewById(R.id.first_place_name);
        secondPlace = findViewById(R.id.second_place_name);
        thirdPlace = findViewById(R.id.third_place_name);
        userRank = findViewById(R.id.user_rank);

        // user details
        userDao = new UserDao();
        uid = userDao.getCurrentUserUid();
        userDao.getUserByUid(uid).addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                username = u.getUsername();
            }
        });

        // get the top 3 ranking
        userDao.getTopThreeUsers().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                // generate the ranking
                generateTopRanking(dataSnapshot);
            }
        });

        // get current users's ranking
        userDao.getAllUsersOrderedByScore().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                // generate the ranking
                generateRank(dataSnapshot);
            }
        });

    }

    private void generateRank(DataSnapshot snapshot){

        int total = (int) snapshot.getChildrenCount();
        int counter = 0;

        for (DataSnapshot ds : snapshot.getChildren()) {

            User u = ds.getValue(User.class);

            // list is sorted by score, ascending order
            // so need to count backwards to get actual rank
            if (u.getUsername().equals(username)) {
                int rank = total - counter;
                String rankLbl = "TH";

                if (rank % 10 == 1) {
                    rankLbl = "ST";
                } else if (rank % 10 == 2) {
                    rankLbl = "ND";
                } else if (rank % 10 == 3) {
                    rankLbl = "RD";
                }


                Log.d("leaderboard", userRank.getText().toString());
                userRank.setText(String.format("%d%s", rank, rankLbl));
                break;
            }

            counter++;
        }
    }

    private void generateTopRanking(DataSnapshot snapshot){
        for (DataSnapshot ds : snapshot.getChildren()) {

            User u = ds.getValue(User.class);

            // add a label if user is in the top rank
            String userLbl = "";
            if (u.getUsername().equals(username)) {
                userLbl = "(YOU)";
            }

            if (thirdPlace.getText().toString().isEmpty()) {
                thirdPlace.setText(String.format("%s %s\n(%d pts)",u.getUsername().toUpperCase(), userLbl, u.getScore()));

            } else if (secondPlace.getText().toString().isEmpty()) {
                secondPlace.setText(String.format("%s %s\n(%d pts)",u.getUsername().toUpperCase(), userLbl, u.getScore()));

            } else if (firstPlace.getText().toString().isEmpty()) {
                firstPlace.setText(String.format("%s %s\n(%d pts)",u.getUsername().toUpperCase(), userLbl, u.getScore()));

            }

        }

    }

}
