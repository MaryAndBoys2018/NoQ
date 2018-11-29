package ua.com.mnbs.noq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimeActivity extends AppCompatActivity {

    TimePicker floatTime;
    TextView currentTime;
    TextView orderTime;
    Button submitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        floatTime = (TimePicker) findViewById(R.id.clock);
        currentTime = (TextView) findViewById(R.id.current_time);
        orderTime = (TextView) findViewById(R.id.text_time);

        currentTime.setText(" " + updateDisplay());

        floatTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                updateDisplay(hourOfDay, minute);
            }
        });

        submitTime = (Button) findViewById(R.id.submit_time);

        submitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenListOfOrders = new Intent(TimeActivity.this, activity_my_orders.class);
                startActivity(OpenListOfOrders);
            }
        });
    }

    private void updateDisplay(int hour, int minute){
        Integer orderHour = hour;
        Integer orderMinute = minute;

        String mOrderTime = convertTime(orderHour, orderMinute);

        orderTime.setText(" " + mOrderTime);
    }

    private String updateDisplay(){

        Integer orderHour = floatTime.getHour();
        Integer orderMinute = floatTime.getMinute();

        String mOrderTime = convertTime(orderHour, orderMinute);

        return  mOrderTime;
    }

    private String fixZero(Integer num){
        String stringNum;

        if (num < 10){
            stringNum = "0";
            stringNum += num.toString();
        }else{
            stringNum = num.toString();
        }

        return stringNum;
    }

    private String convertTime(Integer hour, Integer minute){
        String convertedTime = fixZero(hour);
        convertedTime += ":";
        convertedTime += fixZero(minute);

        return  convertedTime;
    }
}