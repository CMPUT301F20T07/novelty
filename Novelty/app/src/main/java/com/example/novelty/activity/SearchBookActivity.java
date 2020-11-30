package com.example.novelty.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
            public void onClick(View view) {
                dialog.dismiss();
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(SearchBookActivity.this)//此处会有中间一道线，并不影响运行，这是android系统版本的问题
                          .setContentTitle("request success")  //显示通知的标题
                          .setContentText("books")//显示消息通知的内容
                          .setWhen(System.currentTimeMillis())//显示通知的具体时间
                          .setSmallIcon(R.mipmap.ic_launcher)//这里设置显示的是手机顶部系统通知的应用图标
                          .setLargeIcon(BitmapFactory.decodeResource(SearchBookActivity.this.getResources(), R.mipmap.ic_launcher))//这里设置显示的是下拉通知栏后显示的系统图标
                          //.setAutoCancel(true)//可以在此使用此方法，点击通知后，通知内容自动取消,也可以在NotificationActivity.java中设置方法取消显示通知内容
                          .setVibrate(new long[]{0, 1000, 1000, 1000})//设置发出通知后震动一秒，停止一秒后再震动一秒，需要在manifest.xml中设置权限
                          .build();
                manager.notify(1, notification);
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