package ua.com.mnbs.noq;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ListOfCafes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_cafes);

        ArrayList<Cafe> cafes = new ArrayList<>();

        cafes.add(new Cafe("NiceCoffee", "вул. Степана Бандери, 19", "кав'ярня"));
        cafes.add(new Cafe("PizzaHouse", "вул. Університетська, 5а", "піцерія"));

        CafeAdapter adapter = new CafeAdapter(this, cafes);
        ListView listView = (ListView) findViewById(R.id.cafe_list);
        listView.setAdapter(adapter);
    }
}
