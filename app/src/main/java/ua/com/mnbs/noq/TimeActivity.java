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
    TextView orderTime;
    Button submitTime;

    boolean wasNotShownToastForPast = true;
    boolean wasNotShownToastForPreparation = true;
    boolean wasNotShownTooEarlyToast = true;
    boolean wasNotShownTooLateToast = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        floatTime = (TimePicker) findViewById(R.id.clock);
        orderTime = (TextView) findViewById(R.id.text_time);

        floatTime.setIs24HourView(true);

        final Integer currentHour = floatTime.getHour();
        final Integer currentMinute = floatTime.getMinute();

        orderTime.setText(updateDisplay());

        floatTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                if (isCafeOpen(hourOfDay)) {
                    if (isAllowableTime(hourOfDay, currentHour, minute, currentMinute)) {
                        updateDisplay(hourOfDay, minute);
                    }
                }

            }
        });

        submitTime = (Button) findViewById(R.id.submit_time);

        submitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent OpenMyOrder = new Intent(TimeActivity.this, activity_my_orders.class);
                WriteToFile("Order"+ReadFromFileNotAsset("counter.txt")+".txt",orderTime.getText().toString());
                startActivity(OpenMyOrder);
            }
        });
    }



    private void updateDisplay(int hour, int minute) {
        Integer orderHour = hour;
        Integer orderMinute = minute;
        String mOrderTime = convertTime(orderHour, orderMinute);
        orderTime.setText(mOrderTime);
    }



    private String updateDisplay() {
        Integer currentHour = floatTime.getHour();
        Integer currentMinute = floatTime.getMinute();
        String mOrderTime = convertTime(currentHour, currentMinute);
        return mOrderTime;
    }



    private String fixZero(Integer num) {
        String stringNum;
        if (num < 10) {
            stringNum = "0";
            stringNum += num.toString();
        }
        else {
            stringNum = num.toString();
        }
        return stringNum;
    }



    private String convertTime(Integer hour, Integer minute) {
        String convertedTime = fixZero(hour);
        convertedTime += ":";
        convertedTime += fixZero(minute);
        return convertedTime;
    }



    private boolean isAllowableTime(int orderHour, Integer currentHour, int orderMinute, Integer currentMinute) {

        if (orderHour < currentHour) {
            updateDisplay();
            if (wasNotShownToastForPast) {
                Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                wasNotShownToastForPast = false;
            }
            floatTime.setHour(currentHour);
            return false;
        }
        else if (orderHour == currentHour) {
            if (orderMinute < currentMinute) {
                updateDisplay();
                if (wasNotShownToastForPast) {
                    Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                    wasNotShownToastForPast = false;
                }
                floatTime.setMinute(currentMinute);
                return false;
            }
            else if (orderMinute < currentMinute + 15) {
                updateDisplay();
                if (wasNotShownToastForPreparation) {
                    Toast.makeText(this, "Май совість, це замало часу на приготування твого замовлення", Toast.LENGTH_SHORT).show();
                    wasNotShownToastForPreparation = false;
                }
                floatTime.setMinute(currentMinute + 15);
                return false;
            }
        }
        return true;
    }



    private boolean isCafeOpen(int orderHour) {
        if (orderHour >= 22) {
            updateDisplay(22, 0);
            if (wasNotShownTooLateToast){
                Toast.makeText(this, "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
                wasNotShownTooLateToast = false;
            }
            floatTime.setHour(22);
            floatTime.setMinute(0);
            return false;
        }
        if (orderHour <= 7) {
            updateDisplay(7, 0);
            if (wasNotShownTooEarlyToast) {
                Toast.makeText(this, "Вибач, але кафе ще зачинено", Toast.LENGTH_SHORT).show();
                wasNotShownTooEarlyToast = false;
            }
            floatTime.setHour(7);
            floatTime.setMinute(0);
            return false;
        }
        return true;
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
}