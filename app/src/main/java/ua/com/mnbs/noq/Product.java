package ua.com.mnbs.noq;

public class Product {
    private String mProductName;
    private String mProductPrice;
    private String mQuantity;
    Product(String productName, String productPrice, String quantity)
    {
        mProductName=productName;
        mProductPrice=productPrice;
        mQuantity=quantity;
    }

    public String getmProductName() {
        return mProductName;
    }

    public String getmProductPrice() {
        return mProductPrice;
    }

    public String getmQuantity() {
        return mQuantity;
    }
}
