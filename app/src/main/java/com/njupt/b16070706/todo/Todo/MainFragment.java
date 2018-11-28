package com.njupt.b16070706.todo.Todo;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.njupt.b16070706.todo.Data.Items;
import com.njupt.b16070706.todo.Data.GlobalUtil;
import com.njupt.b16070706.todo.R;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import java.util.LinkedList;


@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {

    private View rootView;
    private TextView textView;
    private ListView listView;

    private MyListAdapter myListAdapter;

    private static LinkedList<Items> items = new LinkedList<>();

    private String date;


    @SuppressLint("ValidFragment")
    public MainFragment(String date){
        this.date = date;

        if(GlobalUtil.getInstance().databaseHelper == null)
        {
            //因为Adapter中还未添加，所以事先增加演示(未必需要)
            items.add(new Items());
        } else{
            //将数据库中新建的fragment的date数据传递给items
            items = GlobalUtil.getInstance().databaseHelper.readTodo(date);
        }

    }

    /**滚动字幕于此实现
     * 调用设定fragment内容的方法*/
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main,container,false);
        //滚动字幕
        //RotatingText();
        initView();

        return rootView;
    }

    /**设定fragment里的内容，即 时期 和 todoList*/
    private void initView(){
        //绑定
        textView = rootView.findViewById(R.id.day_text);//显示日期
        listView = rootView.findViewById(R.id.list_view);//每一条Todo

        //textView设为新建fragment时传递进来的日期
        textView.setText(date);

        //将items传递进Adapter中
        myListAdapter = new MyListAdapter(getContext());
        reload();
    }
    public static LinkedList<Items> getItems() {
        return items;
    }
    /**最后一个fragment中的listview添加item时使用*/
    public void addItem(Items item) {
        items.add(item);
    }

    public String getDate() {
        return date;
    }
    /**单个fragment重新加载（刷新逻辑）
     * 两次调用：
     * 第一次：新建一个fragment的时候
     * 第二次：更新items内容的时候*/
    public void reload() {
        //数据库中读取todo条目
        items = GlobalUtil.getInstance().databaseHelper.readTodo(date);
        //将items传递进Adapter中
        myListAdapter.setItems(items);
        //listView绑定myListAdapter
        listView.setAdapter(myListAdapter);
        //有todo的时候 “no to——do today”不显示
        if(myListAdapter.getCount() > 0){
            rootView.findViewById(R.id.no_todo).setVisibility(View.INVISIBLE);
        }
    }

    /**上方的滚动字幕
     * 单纯为了花里花哨*/
    public void RotatingText () {
        RotatingTextWrapper rotatingTextWrapper = rootView.findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(20);

        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 2000, "Make Your Todo Now               ");

        rotatable.setSize(20);
        rotatable.setAnimationDuration(500);

        rotatingTextWrapper.setContent(" YO!    ", rotatable);
    }


}
