package ua.com.mnbs.noq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListOfOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_orders);
    }
    private void printListOfOrders(ArrayList<Order> orders) {
        OrderAdapter adapter = new OrderAdapter(this, orders);
        ListView listView = (ListView) findViewById(R.id.order_list);
        listView.setAdapter(adapter);
    }
}
