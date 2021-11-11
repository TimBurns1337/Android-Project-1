package com.example.androidproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText et1;
    EditText et2;
    EditText et3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        et1 = findViewById(R.id.name);
        et2 = findViewById(R.id.email);
        et3 = findViewById(R.id.password);

        //Get shared preferences object
        String sharedPrefFile = "com.example.androidproject1"; // needs to be the name of your application
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile,
                MODE_PRIVATE);

        String text1 = mPreferences.getString("text1","");
        String text2 = mPreferences.getString("text2","");
        String text3 = mPreferences.getString("text3","");
        // text is the key, default value is blank
        if(text1!=""){
            et1.setText(text1);
        }

        if(text2!=""){
            et2.setText(text2);
        }

        if(text3!=""){
            et3.setText(text3);
        }
    }

    public void login2Clicked(View view) {
        Intent intent = new Intent(this,LoginActivity.class );

        startActivity(intent);
    }

    public void saveDataReg(View view) {

        //Get the text in EditText view
        String text1 = et1.getText().toString();
        String text2 = et2.getText().toString();
        String text3 = et3.getText().toString();

        //Get shared preferences object
        String sharedPrefFile = "com.example.androidproject1";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile,
                MODE_PRIVATE);

        //Save data to shared pref
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("text1",text1); // key and data - string is the key
        preferencesEditor.putString("text2",text2);
        preferencesEditor.putString("text3",text3);
        preferencesEditor.apply();
        // use apply not commit - better because commit will stop the execution until save is done
        // apply will continue the app or keep it running regardless of what happens in the background

    }
}