package com.example.novelty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.novelty.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class LendBook extends MainActivity{
    private boolean lend;
    String ISBN_ok;
    private Button cancelButton;
    TextView status;
    TextView description;
    TextView author;
    TextView holder;
    TextView book_name;
    TextView ISBN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lend_book);
        status = findViewById(R.id.textViewISBN2);
        description = findViewById(R.id.editTextTextMultiLine);
        author = findViewById(R.id.editTextAuthor);
        holder = findViewById(R.id.editTextHolder);
        book_name = findViewById(R.id.editTextTextPersonName);
        ISBN = findViewById(R.id.editTextISBN);

        cancelButton = findViewById(R.id.btn_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button borrow = findViewById(R.id.borrow_book);
        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanISBN.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                ISBN_ok = data.getStringExtra("ISBN_READ");
                lend = true;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
