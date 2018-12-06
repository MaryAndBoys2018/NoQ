package ua.com.mnbs.noq;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    final int closingHour = 22;
    final int openingHour = 7;
    final int preparationTime = 15;
    final int minutesInHour = 60;

    boolean wasShownToastForPast = false;
    boolean wasShownTooEarlyToast = false;
    boolean wasShownTooLateToast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        floatTime = (TimePicker) findViewById(R.id.clock);
        orderTime = (TextView) findViewById(R.id.text_time);

        floatTime.setIs24HourView(true);

        final Integer currentHour = floatTime.getHour();
        final Integer currentMinute = floatTime.getMinute();

        if (isCafeOpen(currentHour, currentMinute)){
            orderTime.setText(updateDisplay());
        }
        else {
            Intent toMainActivity = new Intent(TimeActivity.this, MainActivity.class);
            startActivity(toMainActivity);
        }

        floatTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (isCafeOpen(hourOfDay, minute)) {
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
                Intent OpenMyOrder = new Intent(TimeActivity.this, MyOrdersActivity.class);
                OpenMyOrder.putExtra("counter",getCounter());
                if (checkPreparationTime(floatTime.getHour(), floatTime.getMinute(), currentHour, currentMinute)) {
                    WriteToFile("Time+"+ReadFromFileNotAsset("counter.txt")+".txt",orderTime.getText().toString());
                    startActivity(OpenMyOrder);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Май совість, дай хоча б 15 хвилин на приготування", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    String getCounter(){
        return ReadFromFileNotAsset("counter.txt");
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
                Toast.makeText(TimeActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
            Toast.makeText(TimeActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
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
        } else {
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
            if (!wasShownToastForPast) {
                Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                wasShownToastForPast = true;
            }
            floatTime.setHour(currentHour);
            floatTime.setMinute(currentMinute);
            return false;

        } else if (orderHour == currentHour) {
            if (orderMinute < currentMinute){
                if (!wasShownToastForPast) {
                    Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                    wasShownToastForPast = true;
                }
                floatTime.setHour(currentHour);
                floatTime.setMinute(currentMinute);
                return false;
            }
        }
        return true;
    }



    private int cutMinute(int minute){
        if (minute >= minutesInHour){
            minute -= minutesInHour;
        }
        return minute;
    }



    private boolean isNearNewHour(Integer currentMinute){
        if (minutesInHour - preparationTime <= currentMinute){
            return true;
        }
        return false;
    }



    private boolean isCafeOpen(int orderHour, int orderMinute) {
        if (orderHour > closingHour) {
            if (!wasShownTooLateToast) {
                Toast.makeText(this, "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
                wasShownTooLateToast = true;
            }
            floatTime.setHour(closingHour);
            floatTime.setMinute(0);
            return false;
        }

        if (orderHour == closingHour && orderMinute > 0) {
            if (!wasShownTooLateToast) {
                Toast.makeText(this, "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
                wasShownTooLateToast = true;
            }
            floatTime.setHour(closingHour);
            floatTime.setMinute(0);
            return false;
        }

        if (orderHour < openingHour) {
            if (!wasShownTooEarlyToast) {
                Toast.makeText(this, "Вибач, але кафе ще зачинено", Toast.LENGTH_SHORT).show();
                wasShownTooEarlyToast = true;
            }
            floatTime.setHour(openingHour);
            floatTime.setMinute(0);
            return false;
        }
        return true;
    }


    private boolean checkPreparationTime(int orderHour, int orderMinute, int currentHour, int currentMinute) {
        if (isNearNewHour(currentMinute)) {
            if (orderHour == currentHour) {
                return false;
            }

            if(orderHour == currentHour + 1) {
                if (orderMinute < cutMinute(currentMinute + preparationTime)) {
                    return false;
                }
            }
        }

        else{
            if (orderHour == currentHour) {
                if (orderMinute < currentMinute + preparationTime) {
                    return false;
                }
            }
        }
        return  true;
    }
}