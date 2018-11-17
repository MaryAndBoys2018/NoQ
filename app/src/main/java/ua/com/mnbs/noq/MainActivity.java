package ua.com.mnbs.noq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Order = (Button) findViewById(R.id.makeOrder);

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenListOfCaffees = new Intent(MainActivity.this, ListOfCaffes.class);
                startActivity(OpenListOfCaffees);

            }
        });
        Button myOrders = (Button) findViewById(R.id.myOrders);

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenListofOrders = new Intent(MainActivity.this, ListOfOrders.class);
                startActivity(OpenListofOrders);

            }
        });
    }
}
