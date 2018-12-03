package ua.com.mnbs.noq;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ListOfCafes extends AppCompatActivity {

    ArrayList<Cafe> cafes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_cafes);

        String names = readFile("cafe_names.txt");
        String locations = readFile("cafe_locations.txt");
        String types = readFile("cafe_types.txt");
        String emails = readFile("cafe_emails.txt");

        ArrayList<String> name = moveIntoArrayList(names);
        ArrayList<String> location = moveIntoArrayList(locations);
        ArrayList<String> type = moveIntoArrayList(types);
        ArrayList<String> email = moveIntoArrayList(emails);

        if (isMistakeInFiles(name, location, type, email))
            Toast.makeText(getApplicationContext(), "Something is wrong with your text files.",
                    Toast.LENGTH_SHORT).show();
        else {
            cafes = createCafeArrayList(name, location, type, email);
            printListOfCafes(cafes);

            ListView listView = (ListView) findViewById(R.id.cafe_list);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long l) {

                    Intent OpenMenu = new Intent(ListOfCafes.this, ListOfMeals.class);
                    OpenMenu.putExtra("cafe name", cafes.get(position).getCafeName());
                    OpenMenu.putExtra("position", position);
                    deleteFile("counter.txt");
                    WriteToFile("counter.txt",makeNewOrderFileName(ReadFromFileNotAsset("counter.txt")));
                    WriteToFileBuffer("Order"+ReadFromFileNotAsset("counter.txt")+".txt",cafes.get(position).getCafeName()+"\n"+cafes.get(position).getCafeLocation());
                    startActivity(OpenMenu);
                }
            });
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
            Toast.makeText(ListOfCafes.this,"Помилка у читанні файлу",Toast.LENGTH_SHORT).show();
        }
        return text;

    }

    public void WriteToFileBuffer(String filePath,String text){
        try{
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file);
            pw.println(text);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public String makeNewOrderFileName(String text){
        String smth ="";
        try {
            int counter = Integer.parseInt(text);
            counter++;
            smth = Integer.toString(counter);
            }
            catch (Exception e)
            {
            smth="0";
            }
            return smth;
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

    protected void WriteToFile(String file, String text)
    {
        try {
            FileOutputStream fos = openFileOutput(file,Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        }
        catch (IOException e){
            Toast.makeText(ListOfCafes.this,"Error Writing to file",Toast.LENGTH_SHORT).show();
        }
    }



    private void printListOfCafes(ArrayList<Cafe> cafes) {
        CafeAdapter adapter = new CafeAdapter(this, cafes);
        ListView listView = (ListView) findViewById(R.id.cafe_list);
        listView.setAdapter(adapter);
    }


    private boolean isMistakeInFiles(ArrayList<String> name, ArrayList<String> location, ArrayList<String> type, ArrayList<String> email) {
        return (name.size() != location.size() || name.size() != type.size() || location.size() != type.size() ||
        name.size() != email.size() || type.size() != email.size() || location.size() != email.size());
    }

    private ArrayList<Cafe> createCafeArrayList(ArrayList<String> name, ArrayList<String> location, ArrayList<String> type, ArrayList<String> email) {
        ArrayList<Cafe> cafes = new ArrayList<>();
        for (int i = 0; i < name.size(); i++)
            cafes.add(new Cafe(name.get(i), location.get(i), type.get(i), email.get(i)));
        return cafes;
    }
}
