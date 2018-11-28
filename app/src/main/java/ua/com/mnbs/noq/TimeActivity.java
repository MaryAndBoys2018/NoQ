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

        currentTime.setText(" " + getTime());

        submitTime = (Button) findViewById(R.id.submit_time);

        submitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderTime.setText(" " + getTime());
            }
        });
    }

    private String getTime(){

        Integer orderHour = floatTime.getHour();
        Integer orderMinute = floatTime.getMinute();

        String mOrderTime = orderHour.toString();
        mOrderTime += ":";

        if (orderMinute < 10){
            mOrderTime += "0";
        }

        mOrderTime += orderMinute.toString();

        return  mOrderTime;
    }
}