package ua.com.mnbs.noq;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {

    ArrayList<Meal> meals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final String userName = extras.getString("UserName");
        final int numberOfCheckedItems = extras.getInt("number of checked items");
        final String nameOfCafe = extras.getString("cafe name");
        final String cafeAddress = extras.getString("cafe address");
        final String orderTime = extras.getString("order time");
        final String cafeEmail = extras.getString("email");


        meals = new ArrayList<>();

        String tempName = "";
        String tempPrice = "";
        int tempQuantity;
        int totalPrice = 0;

        for (int i=0; i<numberOfCheckedItems; i++){
            tempName = extras.getString("meal name"+i);
            tempPrice = extras.getString("meal price"+i);
            tempQuantity = extras.getInt("meal quantity"+i);
            meals.add(new Meal(tempName, tempPrice));
            meals.get(i).setQuantity(tempQuantity);
            totalPrice += Integer.parseInt(meals.get(i).getMealPrice()) * meals.get(i).getQuantity();
        }
        String orderSummary;
        orderSummary = "Замовлення:\n";
        orderSummary += "Користувач: " + userName;
        orderSummary += "\nЗаклад: " + nameOfCafe;
        orderSummary += "\nАдреса: " + cafeAddress;
        orderSummary += "\nЧас отримання: " + orderTime;
        orderSummary += "\nЗамовлені страви:";
        for (int i=0; i <numberOfCheckedItems; i++){
            orderSummary += "\n" + meals.get(i).getMealName() + " - " +
                    meals.get(i).getQuantity() + "шт. - " +
                    Integer.parseInt(meals.get(i).getMealPrice()) * meals.get(i).getQuantity() + " грн";
        }

        final String finalOrder = orderSummary;

        displayOrder(nameOfCafe, cafeAddress, meals, totalPrice, orderTime);

        Button orderButton = (Button) findViewById(R.id.button_order);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{cafeEmail});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Замовлення");
                intent.putExtra(Intent.EXTRA_TEXT, finalOrder);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }

            }
        });

        ImageView buttonToMain = (ImageView) findViewById(R.id.horse_icon_from_my_order);

        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMainActivity = new Intent(MyOrdersActivity.this, MainActivity.class);
                startActivity(toMainActivity);
            }
        });

        ImageView buttonToTimeActivity = (ImageView) findViewById(R.id.back_from_my_order);

        buttonToTimeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTimeActivity = new Intent(MyOrdersActivity.this, TimeActivity.class);
                startActivity(toTimeActivity);
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
            Intent goToOrders = new Intent(MyOrdersActivity.this, ListOfOrders.class);
            startActivity(goToOrders);
            overridePendingTransition(R.anim.from_bottom_to_top, R.anim.from_bottom_to_top_exit);
        }
        }
        private  void displayOrder(String cafeName, String cafeAddress, ArrayList<Meal> meals, int totalPrice, String time)
        {
            TextView nameTextView = (TextView) findViewById(R.id.place);
            TextView locationTextView = (TextView) findViewById(R.id.adress);
            nameTextView.setText(cafeName);
            locationTextView.setText(cafeAddress);

            MyOrderAdapter adapter = new MyOrderAdapter(this, meals);
            ListView listView = (ListView) findViewById(R.id.list_view_my_order);
            listView.setAdapter(adapter);

            TextView totalTextView = (TextView) findViewById(R.id.total_field);
            totalTextView.setText(String.valueOf(totalPrice) + " грн");

            TextView timeTextView = (TextView) findViewById(R.id.time_field);
            timeTextView.setText(time);
        }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_top_to_bottom_exit, R.anim.from_top_to_bottom);
    }

}
