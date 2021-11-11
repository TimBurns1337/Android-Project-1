package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et = findViewById(R.id.email);

        //Get shared preferences object
        String sharedPrefFile = "com.example.androidproject1"; // needs to be the name of your application
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile,
                MODE_PRIVATE);

        String text = mPreferences.getString("text","");
        // text is the key, default value is blank
        if(text!=""){
            et.setText(text);
        }
    }

    public void newsignupClicked(View view) {
        Intent intent = new Intent(this,WorkoutSearchActivity.class );

        startActivity(intent);
    }

    public void newRegisterClicked(View view) {
        Intent intent = new Intent(this,RegisterActivity.class );

        startActivity(intent);
    }

    public void saveDataLog(View view) {

        //Get the text in EditText view
        String text = et.getText().toString();

        //Get shared preferences object
        String sharedPrefFile = "com.example.androidproject1";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile,
                MODE_PRIVATE);

        //Save data to shared pref
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("text",text); // key and data - string is the key
        preferencesEditor.apply();
        // use apply not commit - better because commit will stop the execution until save is done
        // apply will continue the app or keep it running regardless of what happens in the background

    }
}