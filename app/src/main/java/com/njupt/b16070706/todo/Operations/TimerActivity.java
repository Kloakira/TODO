package com.njupt.b16070706.todo.Operations;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.njupt.b16070706.todo.Data.DateUtil;
import com.njupt.b16070706.todo.R;
import com.ns.yc.yccountdownviewlib.CountDownView;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;

import static com.njupt.b16070706.todo.Data.DateUtil.getFormattedTime;

public class TimerActivity extends AppCompatActivity{

    private long timeStamp;
    private CountDownView cdvTime;
    private TimePickerDialog mTimePickerDialog;
    int hour;
    int minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        cdvTime = findViewById(R.id.cdv_time);
        RotatingText();
        initCountDownView();
    }
    private void initCountDownView() {
        timeStamp = System.currentTimeMillis();
        String nowTime = getFormattedTime(timeStamp);
        Bundle bundle = this.getIntent().getExtras();
        long MillSeconds = bundle.getLong("MillSeconds");
        String time = DateUtil.getFormattedTime(MillSeconds);

        hour = Integer.parseInt(time.substring(0,2)) - Integer.parseInt(nowTime.substring(0,2)) ;
        minute = Integer.parseInt(time.substring(3)) - Integer.parseInt(nowTime.substring(3));

        int countdownTime = hour * 3600 + minute * 60;

        //Toast.makeText(TimerActivity.this,":::::"+time+":::::",Toast.LENGTH_SHORT).show();
        cdvTime.setTime(countdownTime);
        cdvTime.start();
        cdvTime.setOnLoadingFinishListener(new CountDownView.OnLoadingFinishListener() {
            @Override
            public void finish() {
                Toast.makeText(TimerActivity.this,"完成咯~",Toast.LENGTH_SHORT).show();
            }
        });
        cdvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdvTime.stop();
            }
        });
    }
    /**滚动字幕
     * 单纯为了花里花哨*/
    public void RotatingText () {
        RotatingTextWrapper rotatingTextWrapper = findViewById(R.id.custom_switcher);
        rotatingTextWrapper.setSize(20);

        Rotatable rotatable = new Rotatable(Color.parseColor("#15ABE6"), 3000, "  Let's Todo now~");

        rotatable.setSize(20);
        rotatable.setAnimationDuration(1000);

        rotatingTextWrapper.setContent(" Akira! ", rotatable);
    }

}
