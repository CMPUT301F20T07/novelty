package com.example.novelty.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.novelty.R;
import com.example.novelty.bean.BookBean;
import com.example.novelty.bean.RequestBean;

import java.util.ArrayList;
import java.util.List;

public class RequetsBookAdapter extends BaseAdapter {

    private List<BookBean> list = new ArrayList<BookBean>();
    private Context context;

    public RequetsBookAdapter(List<BookBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_search, null);
            holder = new ViewHolder();
            holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            holder.tv_owner = (TextView) convertView.findViewById(R.id.tv_owner);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_author.setText("status:" + "request");
        holder.tv_title.setText("bookName:" + list.get(position).getTitle());
        holder.tv_description.setText("owner:" + list.get(position).getOwner());
        holder.tv_owner.setText("description:" + list.get(position).getDescription());
        return convertView;
    }


    class ViewHolder {
        TextView tv_title, tv_author, tv_description, tv_owner;
    }

}

