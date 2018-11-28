package com.njupt.b16070706.todo.Todo;

import android.annotation.SuppressLint;
import android.os.Bundle;


import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.njupt.b16070706.todo.Data.DateUtil;
import com.njupt.b16070706.todo.Data.Items;
import com.njupt.b16070706.todo.Data.GlobalUtil;
import com.njupt.b16070706.todo.R;

public class TodoActivity extends AppCompatActivity {

    private EditText editText;
    private Button addButton;
    private ViewPager viewPager;
    private MainViewPagerAdapter pagerAdapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);


        //不让bar凸起
        getSupportActionBar().setElevation(0);
        //绑定viewPager这个部件，并将适配器装入
        viewPager = findViewById(R.id.view_pager);
        pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.notifyDataSetChanged();//提醒数据变更

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(pagerAdapter.getLastIndex());

        operations();



        //全局变量获取实例
        GlobalUtil.getInstance().setContext(getApplicationContext());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void operations() {
        editText = findViewById(R.id.et_todo);//填写todo的编辑框
        //addButton = findViewById(R.id.btn_add);//新建todo的按钮
        floatingActionButton = findViewById(R.id.float_button);

        handleFloatButton();
        //handleAddButton();
        handleEditText();


        //输入完成后让listview所在的部分获得焦点
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewPager.setFocusable(true);
                viewPager.setFocusableInTouchMode(true);
                viewPager.requestFocus();
                return false;
            }
        });
    }

    /**点击floatbutton时，新建一个Fragment,其中日期为当前日期*/
    public void handleFloatButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagerAdapter.setFragments(DateUtil.getFormattedDate());
                viewPager.setCurrentItem(pagerAdapter.getLastIndex());//让新建的fragment成为当前显示的窗口
            }
        });
    }
    /**点击add按钮时：将editText内容新建为新的item*/
    public void handleAddButton(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editText.getText())){
                    Items item = new Items();
                    item.setTodo(editText.getText());
                    editText.setText("");
                   /**两种添加item方式：
                    * 1.通过pagerAdapter的方法获取最后一个Fragment
                    * 再通过最后一个fragment的addItem方法添加一个item
                    * 2.通过pagerAdapter的reload方法遍历所有的Fragment
                    * 再通过单个fragment的reload方法添加一个item
                    *方法一：最后一个fragment中的listview添加item（我也不知道行不行）
                    pagerAdapter.getLastFragment().addItem(item);*/
                    //放入数据库
                    GlobalUtil.getInstance().databaseHelper.addTodo(item);
                    /**方法二*/
                    if(pagerAdapter.getLastFragment().getDate().equals(DateUtil.getFormattedDate()))
                    {
                        pagerAdapter.getLastFragment().reload();
                        pagerAdapter.notifyDataSetChanged();
                    }else{
                        pagerAdapter.setFragments(DateUtil.getFormattedDate());
                        viewPager.setCurrentItem(pagerAdapter.getLastIndex());
                        pagerAdapter.getLastFragment().reload();
                        pagerAdapter.notifyDataSetChanged();
                    }
                    Log.d("Log:","User Input is "+ item.getTodo());
                }else{
                    Toast.makeText(getApplicationContext(),"0A0",Toast.LENGTH_SHORT);
                }

            }
        });

    }
    /**在editText输入回车后直接新建新的item*/
    public void handleEditText(){
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() ==KeyEvent.ACTION_UP){

                    if(!TextUtils.isEmpty(editText.getText())){
                        //若内容非空，添加新的item
                        Items item = new Items();
                        item.setTodo(editText.getText());
                        editText.setText("");
                        /*//最后一个fragment中的listview添加item
                        pagerAdapter.getLastFragment().addItem(item);*/

                        //放入数据库
                        GlobalUtil.getInstance().databaseHelper.addTodo(item);
                        //如果最后一个fragment的时间不是今天，那就新建一个是今天的fragment再添加items
                        if(pagerAdapter.getLastFragment().getDate().equals(DateUtil.getFormattedDate()))
                        {
                            pagerAdapter.getLastFragment().reload();
                            pagerAdapter.notifyDataSetChanged();
                        }else{
                            pagerAdapter.setFragments(DateUtil.getFormattedDate());
                            viewPager.setCurrentItem(pagerAdapter.getLastIndex());
                            pagerAdapter.getLastFragment().reload();
                            pagerAdapter.notifyDataSetChanged();
                        }
                        Log.d("Log:","User Input is "+ item.getTodo());
                        return true;
                    }else{
                        Log.d("Log：","User Input is null");
                        Toast.makeText(getApplicationContext(),"写点东西吧 0A0",Toast.LENGTH_SHORT);
                        return true;
                    }

                }
                return false;
            }
        });

    }
}
