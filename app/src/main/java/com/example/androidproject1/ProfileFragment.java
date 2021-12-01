package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidproject1.dao.UserDao;
import com.example.androidproject1.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private UserDao userDao;
    private User user;
    EditText userName;

    TextView Username;
    TextView ProfileFname;
    TextView ProfileLname;
    TextView DOB;
    TextView Sex;
    TextView Weight;
    TextView Height;

    Context context;



    String Uname, FName, LName, dob, sex, weight, height;
    String UserName = "";
    private FirebaseAuth firebaseAuth;
    private StorageReference storageRef;
    private FirebaseStorage storage;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("image/" + uid);

        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        Username = view.findViewById(R.id.username);
        ProfileFname = view.findViewById(R.id.profile_fname);
        ProfileLname = view.findViewById(R.id.profile_lname);
        DOB = view.findViewById(R.id.dobtext);
        Sex = view.findViewById(R.id.sextext);
        Weight = view.findViewById(R.id.ET_pro_weight);
        Height = view.findViewById(R.id.heighttext);

        Log.d("myapp-uid", uid);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("User");
        rootRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                Log.d("myapp-email", user.getEmail());
                Log.d("myapp-username", user.getUsername());
                Username.setText(user.getUsername());
                ProfileFname.setText(user.getfirstName());
                ProfileLname.setText(user.getlastName());
                DOB.setText(user.getDob());
                Sex.setText(user.getSex());
                Weight.setText(user.getWeight());
                Height.setText(user.getHeight());

                ImageView profileIV = (ImageView) getView().findViewById(R.id.profileImage);

                //String url = storageRef.getDownloadUrl().toString();

                String url = storageRef.getDownloadUrl().toString();

                //Log.d("url", url);
                //Picasso.get().load(url).resize(500,500).into(profileIV);
                Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/calisthenics-83123.appspot.com/o/images%2F4vAv33XXvHdlauDmq2mfSpTBiSn2?alt=media&token=db7b9ee5-7eb4-4c2a-9446-15142b1d3844").resize(500,500).into(profileIV);

//                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
//                        generatedFilePath = downloadUri.toString();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("UserListActivity", "Error occured");
                // Do something about the error
            }
        });



        Button btn = (Button) view.findViewById(R.id.btnChangePro);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getActivity(),UpdateProfileActivity.class);
                // TODO: get data from textview
                FName = ProfileFname.getText().toString();
                LName = ProfileLname.getText().toString();
                dob = DOB.getText().toString();
                sex = Sex.getText().toString();
                weight = Weight.getText().toString();
                height = Height.getText().toString();


                i.putExtra("fname",FName);
                i.putExtra("lname",LName);
                i.putExtra("dob",dob);
                i.putExtra("sex",sex);
                i.putExtra("weight",weight);
                i.putExtra("height",height);

                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }








}

