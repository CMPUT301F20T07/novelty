package com.example.novelty.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.novelty.R;
import com.example.novelty.bean.BookBean;

public class BookAdapter extends BaseAdapter implements Filterable {

    private List<BookBean> list = new ArrayList<BookBean>();
    private Context context;
    private MyFilter filter = null;
    private FilterListener listener = null;

    public BookAdapter(List<BookBean> list, Context context, FilterListener filterListener) {
        this.list = list;
        this.context = context;
        this.listener = filterListener;
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
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_author.setText(list.get(position).getAuthor());
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_description.setText(list.get(position).getDescription());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new MyFilter(list);
        }
        return filter;
    }

    class MyFilter extends Filter {

        private List<BookBean> original = new ArrayList<BookBean>();

        public MyFilter(List<BookBean> list) {
            this.original = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (TextUtils.isEmpty(constraint)) {
                results.values = original;
                results.count = original.size();
            } else {
                List<BookBean> mList = new ArrayList<BookBean>();
                for (BookBean s : original) {
                    if (s.getDescription().trim().toLowerCase().contains(constraint.toString().trim().toLowerCase())) {
                        mList.add(s);
                    }
                }
                results.values = mList;
                results.count = mList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            list = (List<BookBean>) results.values;
            if (listener != null) {
                listener.getFilterData(list);
            }
            notifyDataSetChanged();
        }

    }

    class ViewHolder {
        TextView tv_title, tv_author, tv_description;
    }

    public interface FilterListener {
        void getFilterData(List<BookBean> list);
    }
}

