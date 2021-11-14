package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidproject1.models.*;
import com.example.androidproject1.dao.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class RegisterActivity extends AppCompatActivity {

    private UserDao userDao;
    private Button register;

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

        //

        userDao = new UserDao();
        register = (Button) findViewById(R.id.Register);
        register.setOnClickListener(v->{

            // TODO: handle validation
            User u = new User(
                    et1.getText().toString(),
                    et2.getText().toString(),
                    et3.getText().toString()

            );

            // check if the user exists first
            userDao.getUserByUsername(et1.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {

                                if (task.getResult().getValue() == null) {
                                    // if new user, create one
                                    userDao.add(u).addOnSuccessListener( suc -> {
                                        Log.e("firebase", "New user added", task.getException());
                                    }).addOnFailureListener( err -> {
                                        Log.e("firebase", "Error on adding new user", task.getException());
                                    });

                                } else {
                                    Log.d("firebase", "Existing user found");
                                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                }

                            }
                        }
                    });

        });
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