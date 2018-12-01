package ua.com.mnbs.noq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list_item);
       /*final CheckBox check = findViewById(R.id.meal_checkbox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            Button btn =(Button)findViewById( R.id.btn);
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check.setVisibility(View.VISIBLE);
                }else{
                    check.setVisibility(View.GONE);
                }

            }
        }); */
    }

}
