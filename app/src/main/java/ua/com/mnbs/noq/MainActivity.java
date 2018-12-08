package ua.com.mnbs.noq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
                overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);

            }

        });


        Button myOrders = (Button) findViewById(R.id.myOrders);

        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenListOfOrders = new Intent(MainActivity.this, ListOfOrders.class);
                startActivity(OpenListOfOrders);
                overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);

            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom, R.anim.from_top_to_bottom_exit);
    }
}
