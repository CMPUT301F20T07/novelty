package com.example.novelty.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.novelty.R;
import com.example.novelty.adapter.CustomList_Book;
import com.example.novelty.adapter.NestedListView;
import com.example.novelty.bean.BookBean;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int VIEW_EDIT_BOOK = 111;
    public static final int ADD_BOOK = 112;

    private String userID;

    FloatingActionButton fab_scan, fab_lend, fab_return, fab_view, fab_confirm;
    Animation fabOpen, fabClose, fabRClockwise, fabRCounterClockwise;

    NestedListView bookList;
    ArrayList<BookBean> bookDataList;
    ArrayAdapter<BookBean> bookAdapter;

    int select_pos = -1;

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

        bookList = findViewById(R.id.mainscreen_list);
        bookDataList = new ArrayList<>();
        bookAdapter = new CustomList_Book(this, bookDataList);

        bookList.setAdapter(bookAdapter);
        bookList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //test==========================================
        BookBean book1 = new BookBean("Book 1");
        BookBean book2 = new BookBean("Book 2");
        BookBean book3 = new BookBean("Book 3");
        BookBean book4 = new BookBean("Book 4");
        BookBean book5 = new BookBean("Book 5");
        BookBean book6 = new BookBean("Book 6");
        BookBean book7 = new BookBean("Book 7");
        BookBean book8 = new BookBean("Book 8");
        BookBean book9 = new BookBean("Book 9");
        BookBean book10 = new BookBean("Book 10");
        BookBean book11 = new BookBean("Book 11");
        BookBean book12 = new BookBean("Book 12");
        BookBean book13 = new BookBean("Book 13");
        BookBean book14 = new BookBean("Book 14");
        BookBean book15 = new BookBean("Book 15");

        BookBean bookTest = new BookBean("Test View Edit");
        bookTest.setAuthor("David");
        bookTest.setOwner("Mario");
        bookTest.setDescription("This is a Description `````````````````````````````````````````````");

        bookDataList.add(book1);
        bookDataList.add(book2);
        bookDataList.add(book3);
        bookDataList.add(book4);
        bookDataList.add(book5);

        bookDataList.add(bookTest);

        bookDataList.add(book6);
        bookDataList.add(book7);
        bookDataList.add(book8);
        bookDataList.add(book9);
        bookDataList.add(book10);
        bookDataList.add(book11);
        bookDataList.add(book12);
        bookDataList.add(book13);
        bookDataList.add(book14);
        bookDataList.add(book15);

        bookAdapter.notifyDataSetChanged();


        //test===============================================


        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                select_pos = position;

                BookBean book = bookDataList.get(position);

                Intent viewEditIntent = new Intent(MainActivity.this, ViewEditBook.class);
                viewEditIntent.putExtra("book", book);
                startActivityForResult(viewEditIntent, VIEW_EDIT_BOOK);
            }
        });


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

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the FilterDialog class to show the dialog fragment
                new FilterDialog().show(getSupportFragmentManager(), "filterdialog");

            }
        });

        bottomAppBar.setOnMenuItemClickListener(new BottomAppBar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuAdd:
                        Intent addIntent = new Intent(MainActivity.this, AddBook.class);
                        startActivityForResult(addIntent, ADD_BOOK);
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
                Intent lend_intent = new Intent(getApplicationContext(), LendBook.class);
                startActivityForResult(lend_intent, 1);
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
                Intent lend_intent = new Intent(getApplicationContext(), LendBook.class);
                startActivityForResult(lend_intent, 1);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case VIEW_EDIT_BOOK:
                if (resultCode == 2) {
                    Bundle bundle = data.getBundleExtra("bundle");
                    bookDataList.get(select_pos).setTitle(bundle.getString("Title"));
                    bookDataList.get(select_pos).setAuthor(bundle.getString("Author"));
                    bookDataList.get(select_pos).setISBN(bundle.getString("ISBN"));
                    bookDataList.get(select_pos).setDescription(bundle.getString("Description"));
                    bookDataList.get(select_pos).setHolder(bundle.getString("Holder"));
                    bookDataList.get(select_pos).setStatus(bundle.getString("Status"));
                    bookAdapter.notifyDataSetChanged();
                }
                if (resultCode == 4) {
                    bookDataList.remove(select_pos);
                    bookAdapter.notifyDataSetChanged();
                }
                select_pos = -1;
                break;

            case ADD_BOOK:
                if (resultCode == 2) {
                    Bundle bundle = data.getBundleExtra("bundle");
                    BookBean newBook = new BookBean(bundle.getString("Title"));
                    newBook.setAuthor(bundle.getString("Author"));
                    newBook.setISBN(bundle.getString("ISBN"));
                    newBook.setDescription(bundle.getString("Description"));
                    newBook.setHolder(bundle.getString("Holder"));
                    newBook.setStatus(bundle.getString("Status"));

                    bookDataList.add(newBook);
                    bookAdapter.notifyDataSetChanged();

                }
                select_pos = -1;
                break;

            default:
                break;
        }
    }


    /*
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;
        while (baos.toByteArray().length / 1024 > 50) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
     */

}