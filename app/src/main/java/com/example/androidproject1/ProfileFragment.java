package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.androidproject1.dao.UserDao;
import com.example.androidproject1.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    String Uname, FName, LName, dob, sex, weight, height;
    String UserName = "";
    private FirebaseAuth firebaseAuth;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        Username = view.findViewById(R.id.username);
        ProfileFname = view.findViewById(R.id.profile_fname);
        ProfileLname = view.findViewById(R.id.profile_lname);
        DOB = view.findViewById(R.id.dobtext);
        Sex = view.findViewById(R.id.sextext);
        Weight = view.findViewById(R.id.ET_pro_weight);
        Height = view.findViewById(R.id.heighttext);

        // TODO: add code to get data from db to populate these textviews
        // TODO: need some way to get username to pul down data from db - below is no working
        //userName = view.findViewById(R.id.name); // getting from reg page
        //UserName = userName.getText().toString();
//
//        Bundle profile = getActivity().getIntent().getExtras();
//        UserName =  profile.getString("username");
//
//        //UserName = getActivity().getIntent().getExtra("username");
//        //UserName = user.getUsername();
//        //userDao.getUserByUsername(UserName);
//
//        //testing - not working
//        ProfileFname.setText(UserName);

//        private FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        Log.d("myapp-uid", uid);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("User");
        rootRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                Log.d("myapp-email", user.getEmail());
                Log.d("myapp-username", user.getUsername());
                // Do something with the retrieved data or Bruce Wayne

                Username.setText(user.getUsername());
                ProfileFname.setText(user.getFname());
                ProfileLname.setText(user.getLname());
                DOB.setText(user.getDob());
                Sex.setText(user.getSex());
                Weight.setText(user.getWeight());
                Height.setText(user.getHeight());
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

