package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidproject1.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.util.HashMap;
import java.util.UUID;


public class UpdateProfileActivity extends AppCompatActivity {
    Button btn;
    EditText fname, lname, dob, sex, weight, height;
    String FName, LName, DOB, Sex, Weight, Height;
    ImageView selectedImage;
    // these vars are to upload user data to fbase
    private User user;
    private FirebaseAuth firebaseAuth;

    //uploading image
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private Uri imageURI;

    private static final int CAMERA_REQUEST = 2;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // set variable
        selectedImage = findViewById(R.id.displayImageView);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        fname = findViewById(R.id.et_fname);
        lname = findViewById(R.id.et_lname);
        dob = findViewById(R.id.et_dob);
        sex = findViewById(R.id.et_sex);
        weight = findViewById(R.id.et_weight);
        height = findViewById(R.id.et_height);

        // get intent info
        Bundle profile = getIntent().getExtras();

        // set vars
        FName = profile.getString("fname");
        LName = profile.getString("lname");
        DOB = profile.getString("dob");
        Sex = profile.getString("sex");
        Weight = profile.getString("weight");
        Height = profile.getString("height");

        // display data sent in intent into tv's
        fname.setText(FName);
        lname.setText(LName);
        dob.setText(DOB);
        sex.setText(Sex);
        weight.setText(Weight);
        height.setText(Height);
        // load image into imageview
        Picasso.get().load(profile.getString("profileImage")).resize(500,500).into(selectedImage);

        //method to get info from users tv and pass them into update method
        btn = findViewById(R.id.btnUpdate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO:  do all updating before reaching finish

                String firstName = fname.getText().toString();
                String lastName = lname.getText().toString();
                String DOB = dob.getText().toString();
                String Sex = sex.getText().toString();
                String Weight = weight.getText().toString();
                String Height = height.getText().toString();

                updatedata(firstName, lastName, DOB, Sex, Weight, Height);
                finish();
            }
        });
    }


    // method to update the database
    private void updatedata(String firstName, String lastName, String dob,
                            String sex, String weight, String height) {
        // create firebase auth and uid
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();

        // user hashmap to pass data into user object
        HashMap User = new HashMap();
        User.put("firstName", firstName);
        User.put("lastName", lastName);
        User.put("dob", dob);
        User.put("sex", sex);
        User.put("weight", weight);
        User.put("height", height);
        //User.put("profileImage", filePath);
        // create database reference and pass in the above user object
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

    // method for taking pics, not using atm, implement in future
    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
        //registerForActivityResult(intent, CAMERA_REQUEST);
    }

    // on act result method used to get image from gallery and place it in the image view and call upload method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // below is for taking pics and seting them in image view
//        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            selectedImage.setImageBitmap(photo);
//        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageURI = data.getData();
            selectedImage.setImageURI(imageURI);
            uploadPic();
        }
    }

    // method used to  upload picture to firebase
    public void uploadPic() {
        // create string for uid and progressdialog to show progress of upload
        String uid = firebaseAuth.getCurrentUser().getUid();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        // create storage reference where images are stored
        StorageReference profileRef = storageRef.child("images/" + uid);
        profileRef.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // on success get user id to reference current user, keep track download url
                        Uri downloadUrl = uri;
                        Log.d("myapp", downloadUrl.toString());
                        String uid = firebaseAuth.getCurrentUser().getUid();

                        // create user object and create a new entry (similar to above) to store the url of image uploaded and save in db
                        HashMap User = new HashMap();
                        User.put("profileImage", downloadUrl.toString());
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("User");
                        rootRef.child(uid).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    finish();
                                } else { // display message if failed to update
                                    Toast.makeText(UpdateProfileActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override // display messge if failed to upload
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override // display progress of upload
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPrecent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentage Completed: " + (int) progressPrecent + "%");
            }
        });
    }

    // method to access the users gallery to upload pic to firebase
    public void getPic(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
}