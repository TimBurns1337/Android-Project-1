package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UpdateProfileActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        btn=findViewById(R.id.btnUpdate);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.profile_Frag, new ProfileFragment()).commit();
                // TO DO:  do all updating before reaching finish

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