package com.example.novelty.adapter;


import android.content.Context;
import android.text.TextUtils;
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

public class RequetsAdapter extends BaseAdapter {

    private List<RequestBean> list = new ArrayList<RequestBean>();
    private Context context;

    public RequetsAdapter(List<RequestBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_request, null);
            holder = new ViewHolder();
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_from = (TextView) convertView.findViewById(R.id.tv_from);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_from.setText(list.get(position).getFrom().getUsername());
        holder.tv_status.setText(list.get(position).getStatus());
        return convertView;
    }


    class ViewHolder {
        TextView tv_status, tv_from;
    }

}

