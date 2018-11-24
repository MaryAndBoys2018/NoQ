package ua.com.mnbs.noq;

import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListOfCafes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_cafes);

        String names = readFile("cafe_names.txt");
        String locations = readFile("cafe_locations.txt");
        String types = readFile("cafe_types.txt");

        ArrayList<String> name = moveIntoArrayList(names);
        ArrayList<String> location = moveIntoArrayList(locations);
        ArrayList<String> type = moveIntoArrayList(types);

        ArrayList<Cafe> cafes = new ArrayList<>();

        if (isMistakeInFiles(name, location, type))
            Toast.makeText(getApplicationContext(), "Something is wrong with your text files.",
                    Toast.LENGTH_SHORT).show();
        else {
            cafes = createCafeArrayList(name, location, type);
            printListOfCafes(cafes);
        }

        ListView listView = (ListView) findViewById(R.id.cafe_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent OpenListOfMeals = new Intent(ListOfCafes.this, ListOfMeals.class);
                startActivity(OpenListOfMeals);
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

    private void printListOfCafes(ArrayList<Cafe> cafes) {
        CafeAdapter adapter = new CafeAdapter(this, cafes);
        ListView listView = (ListView) findViewById(R.id.cafe_list);
        listView.setAdapter(adapter);
    }


    private boolean isMistakeInFiles(ArrayList<String> name, ArrayList<String> location, ArrayList<String> type) {
        return (name.size() != location.size() || name.size() != type.size() || location.size() != type.size());
    }

    private ArrayList<Cafe> createCafeArrayList(ArrayList<String> name, ArrayList<String> location, ArrayList<String> type) {
        ArrayList<Cafe> cafes = new ArrayList<>();
        for (int i = 0; i < name.size(); i++)
            cafes.add(new Cafe(name.get(i), location.get(i), type.get(i)));
        return cafes;
    }
}
