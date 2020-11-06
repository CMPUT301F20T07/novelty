package com.example.novelty.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.novelty.R;

public class RecieveBook extends MainActivity {
    private boolean returned;
    String ISBN_expected = "1-56619-909-3";
    String ISBN_ok;//receive the ISBN of the book from the database for now it is
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recieve_book);
        Button recieve = findViewById(R.id.recieve_book);
        TextView ISBN_display = findViewById(R.id.ISBN_expected);
        ISBN_display.setText(ISBN_expected);
        recieve.setOnClickListener(new View.OnClickListener() {
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
                if (ISBN_expected == ISBN_ok){
                    returned = true;
                }
                else {
                    returned = false;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
