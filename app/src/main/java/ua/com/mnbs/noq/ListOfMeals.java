package ua.com.mnbs.noq;

import android.content.Intent;
import android.hardware.camera2.params.BlackLevelPattern;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListOfMeals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String names = readFile("meal_names.txt");
        ArrayList<String> name = moveIntoArrayList(names);
        String prices = readFile("meal_prices.txt");
        ArrayList<String> price = moveIntoArrayList(prices);

        ArrayList<Meal> meals = new ArrayList<>();

        if (isMistakeInFiles(name, price))
            Toast.makeText(getApplicationContext(), "Something is wrong with your text files.",
                    Toast.LENGTH_SHORT).show();
        else {
            meals = createMealArrayList(name, price);
            printListOfMeals(meals);
        }



        ListView listView = (ListView) findViewById(R.id.menu_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long l) {
                if (v != null) {
                    CheckBox checkBox = (CheckBox)v.findViewById(R.id.meal_checkbox);
                    checkBox.setChecked(!checkBox.isChecked());
                }

            }

        });

        /*CheckBox checkBox = (CheckBox)findViewById(R.id.meal_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                View btn = findViewById(R.id.btn);
               // btn.setVisibility(View.GONE);//TO HIDE THE BUTTON
                btn.setVisibility(View.VISIBLE);//TO SHOW THE BUTTON
            }
        }); */



 /* if (checkBox.isChecked()) {
                    View btn = findViewById(R.id.btn);
                    btn.setVisibility(View.VISIBLE); }
                    else {
                        View btn = findViewById(R.id.btn);
                        btn.setVisibility(View.INVISIBLE);
                    } *

        CheckBox checkBox = (CheckBox) findViewById(R.id.meal_checkbox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) findViewById(R.id.meal_checkbox);
                View btn = findViewById(R.id.btn);
                if (checkBox.isChecked()) {
                    btn.setVisibility(View.VISIBLE);
                } else
                    btn.setVisibility(View.GONE);
            }

        });


        /*CheckBox yourCheckBox = (CheckBox) findViewById (R.id.meal_checkbox);

        yourCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View btn = findViewById(R.id.btn);
                if (((CheckBox) v).isChecked()) {
                    btn.setVisibility(View.VISIBLE);
                } else {
                    btn.setVisibility(View.GONE);
                }
            }
        }); */


       /* CheckBox mCheckBox= ( CheckBox ) findViewById( R.id.meal_checkbox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Button mbtn =(Button)findViewById( R.id.btn);
                if ( isChecked )
                {
                    mbtn.setVisibility(View.VISIBLE);
                }
                else{
                    mbtn.setVisibility(View.GONE);
                }
            }
        }); */


    }


    private String readFile(String fileName) {
        String text = "";
        try {
            InputStream inputStream = getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
            text += '\0';
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    private ArrayList<String> moveIntoArrayList(String text) {
        ArrayList<String> returnValue = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n' || text.charAt(i) == '\0') {
                returnValue.add(temp);
                temp = "";
                continue;
            }
            temp += text.charAt(i);
        }
        return returnValue;
    }

    private void printListOfMeals(ArrayList<Meal> meals) {
        MenuAdapter adapter = new MenuAdapter(this, meals);
        ListView listView = (ListView) findViewById(R.id.menu_list);
        listView.setAdapter(adapter);
    }

    private boolean isMistakeInFiles(ArrayList<String> name, ArrayList<String> price) {
        return (name.size() != price.size());
    }

    private ArrayList<Meal> createMealArrayList(ArrayList<String> name, ArrayList<String> price) {
        ArrayList<Meal> meals = new ArrayList<>();
        for (int i = 0; i < name.size(); i++)
            meals.add(new Meal(name.get(i), price.get(i)));
        return meals;
    }
}
