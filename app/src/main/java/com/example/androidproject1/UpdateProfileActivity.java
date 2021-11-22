package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidproject1.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

public class UpdateProfileActivity extends AppCompatActivity {

    Button btn;

    EditText fname, lname, dob, sex, weight, height;
    String FName, LName, DOB, Sex, Weight, Height;

    private User user;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        public void onDataChange(DataSnapshot dataSnapshot) {
            user = dataSnapshot.getValue(User.class);
            Log.d("myapp-email", user.getEmail());
            Log.d("myapp-username", user.getUsername());
            // Do something with the retrieved data or Bruce Wayne
            ProfileFname.setText(user.getUsername());
        }

        fname = findViewById(R.id.et_fname);
        lname = findViewById(R.id.et_lname);
        dob = findViewById(R.id.et_dob);
        sex = findViewById(R.id.et_sex);
        weight = findViewById(R.id.et_weight);
        height = findViewById(R.id.et_height);

        Bundle profile = getIntent().getExtras();

        FName =  profile.getString("fname");
        LName =  profile.getString("lname");
        DOB =  profile.getString("dob");
        Sex =  profile.getString("sex");
        Weight =  profile.getString("weight");
        Height =  profile.getString("height");

        fname.setText(FName);
        lname.setText(LName);
        dob.setText(DOB);
        sex.setText(Sex);
        weight.setText(Weight);
        height.setText(Height);


        btn=findViewById(R.id.btnUpdate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.profile_Frag, new ProfileFragment()).commit();
                // TODO:  do all updating before reaching finish

                // Use user or userdoa??? - new?


                finish();
            }
        });
    }

    public void updateProfile(View view) {
        // update profile to db here


        // below move back to profile frag
        Intent intent = new Intent(this,ProfileFragment.class );

        startActivity(intent);
    }
}