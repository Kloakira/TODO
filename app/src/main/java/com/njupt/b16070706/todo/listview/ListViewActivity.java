package com.njupt.b16070706.todo.listview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;



import com.njupt.b16070706.todo.R;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;


import java.util.LinkedList;
import java.util.List;
public class ListViewActivity extends AppCompatActivity{

    private List<Items> mData = null;
    private Context mContext;
    private ListView mLv1;
    private MyListAdapter mAdapter = null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        RotatingText();
        mContext = ListViewActivity.this;
        mData = new LinkedList<Items>();//添加课程条目
        mData.add(new Items("移动终端开发"));
        mAdapter = new MyListAdapter((LinkedList<Items>) mData,mContext);
        mLv1 = findViewById(R.id.lv_1);
        mLv1.setAdapter(mAdapter);
        mLv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//监听鼠标点击及弹出对应对话框
                        final AlertDialog.Builder normalDialog =
                                new AlertDialog.Builder(ListViewActivity.this);
                        normalDialog.setMessage("移动终端开发课程编号：B1801581C\n" + "学分数：2.0\n" + "授课老师：王亚石\n" + "上课时间：周四67\n" + "教室：4-308");
                        normalDialog.show();
            }
        });

    }

    //上方的滚动字幕
    public void RotatingText () {
        RotatingTextWrapper rotatingTextWrapper = findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(20);

        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 2000, "Make Your Todo Now");

        rotatable.setSize(20);
        rotatable.setAnimationDuration(500);

        rotatingTextWrapper.setContent("       YO!    ", rotatable);
    }


}