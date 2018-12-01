package ua.com.mnbs.noq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuantityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);
        Button btn = (Button) findViewById(R.id.btn1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent OpenQuantityActivity = new Intent(QuantityActivity.this, TimeActivity.class);
                startActivity(OpenQuantityActivity);

            }
        });
    }
    int quantity=1;

    public void increment(View view) {
        if (quantity==20){
            Toast.makeText(this, "Ууупс! Не вибирайте так багато. Ми не впораємося:)", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity= quantity+1;
        displayQuantity(quantity);
    }
    public void decrement(View view) {
        if (quantity==1) {
            Toast.makeText(this, "Не можна вибрати від'ємну кількість страв:)", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity= quantity-1;
        displayQuantity(quantity);
    }
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}
