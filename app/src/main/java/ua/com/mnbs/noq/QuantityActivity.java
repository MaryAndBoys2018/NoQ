package ua.com.mnbs.noq;

import android.content.Context;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
        final ArrayList<Meal> meals = new ArrayList<>();
        String tempName = "";
        String tempPrice = "";
        for (int i=0; i<numberOfCheckedItems; i++){
            tempName = extras.getString("name"+i);
            tempPrice = extras.getString("price"+i);
            meals.add(new Meal(tempName, tempPrice));
        }

        QuantityAdapter adapter = new QuantityAdapter( this, meals);
        ListView listView = (ListView) findViewById(R.id.menu_list);
        listView.setAdapter(adapter);




        Button chooseQuantity = (Button) findViewById(R.id.choose_quantity_button);

        chooseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenQuantityActivity = new Intent(QuantityActivity.this, TimeActivity.class);
                for (int i=1;i<meals.size();i++)
                    WriteToFile("Order"+ReadFromFileNotAsset("counter.txt")+".txt",Integer.toString(meals.get(i).getQuantity())+"\n");
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
    protected void WriteToFile(String file, String text)
    {
        try {
            FileOutputStream fos = openFileOutput(file,Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        }
        catch (IOException e){
            Toast.makeText(QuantityActivity.this,"Error Writing to file",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(QuantityActivity.this,"Помилка у читанні файлу",Toast.LENGTH_SHORT).show();
        }
        return text;

    }

}
