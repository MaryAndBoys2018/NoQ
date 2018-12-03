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
import android.widget.ImageView;
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

        final String nameOfCafeForBackButton = extras.getString("cafe name for intent");
        final int positionForBackButton = extras.getInt("position for intent");

        ArrayList<Meal> meals = new ArrayList<>();

        String tempName = "";
        String tempPrice = "";
        for (int i=0; i<numberOfCheckedItems; i++){
            tempName = extras.getString("name"+i);
            tempPrice = extras.getString("price"+i);
            meals.add(new Meal(tempName, tempPrice));
        }

        QuantityAdapter adapter = new QuantityAdapter( this, meals);
        ListView listView = (ListView) findViewById(R.id.quantity_list);
        listView.setAdapter(adapter);

        ImageView buttonToMain = (ImageView) findViewById(R.id.horse_icon_from_quantity);

        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(QuantityActivity.this, MainActivity.class);
                startActivity(toMainActivity);
            }
        });

        ImageView backButton = (ImageView) findViewById(R.id.back_from_quantity) ;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLisOfMeals = new Intent(QuantityActivity.this, ListOfMeals.class);
                toLisOfMeals.putExtra("cafe name", nameOfCafeForBackButton);
                toLisOfMeals.putExtra("position", positionForBackButton);
                startActivity(toLisOfMeals);
            }
        });

        Button chooseQuantity = (Button) findViewById(R.id.choose_quantity_button);

        chooseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent OpenTimeActivity = new Intent(QuantityActivity.this, TimeActivity.class);
                startActivity(OpenTimeActivity);

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
