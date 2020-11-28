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
        List<UserBean> userBeans = new ArrayList<>();

        UserBean userBean = new UserBean("111111", "111111");
        UserBean userBean1 = new UserBean("22222", "2222222");
        UserBean userBean3 = new UserBean("333333", "333333");
        UserBean userBean4 = new UserBean("444444", "4444444");

        userBeans.add(userBean);
        userBeans.add(userBean1);
        userBeans.add(userBean3);
        userBeans.add(userBean4);
        RequestBean requested = new RequestBean("requested", userBeans);
        RequestBean requested2 = new RequestBean("requested", userBeans);
        RequestBean requested3 = new RequestBean("requested", userBeans);
        RequestBean requested4 = new RequestBean("requested", userBeans);
        RequestBean requested5 = new RequestBean("requested", userBeans);
        mRequest.add(requested);
        mRequest.add(requested2);
        mRequest.add(requested3);
        mRequest.add(requested4);
        mRequest.add(requested5);
        listView.setAdapter(new RequetsAdapter(mRequest, this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyRequestActivity.this, RequestDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}