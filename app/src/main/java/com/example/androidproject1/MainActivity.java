package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void loginClicked(View view) {
        Intent intent = new Intent(this,LoginActivity.class );

        startActivity(intent);
    }

    public void signupClicked(View view) {
        Intent intent = new Intent(this,RegisterActivity.class );

        startActivity(intent);
    }
}