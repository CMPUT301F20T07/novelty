package com.example.novelty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelty.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText myEmail,
            myPassword;
    Button myLoginBtn;
    TextView myRegLink,
            forgotPassLink; //reset password link
    FirebaseAuth fAuth; //provided by firebase used to register the user.
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set views and buttons
        myEmail = findViewById(R.id.email_view);
        myPassword = findViewById((R.id.password_view));
        myLoginBtn = findViewById(R.id.login_button);


        fAuth = FirebaseAuth.getInstance(); // gets current instance of database from firebase to perform operations
        progressBar = findViewById(R.id.progressBar);
        myRegLink = findViewById(R.id.register_link);
        forgotPassLink  = findViewById(R.id.forgot_password_link);

        //click the register button
        //validate the users input
        myLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the values of email and password fields
                String email = myEmail.getText().toString().trim(); // trim() eliminates leading and trailing spaces.
                String password = myPassword.getText().toString().trim();

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

                progressBar.setVisibility(View.VISIBLE); // show user that they are being logged in

                 //authenticate user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //check is login is successful or not
                        if (task.isSuccessful()){
                            //small popup to show success
                            Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();

                            //send user to main activity
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        }else{
                            //if not complete
                            Toast.makeText(Login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE); // on error, hide the spinner
                        }
                    }
                });
            }
        });

        //if users don't have an account, take them to Register activity
        myRegLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send user to Register activity
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        //if users forget their password, take them to reset password activity
        forgotPassLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog  = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter email to receive a reset password link");
                passwordResetDialog.setView(resetMail);

                // click positive send button to send user a reset password link
                passwordResetDialog.setPositiveButton("send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract email and send Link to reset password
                        String myEmail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(myEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "reset link sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                // cancel button to direct back to login view.
                passwordResetDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //return to login activity.
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }
                });

                //show the user the dialog
                passwordResetDialog.create().show();



            }
        });
    }
}