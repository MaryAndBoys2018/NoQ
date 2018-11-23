package ua.com.mnbs.noq;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        ArrayList<String> Name = new ArrayList<>();
        ArrayList<String> Location = new ArrayList<>();
        ArrayList<String> Type = new ArrayList<>();
        String text = "";
        try {
            InputStream inputStream = getAssets().open("cafe_names.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
            text += '\0';
        } catch (IOException e) {
            e.printStackTrace();
        }
        String temp = "";
        for (int i = 0; i < text.length(); i++)
        {
            if (text.charAt(i) == '\n' || text.charAt(i) == '\0') {
                Name.add(temp);
                temp = "";
                continue;
            }
            temp += text.charAt(i);
        }

        text = "";
        try {
            InputStream inputStream = getAssets().open("cafe_locations.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
            text += '\0';
        } catch (IOException e) {
            e.printStackTrace();
        }
        temp = "";
        for (int i = 0; i < text.length(); i++)
        {
            if (text.charAt(i) == '\n' || text.charAt(i) == '\0') {
                Location.add(temp);
                temp = "";
                continue;
            }
            temp += text.charAt(i);
        }

        text = "";
        try {
            InputStream inputStream = getAssets().open("cafe_types.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
            text += '\0';
        } catch (IOException e) {
            e.printStackTrace();
        }
        temp = "";
        for (int i = 0; i < text.length(); i++)
        {
            if (text.charAt(i) == '\n' || text.charAt(i) == '\0') {
                Type.add(temp);
                temp = "";
                continue;
            }
            temp += text.charAt(i);
        }

        ArrayList<Cafe> cafes = new ArrayList<>();
        if (Name.size() != Location.size() || Name.size() != Type.size() || Location.size() != Type.size())
            Toast.makeText(getApplicationContext(),"Something is wrong with your text files.",
                    Toast.LENGTH_SHORT).show();
       else {
            for (int i = 0; i < Name.size(); i++)
                cafes.add(new Cafe(Name.get(i), Location.get(i), Type.get(i)));


                CafeAdapter adapter = new CafeAdapter(this, cafes);
                ListView listView = (ListView) findViewById(R.id.cafe_list);
                listView.setAdapter(adapter);
            }

    }
}
