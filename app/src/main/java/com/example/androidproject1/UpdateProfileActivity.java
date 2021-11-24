package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidproject1.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

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

        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("User");
//        rootRef.child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                user = dataSnapshot.getValue(User.class);
//                Log.d("myapp-email", user.getEmail());
//                Log.d("myapp-username", user.getUsername());
//                // Do something with the retrieved data or Bruce Wayne
//                //ProfileFname.setText(user.getUsername());
//
//                btn=findViewById(R.id.btnUpdate);
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////                ft.replace(R.id.profile_Frag, new ProfileFragment()).commit();
//                        // TODO:  do all updating before reaching finish
//
//                        // Use user or userdoa??? - new?
//                        //
//                        String firstname = fname.getText().toString();
//                        user.setFname(firstname);
//
//                        finish();
//                    }
//                });
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.e("UserListActivity", "Error occured");
//                // Do something about the error
//            }
//        });




        // use this to update
        btn=findViewById(R.id.btnUpdate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.profile_Frag, new ProfileFragment()).commit();
                // TODO:  do all updating before reaching finish

                // Use user or userdoa??? - new?
                String firstName = fname.getText().toString();
                String lastName = lname.getText().toString();

                updatedata(firstName, lastName);
                finish();
            }
        });
    }


    private void updatedata(String firstName, String lastName) {
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        HashMap User = new HashMap();
        User.put("firstName", firstName);
        User.put("lastName", lastName);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("User");
        rootRef.child(uid).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()) {

//                    binding.userName.setText("");
//                    binding.firstName.setText("");
//                    binding.lastname.setText("");
//                    binding.age.setText("");
//                    Toast.makeText(UpdateData.this,"Successfully Updated",Toast.LENGTH_SHORT).show();

                    finish();

                } else {

                    Toast.makeText(UpdateProfileActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}