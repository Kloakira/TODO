package com.njupt.b16070706.todo.Todo;

import android.annotation.SuppressLint;
import android.content.Intent;
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


import com.jzxiang.pickerview.TimeWheel;
import com.jzxiang.pickerview.config.PickerConfig;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.njupt.b16070706.todo.Data.DateUtil;
import com.njupt.b16070706.todo.Data.Items;
import com.njupt.b16070706.todo.Data.GlobalUtil;
import com.jzxiang.pickerview.TimePickerDialog;
import com.njupt.b16070706.todo.Operations.OnDoubleClickListener;
import com.njupt.b16070706.todo.R;
import com.njupt.b16070706.todo.Operations.TimerActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TodoActivity extends AppCompatActivity implements OnDateSetListener {

    private EditText editText;
    private Button alarmButton;
    private Button timeButton;
    private ViewPager viewPager;
    private MainViewPagerAdapter pagerAdapter;
    private FloatingActionButton floatingActionButton;
    TimePickerDialog mDialogHourMinute;
    long time;

    //TimePickerDialog mTimePickerDialog = new TimePickerDialog(TodoActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        mDialogHourMinute = new TimePickerDialog.Builder()
                .setType(Type.HOURS_MINS)
                .setCallBack(this)
                .build();


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
        alarmButton = findViewById(R.id.btn_alarm);//新建todo的按钮
        floatingActionButton = findViewById(R.id.float_button);
        timeButton = findViewById(R.id.btn_time);
        handleFloatButton();
        handleEditText();

        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //mTimePickerDialog.showTimePickerDialog();
                bundle.putLong("MillSeconds", time);
                Intent intent = new Intent(TodoActivity.this, TimerActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
       timeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mDialogHourMinute.show(getSupportFragmentManager(), "hour_minute");
           }
       });

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
                if(pagerAdapter.getLastFragment().getDate().equals(DateUtil.getFormattedDate())){

                }else{
                    pagerAdapter.setFragments(DateUtil.getFormattedDate());
                    viewPager.setCurrentItem(pagerAdapter.getLastIndex());//让新建的fragment成为当前显示的窗口
                }

            }
        });
    }

    /**在editText输入回车后直接新建新的item*/
    /**两种添加item方式：
     * 1.通过pagerAdapter的方法获取最后一个Fragment
     * 再通过最后一个fragment的addItem方法添加一个item
     * 2.通过pagerAdapter的reload方法遍历所有的Fragment
     * 再通过单个fragment的reload方法添加一个item
     *方法一：最后一个fragment中的listview添加item（我也不知道行不行）
     pagerAdapter.getLastFragment().addItem(item);*/
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

    @Override
    public void onDateSet(com.jzxiang.pickerview.TimePickerDialog timePickerView, long millseconds) {
        time = millseconds;
    }

}
