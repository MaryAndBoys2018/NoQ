package ua.com.mnbs.noq;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    ProductAdapter(Activity context, ArrayList<Product> products) {
        super(context, 0, products);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.product_list_item, parent, false);
        }
        Product currentProduct = getItem(position);
        TextView ProductNameTextView = (TextView) listItemView.findViewById(R.id.product_name_text_view);
        ProductNameTextView.setText(currentProduct.getmProductName());

        TextView PriceTextView = (TextView) listItemView.findViewById(R.id.product_price_text_view);
        PriceTextView.setText(currentProduct.getmProductName());

        TextView QuantityTextView = (TextView) listItemView.findViewById(R.id.quantity_text_view);
        ProductNameTextView.setText(currentProduct.getmProductName());

        return listItemView;
    }
}
