package com.example.novelty.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.novelty.R;
import com.example.novelty.adapter.RequetsAdapter;
import com.example.novelty.bean.RequestBean;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyRequestActivity extends AppCompatActivity {
    ListView listView;
    List<RequestBean> mRequest = new ArrayList<>();
    CollectionReference requestRef;
    RequetsAdapter requetsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        listView = findViewById(R.id.listRequest);
        requetsAdapter = new RequetsAdapter(mRequest, this);
        listView.setAdapter(requetsAdapter);
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
                    mRequest.add(new RequestBean(title, status, Owner, description));
                }
                requetsAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RequestBean requestBean = mRequest.get(i);
                Intent intent = new Intent(MyRequestActivity.this, RequestDetailsActivity.class);
                intent.putExtra("Owner",requestBean.getOwer());
                intent.putExtra("description",requestBean.getDescription());
                intent.putExtra("status",requestBean.getStatus());
                intent.putExtra("title",requestBean.getBook());
                startActivity(intent);
            }
        });
    }
}