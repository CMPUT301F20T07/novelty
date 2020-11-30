package com.example.novelty.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.novelty.R;
import com.example.novelty.adapter.RequetsAdapter;
import com.example.novelty.adapter.RequetsBookAdapter;
import com.example.novelty.adapter.UserAdapter;
import com.example.novelty.bean.BookBean;
import com.example.novelty.bean.RequestBean;
import com.example.novelty.bean.UserBean;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyAcceptActivity extends AppCompatActivity {
    ListView listView;
    List<BookBean> mBook = new ArrayList<>();
    List<UserBean> userBeans = new ArrayList<>();
    CollectionReference requestRef;
    RequetsBookAdapter requetsBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        listView = findViewById(R.id.listRequest);
        requetsBookAdapter =new RequetsBookAdapter(mBook, this);
        listView.setAdapter(requetsBookAdapter);


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        requestRef = Database.userRequestRef(uid);
        requestRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    String Owner = (String) documentSnapshot.get("Owner");
                    String description = (String) documentSnapshot.get("description");
                    String status = (String) documentSnapshot.get("status");
                    String title = (String) documentSnapshot.get("title");
                    mBook.add(new BookBean(title, status, Owner, description));
                }
                requetsBookAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        dialogShow(userBeans);
                    }
                });

            }
        });
    }

    private void dialogShow(List<UserBean> bookBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.request_book_dialog, null);
        ListView listview = (ListView) v.findViewById(R.id.listview);
        UserAdapter userAdapter = new UserAdapter(bookBean, this);
        listview.setAdapter(userAdapter);

        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setGravity(Gravity.CENTER);
        userAdapter.setListener(new UserAdapter.Call() {
            @Override
            public void close() {
                dialog.dismiss();
            }
        });
    }

}