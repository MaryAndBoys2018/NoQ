package ua.com.mnbs.noq;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class QuantityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_quantity);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int numberOfCheckedItems = extras.getInt("number of checked meals");
       int[] positions = new int[5];
       String menuFile = extras.getString("menu file");
       String pricesFile = extras.getString("price file");
        for (int i=0; i<numberOfCheckedItems; i++)
            positions[i] = extras.getInt("position"+i);


        final ArrayList<Meal> meals = new ArrayList<Meal>();

        QuantityAdapter adapter = new QuantityAdapter( this, meals);
        ListView listView = (ListView) findViewById(R.id.menu_list);
        listView.setAdapter(adapter);



        Button chooseQuantity = (Button) findViewById(R.id.choose_quantity_button);

        chooseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent OpenQuantityActivity = new Intent(QuantityActivity.this, TimeActivity.class);
                startActivity(OpenQuantityActivity);

            }
        });
    }
    int quantity=1;

    public void increment(View view) {
        if (quantity==20){
            Toast.makeText(this, "Ууупс! Не вибирайте так багато. Ми не впораємося:)", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity= quantity+1;
        displayQuantity(quantity);
    }
    public void decrement(View view) {
        if (quantity==1) {
            Toast.makeText(this, "Не можна вибрати від'ємну кількість страв:)", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity= quantity-1;
        displayQuantity(quantity);
    }
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


}
