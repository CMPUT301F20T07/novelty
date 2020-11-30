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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.novelty.R;
import com.example.novelty.adapter.RequetsAdapter;
import com.example.novelty.adapter.RequetsBookAdapter;
import com.example.novelty.adapter.UserAdapter;
import com.example.novelty.bean.BookBean;
import com.example.novelty.bean.RequestBean;
import com.example.novelty.bean.UserBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAcceptActivity extends AppCompatActivity {
    ListView listView;
    List<BookBean> mBook = new ArrayList<>();
    List<UserBean> userBeans = new ArrayList<>();
    CollectionReference requestRef;
    RequetsBookAdapter requetsBookAdapter;
    private FirebaseAuth fAuth; //provided by firebase used to register the user.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        listView = findViewById(R.id.listRequest);
        requetsBookAdapter = new RequetsBookAdapter(mBook, this);
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
                    mBook.add(new BookBean(title, description, "request", Owner));
                }
                requetsBookAdapter.notifyDataSetChanged();
            }
        });

        fAuth = FirebaseAuth.getInstance(); // gets current instance of database from firebase to perform operations
        String userID = fAuth.getCurrentUser().getUid();

        Database.getUserRef(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> data = result.getData();
                String username = (String) data.get("username");
                String phone = (String) data.get("phone");
                userBeans.add(new UserBean(username, phone));
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