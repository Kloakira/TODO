package com.njupt.b16070706.todo.listview;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.njupt.b16070706.todo.R;

import java.util.LinkedList;

public class MyListAdapter extends BaseAdapter {

    private LinkedList<Items> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public MyListAdapter(LinkedList<Items> Data, Context context) {
        this.mData = Data;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView tvTitle;
        public Button btnAdd;
        public Button btnDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_one_item, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.iv);
            holder.tvTitle = convertView.findViewById(R.id.tv_title);
            holder.btnAdd = convertView.findViewById(R.id.btn_add);
            holder.btnDelete = convertView.findViewById(R.id.btn_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /**
         * 给控件赋值*/
        holder.tvTitle.setText(mData.get(position).getTodo());
        /**
         * 添加一条TODO*/
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Items item = new Items();
                mData.add(item);
                notifyDataSetChanged();
                }
        });


        /**
         * 删除一条TODO*/
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mData != null) {
                    mData.remove(position);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

}

