
package com.example.novelty.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novelty.R;
import com.example.novelty.bean.BookBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class contact_info extends AppCompatActivity {
    public static final String TAG = "TAG";
    private EditText myUsername, myEmail, myPhone;

    private String userID;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        myUsername = findViewById(R.id.username_view);
        myEmail = findViewById(R.id.email_view);
        myPhone = findViewById((R.id.phone_number_view));

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

        userID = user.getUid();


        DocumentReference userdoc = Database.getUserRef(userID);
        userdoc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot docsnap, @Nullable FirebaseFirestoreException error) {
                assert docsnap != null;

                myUsername.setText(docsnap.getString("username"));
                myEmail.setText(docsnap.getString("email"));
                myPhone.setText(docsnap.getString("phone"));

            }
        });

    }

    public void update(View view) {
        final String email = myEmail.getText().toString();
        final String username = myUsername.getText().toString();
        final String phone = myPhone.getText().toString();

        // can not leave email blank
        if (email.isEmpty()){
            Toast.makeText(contact_info.this, "email field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

        assert user != null;
        user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                final DocumentReference docref = Database.getUserRef(userID);

                final Map<String,Object> updated = new HashMap<>();
                updated.put("email", email);
                updated.put("username",username);
                updated.put("phone",phone);


                // make username unique through Query
                Database.getusers()
                        .whereEqualTo("username", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            // task is a list of documents
                            if (task.getResult().size() == 0){
                                docref.update(updated);
                                Toast.makeText(contact_info.this, "updated", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                myUsername.setError("username already exists");
                                Toast.makeText(contact_info.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(contact_info.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}

