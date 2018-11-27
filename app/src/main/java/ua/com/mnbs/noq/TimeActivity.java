package ua.com.mnbs.noq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimeActivity extends AppCompatActivity {

    TimePicker floatTime;
    TextClock currentTime;
    TextView orderTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        floatTime = (TimePicker) findViewById(R.id.clock);
        currentTime = (TextClock) findViewById(R.id.current_time);
        orderTime = (TextView) findViewById(R.id.text_time);

        orderTime.setText(" " + getTime());
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
