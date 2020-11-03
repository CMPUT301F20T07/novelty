package com.example.novelty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";

    EditText myUsername, myEmail, myPassword, myPhone;
    Button myRegisterBtn;
    TextView myLoginLink; // used to direct user to login activity
    String userID; //unique userID for current user
    FirebaseAuth fAuth; //provided by firebase used to register the user.
    FirebaseFirestore fStore;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set views, buttons, and progress bar
        myUsername = findViewById(R.id.username_view);
        myEmail = findViewById(R.id.email_view);
        myPassword = findViewById((R.id.password_view));
        myPhone = findViewById(R.id.phone_number_view);

        myRegisterBtn = findViewById(R.id.register_button);
        myLoginLink = findViewById(R.id.login_link);

        fAuth = FirebaseAuth.getInstance(); // gets current instance of database from firebase to perform operations
        fStore = FirebaseFirestore.getInstance();// get instance of fireStore
        progressBar = findViewById(R.id.progressBar);

        //returning user that is already logged in
        if (fAuth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        //click the register button

        myRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the values of all fields including email/password for authenticating later
                String email = myEmail.getText().toString().trim(); // trim() eliminates leading and trailing spaces.
                String password = myPassword.getText().toString().trim();
                String username = myUsername.getText().toString().trim();
                String phone = myPhone.getText().toString();

                //validate the email and password
                //check if there was something entered for email
                if (TextUtils.isEmpty(email)){
                    //display error message
                    myEmail.setError("Email is required");
                    return ;
                }

                //check if there was something entered for password
                if (TextUtils.isEmpty(password)){
                    //display error message
                    myPassword.setError("Password is required");
                    return;
                }
                // check length of the password is correct
                if (myPassword.length() < 6){
                    myPassword.setError("Password must be at least 6 characters long");
                }

                progressBar.setVisibility(View.VISIBLE); // show user that they are being registered.

                //register user in firebase
                //add an even listener to inform when complete.
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if task is successful then successfully created a user
                        //if not complete
                        if (task.isSuccessful()){
                            //small popup to show success
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();

                            // create/update users collections with documents of each user profile
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            //add user data using HashMap
                            Map<String, Object> user = new HashMap<>();
                            user.put("username", myUsername);
                            user.put("email", myEmail);
                            user.put("phone", myPhone);

                            //insert into cloud database
                            documentReference
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "OnSuccess: user profile is created for "+ userID);
                                }
                            });


                            //send user to main activity
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        }else
                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE); // on Error, Hide spinner
                    }
                }) ;
            }
        });

        //if users already have an account, take them to login activity
        myLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send user to Login activity
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }
}