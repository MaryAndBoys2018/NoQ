package ua.com.mnbs.noq;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuantityAdapter extends ArrayAdapter<Meal> {
    QuantityAdapter (Activity context, ArrayList<Meal> meals) {
        super(context, 0, meals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_quantity, parent, false);
        }
        final Meal currentMeal = getItem(position);


        TextView mealNameTextView = (TextView) listItemView.findViewById(R.id.meal_name_text_view);
        mealNameTextView.setText(currentMeal.getMealName());

        final TextView priceTypeTextView = (TextView) listItemView.findViewById(R.id.price_type_text_view);
        priceTypeTextView.setText(currentMeal.getMealPrice());

        Button IncrementButton = (Button) listItemView.findViewById(R.id.increment);

        IncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMeal.getQuantity()==20){
                    return;
                }
                currentMeal.setQuantity(currentMeal.getQuantity()+1);
                priceTypeTextView.setText(currentMeal.getQuantity()*Integer.parseInt(currentMeal.getMealPrice()));
            }
        });

        Button DecrementButton = (Button) listItemView.findViewById(R.id.decrement);

        DecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMeal.getQuantity()==1){
                    return;
                }
                currentMeal.setQuantity(currentMeal.getQuantity()-1);
                priceTypeTextView.setText(currentMeal.getQuantity()*Integer.parseInt(currentMeal.getMealPrice()));
            }
        });

        return listItemView;
    }
}
