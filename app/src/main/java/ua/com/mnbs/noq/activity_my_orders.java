package ua.com.mnbs.noq;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

}
