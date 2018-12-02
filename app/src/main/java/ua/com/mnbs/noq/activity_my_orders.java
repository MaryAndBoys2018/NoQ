package ua.com.mnbs.noq;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.view.View;
import android.widget.Button;


public class activity_my_orders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        Button orderButton = (Button) findViewById(R.id.button_order);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"crusty@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Замовлення");
                intent.putExtra(Intent.EXTRA_TEXT, "Моє замовлення");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }

            }
        });
    }

    private void sendData(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ "crusty@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Замовлення");
        intent.putExtra(Intent.EXTRA_TEXT, "Моє замовлення");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

        @Override   public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Intent goToOrders = new Intent(activity_my_orders.this, ListOfOrders.class);
            startActivity(goToOrders);
        }
        }
private ArrayList<String> ReadFromFileBufferedReader(String file){
        int i=0;
        ArrayList<String> text = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.readLine()!=null){
                text.add(i,reader.readLine());
            }

        }
        catch (IOException e) {
                Toast.makeText(activity_my_orders.this,"Помилка у читанні файлу",Toast.LENGTH_SHORT).show();
        }
        return text;
    }

    private ArrayList<Product> createMealArrayList(ArrayList<String> name,ArrayList<String> price, ArrayList<String> quantity) {
        ArrayList<Product> order = new ArrayList<>();
        for (int i = 0; i < name.size(); i++)
            order.add(new Product(name.get(i),price.get(i),quantity.get(i)));
        return order;
    }

    private String ReadFromFileNotAsset(String file){
        String text= "";
        try{
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        }
        catch (IOException e) {
                Toast.makeText(activity_my_orders.this,"Помилка у читанні файлу",Toast.LENGTH_SHORT).show();
        }
        return text;

}
