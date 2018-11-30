package ua.com.mnbs.noq;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
                WriteToFile("Order"+ReadFromFileNotAsset("counter.txt")+".txt",orderTime.getText().toString());
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

    public String ReadFromFileNotAsset(String file){
        String text= "";
        try{
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        }
        catch (IOException e)
        {
            if (file=="counter.txt"){
                WriteToFile("counter.txt","0");
            }
            else
                Toast.makeText(TimeActivity.this,"Помилка у читанні файлу",Toast.LENGTH_SHORT).show();
        }
        return text;

    }

    protected void WriteToFile(String file, String text)
    {
        try {
            FileOutputStream fos = openFileOutput(file,Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        }
        catch (IOException e){
            Toast.makeText(TimeActivity.this,"Error Writing to file",Toast.LENGTH_SHORT).show();
        }
    }
}