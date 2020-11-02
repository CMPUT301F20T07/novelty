package com.example.novelty.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelty.R;
import com.example.novelty.adapter.RequetsAdapter;
import com.example.novelty.adapter.RequetsBookAdapter;
import com.example.novelty.bean.BookBean;
import com.example.novelty.bean.RequestBean;
import com.example.novelty.bean.UserBean;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class RequstBooksActivity extends AppCompatActivity {
    ListView listView;
    List<BookBean> mBook = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        listView = findViewById(R.id.listRequest);
        mBook.add(new BookBean("title"));
        mBook.add(new BookBean("city"));
        
        mBook.add(new BookBean("cinder"));
        listView.setAdapter(new RequetsBookAdapter(mBook, this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogShow(mBook.get(i).getRequests());
            }
        });
    }

    private void dialogShow(List<RequestBean> bookBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.request_book_dialog, null);
        ListView listview = (ListView) v.findViewById(R.id.listview);
        listview.setAdapter(new RequetsAdapter(bookBean, RequstBooksActivity.this));
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setGravity(Gravity.CENTER);

    }


}