package ua.com.mnbs.noq;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Adapter;
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


public class MyOrder extends AppCompatActivity {
    String counter="";
    String file ="Order"+counter+".txt";
    String product_file ="Product"+counter+".txt";
    String product_quantity="ProductQuantity"+counter+".txt";
    String time_file="Time"+counter+".txt";
    String product_price="ProductPrice"+counter+".txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        counter= extras.getString("counter");

        Button orderButton = (Button) findViewById(R.id.button_order);
        //DisplayOrder();
        Toast.makeText(MyOrder.this,Integer.toString(sum()),Toast.LENGTH_LONG);

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
    String getCounter(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        return extras.getString("counter");
    }


    private void sendData() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"crusty@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Замовлення");
        intent.putExtra(Intent.EXTRA_TEXT, "Моє замовлення");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private void printListOfProducts(ArrayList<Product> products) {
        ProductAdapter adapter = new ProductAdapter(this, products);
        ListView listView = (ListView) findViewById(R.id.list_view_my_order);
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Intent goToOrders = new Intent(MyOrder.this, ListOfOrders.class);
            startActivity(goToOrders);
        }
    }

    private ArrayList<String> ReadFromFileBufferedReader(String file) {
        int i = 0;
        ArrayList<String> text=null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.readLine() != null) {
                text.add(i, reader.readLine());
            }

        } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "e.printStackTrace()", Toast.LENGTH_SHORT).show();
        }
        return text;
    }

    private String ReadFromFileNotAsset(String file) {
        String text = "";
        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch (IOException e) {
            Toast.makeText(MyOrder.this, e.getMessage(), Toast.LENGTH_LONG).show();
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


    private ArrayList<Product> products(){

        ArrayList<String> rawProducts =moveIntoArrayList(ReadFromFileNotAsset(product_file));
        ArrayList<String> quantities = moveIntoArrayList(ReadFromFileNotAsset(product_quantity));
        ArrayList<String> prices = moveIntoArrayList(ReadFromFileNotAsset(product_price));
        ArrayList<Product> products= new ArrayList<>();
        for (int i=0;i<(rawProducts.size());i++) {
            products.add(new Product(rawProducts.get(i), prices.get(i + 1), quantities.get(i)));
        }
        return  products;
    }

    int sum(){
        int sum=0;
        for (int i=0;i<products().size();i++)
            sum+=Integer.getInteger(products().get(i).getmQuantity())*Integer.getInteger(products().get(i).getmProductPrice());
        return sum;
    }


    private void DisplayOrder(){

        TextView nameTextView = (TextView) findViewById(R.id.order_cafe_name);
        nameTextView.setText(ReadFromFileNotAsset(file));


        TextView timeTextView = (TextView) findViewById(R.id.order_time);
        timeTextView.setText(ReadFromFileNotAsset(time_file));

        TextView sumTextView = (TextView) findViewById(R.id.sum_text_view);
        sumTextView.setText(Integer.toString(sum()));
        printListOfProducts(products());


    }
}
