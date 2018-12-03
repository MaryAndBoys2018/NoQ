package ua.com.mnbs.noq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimeActivity extends AppCompatActivity {

    TimePicker floatTime;
    TextView orderTime;
    Button submitTime;

    final int preparationTime = 15;
    final int minutesInHour = 60;

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

        if (isCafeOpen(currentHour, currentMinute)){
            orderTime.setText(updateDisplay());
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
                if (checkPreparationTime(floatTime.getHour(), floatTime.getMinute(), currentHour, currentMinute)) {
                    startActivity(OpenMyOrder);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Май совість, це замало часу на приготування твого замовлення", Toast.LENGTH_SHORT).show();
                }
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
            if (wasNotShownToastForPast) {
                Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                wasNotShownToastForPast = false;
            }
            floatTime.setHour(currentHour);
            floatTime.setMinute(currentMinute);
            return false;

        } else if (orderHour == currentHour) {
            if (orderMinute < currentMinute){
                if (wasNotShownToastForPast) {
                    Toast.makeText(this, "Ей, не можна робити замовлення в минулому часі", Toast.LENGTH_SHORT).show();
                    wasNotShownToastForPast = false;
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
        if (orderHour > 22) {
            if (wasNotShownTooLateToast) {
                Toast.makeText(this, "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
                wasNotShownTooLateToast = false;
            }
            floatTime.setHour(22);
            floatTime.setMinute(0);
            return false;
        }

        if (orderHour == 22 && orderMinute > 0) {
            if (wasNotShownTooLateToast) {
                Toast.makeText(this, "Вибач, але кафе вже зачинено", Toast.LENGTH_SHORT).show();
                wasNotShownTooLateToast = false;
            }
            floatTime.setHour(22);
            floatTime.setMinute(0);
            return false;
        }

        if (orderHour < 7) {
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


    private boolean checkPreparationTime(int orderHour, int orderMinute, int currentHour, int currentMinute) {
        if (isNearNewHour(currentMinute)) {
            if (orderHour == currentHour) {
                if (wasNotShownToastForPreparation) {
                    Toast.makeText(this, "Май совість, це замало часу на приготування твого замовлення", Toast.LENGTH_SHORT).show();
                    wasNotShownToastForPreparation = false;
                }
                return false;
            }
            if(orderHour == currentHour + 1) {
                if (orderMinute < cutMinute(currentMinute + preparationTime)) {

                    if (wasNotShownToastForPreparation) {
                        Toast.makeText(this, "Май совість, це замало часу на приготування твого замовлення", Toast.LENGTH_SHORT).show();
                        wasNotShownToastForPreparation = false;
                    }
                    return false;
                }
            }
        }

        else{
            if (orderMinute < currentMinute + preparationTime){
                return false;
            }
        }
        return  true;
    }
}