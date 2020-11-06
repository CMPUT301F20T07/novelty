package com.example.novelty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.novelty.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab_scan, fab_lend, fab_return, fab_view, fab_confirm;
    Animation fabOpen, fabClose, fabRClockwise, fabRCounterClockwise;

    private DrawerLayout drawerlayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private Toolbar mToolbar;

    boolean isOpen = false;
    private Intent mIntent;

    private BottomAppBar bottomAppBar;

    @SuppressLint("NewApi")
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


        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);


        drawerlayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);

        toggle = new ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close);

        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

        bottomAppBar = findViewById(R.id.bottomAppBar);

        bottomAppBar.setOnMenuItemClickListener(new BottomAppBar.OnMenuItemClickListener() {
                                                    @Override
                                                    public boolean onMenuItemClick(MenuItem item) {
                                                        switch (item.getItemId()) {
                                                            case R.id.menuAdd:
                                                                Intent addIntent = new Intent(MainActivity.this, ViewEditBook.class);
                                                                startActivity(addIntent);
                                                                break;
                                                            default:
                                                                break;
                                                        }
                                                        return true;
                                                    }
                                                });


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
                Toast.makeText(MainActivity.this, "Lend book", Toast.LENGTH_SHORT).show();
            }
        });

        fab_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Return book", Toast.LENGTH_SHORT).show();
            }
        });

        fab_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "View book details", Toast.LENGTH_SHORT).show();
            }
        });

        fab_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Confirm", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //send user to Login activity once logged out.
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish(); //
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mIntent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_log_out:
                //send user to Login activity once logged out.
                Toast.makeText(MainActivity.this, "Log out", Toast.LENGTH_SHORT).show();
                logout(findViewById(R.id.nav_log_out));
                break;

            case R.id.nav_contact_info:
                Toast.makeText(MainActivity.this, "Contact information", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_search_book:
                mIntent.setClass(MainActivity.this, SearchBookActivity.class);
                startActivity(mIntent);
                break;
            case R.id.nav_search_user:
                mIntent.setClass(MainActivity.this, SearchUserActivity.class);
                startActivity(mIntent);
                break;
            case R.id.nav_my_request:
                mIntent.setClass(MainActivity.this, MyRequestActivity.class);
                startActivity(mIntent);
                break;

        }


        return true;
    }
}