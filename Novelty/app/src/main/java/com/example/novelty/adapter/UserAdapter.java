package com.example.novelty.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.novelty.R;
import com.example.novelty.activity.MapActivity;
import com.example.novelty.bean.RequestBean;
import com.example.novelty.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {


    private List<UserBean> list = new ArrayList<UserBean>();
    private Context context;

    public UserAdapter(List<UserBean> list, Context context) {
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
            holder.yes = (ImageView) convertView.findViewById(R.id.yes);
            holder.no = (ImageView) convertView.findViewById(R.id.no);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_from.setText("username：" + list.get(position).getUsername());
        holder.tv_status.setText("phone：" + list.get(position).getPhone());

        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MapActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }



    class ViewHolder {
        TextView tv_status, tv_from;
        ImageView yes, no;
    }

}

