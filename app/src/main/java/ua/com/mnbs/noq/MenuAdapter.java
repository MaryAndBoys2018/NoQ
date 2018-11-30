package ua.com.mnbs.noq;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter<Meal> {
    MenuAdapter(Activity context, ArrayList<Meal> meals) {
        super(context, 0, meals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.menu_list_item, parent, false);
        }

        Meal currentMeal = getItem(position);


        TextView mealNameTextView = (TextView) listItemView.findViewById(R.id.meal_name_text_view);
        mealNameTextView.setText(currentMeal.getMealName());

        TextView priceTypeTextView = (TextView) listItemView.findViewById(R.id.price_type_text_view);
        priceTypeTextView.setText(currentMeal.getMealPrice());



      /* final CheckBox check = (CheckBox) listItemView.findViewById(R.id.meal_checkbox);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_menu, null);
                Button btn = (Button) view.findViewById(R.id.btn);
                if(isChecked){
                    btn.setVisibility(View.VISIBLE);
                }else{
                    btn.setVisibility(View.GONE);
                }

            }
        }); */
        return listItemView;
    }
}
