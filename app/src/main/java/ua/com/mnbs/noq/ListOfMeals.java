package ua.com.mnbs.noq;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListOfMeals extends MenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String currentCafe = extras.getString("cafe name");
        int position = extras.getInt("position");

        currentCafe = currentCafe.trim();

        if (position == 0)
            currentCafe = currentCafe.substring(1,currentCafe.length());

        String menuFileDirectory = currentCafe + "_menu.txt";
        String pricesFileDirectory = currentCafe + "_prices.txt";

        String names = readFile(menuFileDirectory);

        ArrayList<String> name = moveIntoArrayList(names);
        String prices = readFile(pricesFileDirectory);
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


               CheckBox checkBox = (CheckBox)v.findViewById(R.id.meal_checkbox);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        Button btn= findViewById(R.id.btn);
                        if (isChecked) {
                            btn.setVisibility(View.VISIBLE);
                        }
                        else{
                            btn.setVisibility(View.GONE);
                        }
                    }
                });

            }

        });
       /* LayoutInflater inflater = getLayoutInflater();
        View myView = inflater.inflate(R.layout.menu_list_item, null);
        CheckBox myTextView = (CheckBox) myView.findViewById(R.id.meal_checkbox);
        myTextView.setText("3468430248"); */

        /*View v = LayoutInflater.from(this).inflate(R.layout.menu_list_item, null);
        CheckBox check = (CheckBox) findViewById(R.id.meal_checkbox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            Button btn =(Button)findViewById( R.id.btn);
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    btn.setVisibility(View.VISIBLE);
                }else{
                    btn.setVisibility(View.GONE);
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
