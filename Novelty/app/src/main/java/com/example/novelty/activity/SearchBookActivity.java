package com.example.novelty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
        mBookList.add(new BookBean("To Kill a Mockingbird", "when I resent the size of my unbounded set, I want more numbers than I’m likely to get, and God", "request", "John Green"));
        mBookList.add(new BookBean("The Silent Patient", "There are infinite numbers between 0 and 1,You gave me a forever within the numbered days", "request ", "John Green"));
        mBookList.add(new BookBean("Tuesdays with Morrie", "It was Tuesday", "request", "Mitch Albom"));
        mBookList.add(new BookBean("Sweetbitter", "Bitter, always a bit anticipated.The mouth still hesitates at each new encounter.We urge it forward, say, Adapt. Now, enjoy it.", "this is36", "Stephanie Danler"));
        mBookList.add(new BookBean("Lord of the flies", "Funerals are not for the dead,they are for the living.", "request", "William Golding"));


        adapter = new BookAdapter(mBookList, this, new BookAdapter.FilterListener() {
            @Override
            public void getFilterData(List<BookBean> list) {
                setItemClick(list);

            }
        });


        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogShow(mBookList.get(i));

            }
        });
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
        tv_owner.setText("ownerd by" + bookBean.getOwner());
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setGravity(Gravity.CENTER);
        btn_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(SearchBookActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }


}