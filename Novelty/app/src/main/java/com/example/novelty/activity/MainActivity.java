package com.example.novelty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.novelty.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_scan, fab_lend, fab_return, fab_view, fab_confirm;
    Animation fabOpen, fabClose, fabRClockwise, fabRCounterClockwise;

    boolean isOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_scan = findViewById(R.id.flt_scan);
        fab_lend = findViewById(R.id.flt_lend);
        fab_return = findViewById(R.id.flt_return);
        fab_view = findViewById(R.id.flt_view);
        fab_confirm = findViewById(R.id.flt_confirm);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRCounterClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_counterclockwise);

        fab_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOpen) {

                    fab_view.startAnimation(fabClose);
                    fab_lend.startAnimation(fabClose);
                    fab_return.startAnimation(fabClose);
                    fab_confirm.startAnimation(fabClose);
                    fab_scan.startAnimation(fabRClockwise);

                    fab_view.setClickable(false);
                    fab_lend.setClickable(false);
                    fab_return.setClickable(false);
                    fab_confirm.setClickable(false);

                    isOpen = false;
                } else {

                    fab_view.startAnimation(fabOpen);
                    fab_lend.startAnimation(fabOpen);
                    fab_return.startAnimation(fabOpen);
                    fab_confirm.startAnimation(fabOpen);
                    fab_scan.startAnimation(fabRCounterClockwise);

                    fab_view.setClickable(true);
                    fab_lend.setClickable(true);
                    fab_return.setClickable(true);
                    fab_confirm.setClickable(true);

                    isOpen = true;

                }
            }
        });


        fab_lend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked on lend book", Toast.LENGTH_SHORT).show();
            }
        });

        fab_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked on return book", Toast.LENGTH_SHORT).show();
            }
        });

        fab_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked on view book details", Toast.LENGTH_SHORT).show();
            }
        });

        fab_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked on confirm", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //send user to Login activity once logged out.
    public void logout (View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent (getApplicationContext(), MyRequestActivity.class));
        finish(); //
    }
}