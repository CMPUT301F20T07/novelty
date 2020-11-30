package com.example.novelty.adapter;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.example.novelty.R;
import com.example.novelty.activity.MapActivity;
import com.example.novelty.activity.MyRequestActivity;
import com.example.novelty.bean.RequestBean;
import com.example.novelty.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class UserAdapter extends BaseAdapter {


    private List<UserBean> list = new ArrayList<UserBean>();
    private Context context;

private Call mcall;
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
                mcall.close();
                Intent intent = new Intent(context, MyRequestActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(context)//此处会有中间一道线，并不影响运行，这是android系统版本的问题
                          .setContentTitle("refuse request")  //显示通知的标题
                          .setContentText("books")//显示消息通知的内容
                          .setWhen(System.currentTimeMillis())//显示通知的具体时间
                          .setSmallIcon(R.mipmap.ic_launcher)//这里设置显示的是手机顶部系统通知的应用图标
                          .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))//这里设置显示的是下拉通知栏后显示的系统图标
                          //.setAutoCancel(true)//可以在此使用此方法，点击通知后，通知内容自动取消,也可以在NotificationActivity.java中设置方法取消显示通知内容
                          .setVibrate(new long[]{0, 1000, 1000, 1000})//设置发出通知后震动一秒，停止一秒后再震动一秒，需要在manifest.xml中设置权限
                          .build();
                manager.notify(1, notification);
            }
        });

        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcall.close();
                Intent intent = new Intent(context, MyRequestActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(context)//此处会有中间一道线，并不影响运行，这是android系统版本的问题
                          .setContentTitle("accept success")  //显示通知的标题
                          .setContentText("books")//显示消息通知的内容
                          .setWhen(System.currentTimeMillis())//显示通知的具体时间
                          .setSmallIcon(R.mipmap.ic_launcher)//这里设置显示的是手机顶部系统通知的应用图标
                          .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))//这里设置显示的是下拉通知栏后显示的系统图标
                          //.setAutoCancel(true)//可以在此使用此方法，点击通知后，通知内容自动取消,也可以在NotificationActivity.java中设置方法取消显示通知内容
                          .setVibrate(new long[]{0, 1000, 1000, 1000})//设置发出通知后震动一秒，停止一秒后再震动一秒，需要在manifest.xml中设置权限
                          .build();
                manager.notify(1, notification);
                Intent intent1 = new Intent(context, MapActivity.class);
                context.startActivity(intent1);
            }
        });

        return convertView;
    }

    public void setListener(Call call){
        this.mcall=call;
    }
    class ViewHolder {
        TextView tv_status, tv_from;
        ImageView yes, no;
    }
    public interface Call{
        public void close();
    }
}

