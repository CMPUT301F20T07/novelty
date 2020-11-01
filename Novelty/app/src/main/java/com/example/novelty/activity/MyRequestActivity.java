package com.example.novelty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.novelty.R;
import com.example.novelty.adapter.RequetsAdapter;
import com.example.novelty.bean.RequestBean;
import com.example.novelty.bean.UserBean;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MyRequestActivity extends AppCompatActivity {
    ListView listView;
    List<RequestBean> mRequest = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        listView = findViewById(R.id.listRequest);
        UserBean userBean = new UserBean("111111", "111111");
        RequestBean requested = new RequestBean("requested", userBean);
        UserBean userBean1 = new UserBean("22222", "2222222");
        RequestBean requested2 = new RequestBean("requested", userBean1);
        UserBean userBean3 = new UserBean("333333", "333333");
        RequestBean requested3 = new RequestBean("requested", userBean3);
        mRequest.add(requested);
        mRequest.add(requested2);
        mRequest.add(requested3);
        listView.setAdapter(new RequetsAdapter(mRequest, this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MyRequestActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}