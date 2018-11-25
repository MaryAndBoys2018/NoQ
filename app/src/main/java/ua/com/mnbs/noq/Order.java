package ua.com.mnbs.noq;

public class Order {
    private String mDate;
    private int mHOUR;
    private int mMinute;
    private String mCafe;
    private int mSum;
    private String mProduct;
    private int mQuantity;
    public Order(String product,int hour,int minute,String cafe,int sum,String date,int quantity){
        mHOUR=hour;
        mMinute=minute;
        mCafe=cafe;
        mSum=sum;
        mDate=date;
        mProduct=product;
        mQuantity=quantity;

    }

    public int getmHOUR() {
        return mHOUR;
    }

    public int getmMinute() {
        return mMinute;
    }

    public int getmSum() {
        return mSum;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmCafe() {
        return mCafe;
    }

    public String getmProduct() {
        return mProduct;
    }

    public int getmQuantity() {
        return mQuantity;
    }
}
