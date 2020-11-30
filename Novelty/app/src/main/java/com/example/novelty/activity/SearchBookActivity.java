package com.example.novelty.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchBookActivity extends AppCompatActivity {
    List<BookBean> mBookList = new ArrayList<>();
    SearchView searchView;
    ListView mListView;
    BookAdapter adapter;
    CollectionReference bookInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        searchView = findViewById(R.id.searchView);
        searchView.onActionViewExpanded();
        searchView.clearFocus();
        searchView.setIconifiedByDefault(false);
        mListView = findViewById(R.id.listBook);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        adapter = new BookAdapter(mBookList, this, new BookAdapter.FilterListener() {
            @Override
            public void getFilterData(List<BookBean> list) {
                setItemClick(list);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogShow(mBookList.get(i));

            }
        });
        mListView.setAdapter(adapter);
        bookInfo = Database.getBookInfo(uid);
        bookInfo.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    String ibsn = (String) documentSnapshot.get("ISBN");
                    Database.getBookRef(bookInfo, ibsn).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot result = task.getResult();
                            Map<String, Object> data = result.getData();
                            mBookList.add(new BookBean(data.get("book_name").toString(), data.get("description").toString(), "request", data.get("author").toString()));
                        }
                    });
                }
                adapter.notifyDataSetChanged();
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
                if (s.length() == 0) {

                    adapter.setdata(mBookList);
                }
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

    private void dialogShow(final BookBean bookBean) {
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
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Database.userRequestRef(uid).document(uid).set(bookBean).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SearchBookActivity.this, "add success", Toast.LENGTH_SHORT).show();
                    }
                });

                Notification notification = new Notification.Builder(SearchBookActivity.this)
                          .setContentTitle("message")
                          .setContentText("request send")
                          .setSmallIcon(R.mipmap.ic_launcher)
                          .build();
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
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