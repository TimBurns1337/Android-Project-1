package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button register;
    Button login;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_ek3);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin(view);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), WorkoutSearchActivity.class));
        }

        //Get shared preferences object
        String sharedPrefFile = "com.example.androidproject1"; // needs to be the name of your application
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile,
                MODE_PRIVATE);

        String text = mPreferences.getString("text","");
        // text is the key, default value is blank
        if(text!=""){
            email.setText(text);
        }
    }

    public void newRegisterClicked(View view) {
        Intent intent = new Intent(this,RegisterActivity.class );

        startActivity(intent);
    }

    public void saveDataLog(View view) {

        //Get the text in EditText view
        String text = email.getText().toString();

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

    private void userLogin(View view){

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password.getText().toString())){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), WorkoutSearchActivity.class));
                        }
                    }
                });

    }
}