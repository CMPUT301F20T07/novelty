package com.example.novelty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novelty.R;
import com.example.novelty.bean.BookBean;
import com.example.novelty.adapter.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchBookActivity extends AppCompatActivity {
    List<BookBean> mBookList = new ArrayList<>();
    SearchView searchView;
    ListView mListView;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        searchView = findViewById(R.id.searchView);
        searchView.onActionViewExpanded();
        searchView.setIconifiedByDefault(false);
        mListView = findViewById(R.id.listBook);
        mBookList.add(new BookBean("cinder","hexingjie","this is "));
        mBookList.add(new BookBean("cinder1","hexingjie1","this is2 "));
        mBookList.add(new BookBean("cinder2","hexingjie4","this is3 "));


        adapter = new BookAdapter(mBookList, this, new BookAdapter.FilterListener() {
            @Override
            public void getFilterData(List<BookBean> list) {
                setItemClick(list);

            }
        });


        mListView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    protected void setItemClick(final List<BookBean> filter_lists) {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                dialogShow(filter_lists.get(position));
            }
        });
    }


    private void dialogShow(BookBean bookBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.book_details_dialog, null);
        Button btn_cancel = (Button) v.findViewById(R.id.btn_back);
        Button btn_sure = (Button) v.findViewById(R.id.btn_request);
        TextView tv_description = (TextView) v.findViewById(R.id.tv_description);
        TextView tv_owner = (TextView) v.findViewById(R.id.tv_owner);
        TextView tv_book_title = (TextView) v.findViewById(R.id.tv_book_title);


        tv_book_title.setText(bookBean.getTitle());
        tv_description.setText(bookBean.getDescription());
        tv_owner.setText(bookBean.getOwner());
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setGravity(Gravity.CENTER);
        btn_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(SearchBookActivity.this, "ok", Toast.LENGTH_LONG).show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                Toast.makeText(SearchBookActivity.this, "no", Toast.LENGTH_LONG).show();
            }
        });
    }


}