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

import androidx.appcompat.app.AppCompatActivity;

import com.example.novelty.R;
import com.example.novelty.adapter.RequetsAdapter;
import com.example.novelty.adapter.RequetsBookAdapter;
import com.example.novelty.adapter.UserAdapter;
import com.example.novelty.bean.BookBean;
import com.example.novelty.bean.RequestBean;
import com.example.novelty.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class MyAcceptActivity extends AppCompatActivity {
    ListView listView;
    List<BookBean> mBook = new ArrayList<>();
    List<UserBean> userBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        listView = findViewById(R.id.listRequest);
        mBook.add(new BookBean("To Kill a Mockingbird", "when I resent the size of my unbounded set, I want more numbers than I’m likely to get, and God", "request", "Jhon Green"));
        mBook.add(new BookBean("The Silent Patient", "There are infinite numbers between 0 and 1,You gave me a forever within the numbered days", "request ", "John Green"));
        mBook.add(new BookBean("Tuesdays with Morrie", "It was Tuesday", "request", "Mitch Albom"));
        mBook.add(new BookBean("Sweetbitter", "Bitter, always a bit anticipated.The mouth still hesitates at each new encounter.We urge it forward, say, Adapt. Now, enjoy it.", "this is36", "Stephanie Danler"));
        mBook.add(new BookBean("Lord of the flies", "Funerals are not for the dead,they are for the living.", "request", "William Golding"));


        UserBean userBean = new UserBean("lihua", "010203455");
        UserBean userBean1 = new UserBean("yuan", "7323453");
        UserBean userBean3 = new UserBean("blue li", "134t454323");
        UserBean userBean4 = new UserBean("jack", "10334535");
        userBeans.add(userBean);
        userBeans.add(userBean1);
        userBeans.add(userBean3);
        userBeans.add(userBean4);
        listView.setAdapter(new RequetsBookAdapter(mBook, this));
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