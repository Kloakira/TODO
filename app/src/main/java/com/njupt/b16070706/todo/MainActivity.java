package com.njupt.b16070706.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.njupt.b16070706.todo.Todo.TodoActivity;

import me.nlmartian.silkcal.DatePickerController;
import me.nlmartian.silkcal.DayPickerView;
import me.nlmartian.silkcal.SimpleMonthAdapter;


public class MainActivity extends AppCompatActivity implements DatePickerController{

    private Button btnTodo;
    private DayPickerView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //将它转移到TodoActivity   RotatingText();

        btnTodo = findViewById(R.id.btn_todo);
        btnTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , TodoActivity.class);
                startActivity(intent);
            }
        });

        calendarView = findViewById(R.id.calendar_view);
        calendarView.setController(this);


    }

    //日历要默认实现的方法
    @Override
    public int getMaxYear() {
        return 0;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {

    }
    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

    }
    //上方的滚动字幕，将它转移到TodoActivity
   /* public void RotatingText () {
        RotatingTextWrapper rotatingTextWrapper = findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(20);

        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 2000, "Make Your Todo Now");

        rotatable.setSize(20);
        rotatable.setAnimationDuration(500);

        rotatingTextWrapper.setContent("       YO!    ", rotatable);
    }*/
}
