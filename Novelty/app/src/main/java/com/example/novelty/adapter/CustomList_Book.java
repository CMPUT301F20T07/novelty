package com.example.novelty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.novelty.R;
import com.example.novelty.bean.BookBean;

import java.util.ArrayList;

public class CustomList_Book extends ArrayAdapter<BookBean> {


    private ArrayList<BookBean> books;
    private Context context;

    public CustomList_Book(Context context, ArrayList<BookBean> cities){
        super(context,0, cities);
        this.books = cities;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.listview_book_item, parent,false);
        }

        BookBean book = books.get(position);

        TextView bookName = view.findViewById(R.id.booklist_item);

        bookName.setText(book.getTitle());

        return view;

    }
}
