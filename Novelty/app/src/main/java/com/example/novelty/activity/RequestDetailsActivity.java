package com.example.novelty.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novelty.R;
import com.example.novelty.adapter.BookAdapter;
import com.example.novelty.bean.BookBean;

import java.util.ArrayList;
import java.util.List;

public class RequestDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        TextView book = findViewById(R.id.tv_book_name);
        TextView tv_au = findViewById(R.id.tv_au);
        TextView rule = findViewById(R.id.rule);
        TextView tv_description = findViewById(R.id.tv_description);
        book.setText(getIntent().getStringExtra("title"));
        tv_au.setText(getIntent().getStringExtra("Owner"));
        tv_description.setText(getIntent().getStringExtra("description"));
        rule.setText(getIntent().getStringExtra("status"));
    }

}