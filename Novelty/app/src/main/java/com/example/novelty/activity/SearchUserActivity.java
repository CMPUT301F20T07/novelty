package com.example.novelty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.novelty.R;
import com.example.novelty.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class SearchUserActivity extends AppCompatActivity {
    private SearchView mSearchView;
    private ListView mListView;
    private TextView username;
    private TextView phone,tv_username,tv_phone;
    List<UserBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        mSearchView = findViewById(R.id.searchView);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        tv_phone = findViewById(R.id.tv_phone);
        tv_username = findViewById(R.id.tv_username);

        list.add(new UserBean("sam","110"));
        list.add(new UserBean("jay","130"));
        list.add(new UserBean("user11","5062222"));
        list.add(new UserBean("user222","44444"));
        mSearchView.onActionViewExpanded();
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                for (int i = 0; i < list.size(); i++)
                    if (list.get(i).getUsername().contains(query)) {
                        username.setText(list.get(i).getUsername());
                        phone.setText(list.get(i).getPhone());
                        tv_phone.setVisibility(View.VISIBLE);
                        tv_username.setVisibility(View.VISIBLE);
                    }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });
    }

}