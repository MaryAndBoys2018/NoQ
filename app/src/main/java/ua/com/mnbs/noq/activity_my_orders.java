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


public class activity_my_orders extends AppCompatActivity {
    ArrayList<Product> products;
    String file =("Order"+ReadFromFileNotAsset("counter.txt")+".txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        Button orderButton = (Button) findViewById(R.id.button_order);
        products =new ArrayList<>();
        DisplayOrder();
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
            Intent goToOrders = new Intent(activity_my_orders.this, ListOfOrders.class);
            startActivity(goToOrders);
        }
    }

    private ArrayList<String> ReadFromFileBufferedReader(String file) {
        int i = 0;
        ArrayList<String> text = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.readLine() != null) {
                text.add(i, reader.readLine());
            }

        } catch (IOException e) {
            Toast.makeText(activity_my_orders.this, "Помилка у читанні файлу", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(activity_my_orders.this, "Помилка у читанні файлу", Toast.LENGTH_SHORT).show();
        }
        return text;

    }
    private ArrayList<String> OnlyOrder(ArrayList<String> orderlist){
        ArrayList<String> DisplayOrder= new ArrayList<String>();
        DisplayOrder.add(orderlist.get(1));//cafe name
        DisplayOrder.add(orderlist.get(2));//cafe location
        DisplayOrder.add(orderlist.get(orderlist.size()-2));//sum
        DisplayOrder.add(orderlist.get(orderlist.size()-1));//time
        DisplayOrder.add(orderlist.get(orderlist.size()));//date
        return DisplayOrder;
    }

    private ArrayList<String> NameArrayList(ArrayList<String> list){
        ArrayList<String> NameArrayList= new ArrayList<>();
        for (int i=3;i<(list.size()-3);i+=3){
            NameArrayList.add(list.get(i));
        }
        return NameArrayList;
    }

    private ArrayList<String> QuantityArrayList(ArrayList<String> list){
        ArrayList<String> QuantityArrayList= new ArrayList<>();
        for (int i=3+(2*QuantityArrayList(ReadFromFileBufferedReader(file)).size());i<(list.size()-3);i++){
            QuantityArrayList.add(list.get(i));
        }
        return QuantityArrayList;
    }

    private ArrayList<String> PriceArrayList(ArrayList<String> list){
        ArrayList<String> PriceArrayList= new ArrayList<>();
        for (int i=4;i<(list.size()-3);i+=3){
            PriceArrayList.add(list.get(i));
        }
        return PriceArrayList;
    }

    private ArrayList<Product> products(ArrayList<String> Name,ArrayList<String> Quantity, ArrayList<String> Price){
        ArrayList<Product> products= new ArrayList<>();
        for (int i=0;i<Name.size();i++){
            products.add(new Product(Name.get(i),Price.get(i),Quantity.get(i)));
        }
        return products;
    }

    int sum(){
        int sum=0;
        for (int i=0;i<QuantityArrayList(ReadFromFileBufferedReader(file)).size();i++)
            sum+=Integer.parseInt(QuantityArrayList(ReadFromFileBufferedReader(file)).get(i))*Integer.parseInt(PriceArrayList(ReadFromFileBufferedReader(file)).get(i));
        return sum;
    }


    private void DisplayOrder(){
        ArrayList<String> order = OnlyOrder(ReadFromFileBufferedReader("Order" + ReadFromFileNotAsset("counter.txt") + ".txt"));

        printListOfProducts(products);

        TextView nameTextView = (TextView) findViewById(R.id.order_cafe_name);
        nameTextView.setText(order.get(1));

        TextView timeTextView = (TextView) findViewById(R.id.order_time);
        timeTextView.setText(order.get(order.size()-1));

        TextView sumTextView = (TextView) findViewById(R.id.sum_text_view);
        sumTextView.setText(Integer.toString(sum()));


    }
}
