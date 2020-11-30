package com.example.novelty.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.novelty.R;
import com.example.novelty.bean.UserBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchUserActivity extends AppCompatActivity {
    private SearchView mSearchView;
    private ListView mListView;
    private TextView username;
    private TextView phone, tv_username, tv_phone;
    List<UserBean> list = new ArrayList<>();
    private FirebaseAuth fAuth; //provided by firebase used to register the user.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        mSearchView = findViewById(R.id.searchView);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phone);
        tv_phone = findViewById(R.id.tv_phone);
        tv_username = findViewById(R.id.tv_username);

//        list.add(new UserBean("sam", "110"));
//        list.add(new UserBean("jay", "130"));
//        list.add(new UserBean("user11", "5062222"));
//        list.add(new UserBean("user222", "44444"));
//        list.add(new UserBean("user230", "5555"));
//        list.add(new UserBean("user240", "666666"));
        fAuth = FirebaseAuth.getInstance(); // gets current instance of database from firebase to perform operations
        String userID = fAuth.getCurrentUser().getUid();

        Database.getUserRef(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot result = task.getResult();
                Map<String, Object> data = result.getData();
                String username = (String) data.get("username");
                String phone = (String) data.get("phone");
                list.add(new UserBean(username, phone));
            }
        });
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