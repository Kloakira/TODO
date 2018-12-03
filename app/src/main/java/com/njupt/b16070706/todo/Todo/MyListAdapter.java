package com.njupt.b16070706.todo.Todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.njupt.b16070706.todo.Data.Items;
import com.njupt.b16070706.todo.Data.GlobalUtil;
import com.njupt.b16070706.todo.Operations.OnDoubleClickListener;
import com.njupt.b16070706.todo.R;

import java.util.LinkedList;

public class MyListAdapter extends BaseAdapter {

    private LinkedList<Items> items;
    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public MyListAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setItems(LinkedList<Items> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**一条item的中的部件：内容和删除按钮*/
    static class ViewHolder {
        public TextView tvTodo;

        public ViewHolder(View itemView, Items item) {
            tvTodo = itemView.findViewById(R.id.tv_todo);

            tvTodo.setText(item.getTodo());

        }
    }


    /**listview里获取一条item的样式*/
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_one_item, null);

            Items item = (Items) getItem(position);
            holder = new ViewHolder(convertView, item);

            //  holder.tvTitle = convertView.findViewById(R.id.tv_title);
            //  holder.btnDelete = convertView.findViewById(R.id.btn_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /**删除逻辑：这里实现点击delete按钮后删除一条item*/
        final Items selectedItem = items.get(position);
        holder.tvTodo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(items != null) {
                    String uuid = selectedItem.getUuid();
                    items.remove(position);
                    GlobalUtil.getInstance().databaseHelper.removeTodo(uuid);
                    //这里可能有错
                }else{
                    notifyDataSetChanged();
                    return false;
                }
                notifyDataSetChanged();
                return true;
            }
        });
        holder.tvTodo.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick() {
                if(items != null) {
                    String uuid = selectedItem.getUuid();
                    items.remove(position);
                    GlobalUtil.getInstance().databaseHelper.removeTodo(uuid);
                    //这里可能有错
                }else{
                    notifyDataSetChanged();

                }
                notifyDataSetChanged();
            }
        }));
        return convertView;
    }
}


