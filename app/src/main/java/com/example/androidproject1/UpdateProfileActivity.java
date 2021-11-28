package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
    private Uri mImageUri = null;

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
                String DOB  = dob.getText().toString();
                String Sex = sex.getText().toString();
                String Weight = weight.getText().toString();
                String Height = height.getText().toString();

                updatedata(firstName, lastName, DOB, Sex, Weight, Height);
                finish();
            }
        });
    }


    private void updatedata(String firstName, String lastName, String dob,
                            String sex, String weight, String height) {
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        HashMap User = new HashMap();
        User.put("firstName", firstName);
        User.put("lastName", lastName);
        User.put("dob", dob);
        User.put("sex", sex);
        User.put("weight", weight);
        User.put("height", height);
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

    public void addPhoto(View view) {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //To save to file: photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.withAppendedPath(locationForPhotos, targetFilename));
        if (photoIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(photoIntent);
            photoIntent.setType("image/*");
        }
    }

    public void selectImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }
}