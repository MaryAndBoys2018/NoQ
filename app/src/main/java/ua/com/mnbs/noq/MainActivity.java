package ua.com.mnbs.noq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Order = (Button) findViewById(R.id.makeOrder);

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent OpenListOfCafes = new Intent(MainActivity.this, ListOfCafes.class);
                startActivity(OpenListOfCafes);

            }
        });
        Button myOrders = (Button) findViewById(R.id.myOrders);

        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenListOfOrders = new Intent(MainActivity.this, ListOfOrders.class);
                startActivity(OpenListOfOrders);

            }
        });
    }
   /* public class RegistA extends ListOfMeals {
        CheckBox fee_checkbox;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.menu_list_item);
            fee_checkbox = (CheckBox) findViewById(R.id.meal_checkbox);// Fee Payment Check box
        }
        public void checkbox_clicked(View v)
        {
            Button btn = findViewById(R.id.btn);
            if(fee_checkbox.isChecked())
            {
                btn.setVisibility(Button.VISIBLE);

            }
            else
            {
                btn.setVisibility(Button.GONE);
            }

        }
    } */
}

