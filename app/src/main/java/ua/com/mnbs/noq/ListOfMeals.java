package ua.com.mnbs.noq;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListOfMeals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String currentCafe = extras.getString("cafe name");

        currentCafe = currentCafe.trim();

        Toast.makeText(getApplicationContext(), currentCafe,
                Toast.LENGTH_SHORT).show();

        String menuFileDirectory = currentCafe + "_menu.txt";
        String pricesFileDirectory = currentCafe + "_prices.txt";

        Toast.makeText(getApplicationContext(), menuFileDirectory,
                Toast.LENGTH_SHORT).show();

        String names = readFile(menuFileDirectory);
        ArrayList<String> name = moveIntoArrayList(names);
        String prices = readFile(pricesFileDirectory);
        ArrayList<String> price = moveIntoArrayList(prices);

       // Toast.makeText(getApplicationContext(), name.get(1), Toast.LENGTH_SHORT).show();

        ArrayList<Meal> meals = new ArrayList<>();

        if (isMistakeInFiles(name, price))
            Toast.makeText(getApplicationContext(), "Something is wrong with your text files.",
                    Toast.LENGTH_SHORT).show();
        else {
            meals = createMealArrayList(name, price);
            printListOfMeals(meals);
        }

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
