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
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListOfMeals extends AppCompatActivity {
    ArrayList<Meal> meals;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String currentCafe = extras.getString("cafe name");
        position = extras.getInt("position");


        currentCafe = currentCafe.trim();

        if (position == 0)
            currentCafe = currentCafe.substring(1,currentCafe.length());

       final String menuFileDirectory = currentCafe + "_menu.txt";
       final String pricesFileDirectory = currentCafe + "_prices.txt";

        String names = readFile(menuFileDirectory);

        ArrayList<String> name = moveIntoArrayList(names);
        String prices = readFile(pricesFileDirectory);
        ArrayList<String> price = moveIntoArrayList(prices);

        meals = new ArrayList<>();

        if (isMistakeInFiles(name, price))
            Toast.makeText(getApplicationContext(), "Something is wrong with your text files.",
                    Toast.LENGTH_SHORT).show();
        else {
            meals = createMealArrayList(name, price);
            printListOfMeals(meals);
        }


        ListView listView = (ListView) findViewById(R.id.menu_list);
        final Button chooseDishes = (Button) findViewById(R.id.choose_dishes_button);
        meals.get(0).numberOfCheckedItems = 0;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long l) {
                if (v != null) {
                    CheckBox checkBox = (CheckBox)v.findViewById(R.id.meal_checkbox);
                    checkBox.setChecked(!checkBox.isChecked());
                }

            }

        });


        chooseDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (meals.get(0).numberOfCheckedItems == 0) {
                    Toast.makeText(getApplicationContext(), "Виберіть, будь ласка, страву",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent OpenQuantityActivity = new Intent(ListOfMeals.this, QuantityActivity.class);
                    int index = 0;
                    for (int i=0; i<meals.size(); i++) {
                        if (meals.get(i).getChecked()) {
                            OpenQuantityActivity.putExtra("number of checked meals", meals.get(0).numberOfCheckedItems);
                            OpenQuantityActivity.putExtra("name"+index,meals.get(i).getMealName());
                            OpenQuantityActivity.putExtra("price"+index,meals.get(i).getMealPrice());
                            index++;
                        }
                    }
                  for (int i=0;i<meals.size();i++) {
                         if (meals.get(i).getChecked())
                              WriteToFile("Order" + ReadFromFileNotAsset("counter.txt") + ".txt", meals.get(i).getMealName() + "\n" + meals.get(i).getMealPrice());
                     }
                    startActivity(OpenQuantityActivity);
                }
            }
        });

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
                Toast.makeText(ListOfMeals.this,"Помилка у читанні файлу",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ListOfMeals.this,"Error Writing to file",Toast.LENGTH_SHORT).show();
        }
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
