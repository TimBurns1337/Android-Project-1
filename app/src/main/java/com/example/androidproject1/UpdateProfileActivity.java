package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidproject1.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.HashMap;



public class UpdateProfileActivity extends AppCompatActivity {
    Button btn;
    EditText fname, lname, dob, sex, weight, height;
    String FName, LName, DOB, Sex, Weight, Height;
    ImageView selectedImage;
    // these vars are to upload user data to fbase
    private User user;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        selectedImage = findViewById(R.id.displayImageView);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

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
                // TODO:  do all updating before reaching finish

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
        //User.put("profileImage", filePath);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("User");
        rootRef.child(uid).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()) {
                    finish();
                } else {
                    Toast.makeText(UpdateProfileActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // below does not seem to work
    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(photo);
        }
    }

    public void uploadPic(View view) {

        // get image from image view and upload to firebase storage
    }
}