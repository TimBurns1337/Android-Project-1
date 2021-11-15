package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.androidproject1.dao.UserDao;
import com.example.androidproject1.models.User;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private UserDao userDao;
    private User user = new User();
    EditText userName;

    TextView ProfileFname;
    TextView ProfileLname;
    TextView DOB;
    TextView Sex;
    TextView Weight;
    TextView Height;

    String FName, LName, dob, sex, weight, height;
    String UserName = "";

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_fragment, container, false);

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

