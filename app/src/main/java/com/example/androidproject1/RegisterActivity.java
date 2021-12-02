package com.example.androidproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidproject1.models.*;
import com.example.androidproject1.dao.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private UserDao userDao;
    private User user;
    //private Button register;

    EditText username;
    EditText email;
    EditText password;
    Button register;
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        // creating user
        user = new User();

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        register = findViewById(R.id.Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(view);
            }
        });
    }

    // move to login page
    public void login2Clicked(View view) {
        Intent intent = new Intent(this,LoginActivity.class );

        startActivity(intent);
    }

    // used to register and check all is provided
    private void registerUser(View view){
        //checking if username is empty
        if(TextUtils.isEmpty(username.getText().toString())){
            Toast.makeText(this,"Please enter username",Toast.LENGTH_LONG).show();
            return;
        }

        //checking if email is empty
        if(TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        //checking if password is empty
        if(TextUtils.isEmpty(password.getText().toString())){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        Log.d("calis", "user");
        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Log.d("calis", "add user");
                            addUsertoFirebase();
                            Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_LONG).show();
                        }else{
                            //display some message here
                            Toast.makeText(getApplicationContext(), String.valueOf(task.getException()),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // add user to db after all checks above are satisfied
    private void addUsertoFirebase() {
        // below 3 lines of code is used to set
        // data in our object class.
        user.setUsername(username.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());

        // inside the method of on Data change we are setting
        // our object class to our database reference.
        // data base reference will sends data to firebase.
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getInstance().getReference("User");
        String id = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child(id).setValue(user);

        Intent intent = new Intent(this, LoginActivity.class );
        startActivity(intent);
    }
}