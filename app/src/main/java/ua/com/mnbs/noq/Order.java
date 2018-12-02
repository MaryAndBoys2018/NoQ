package ua.com.mnbs.noq;

public class Order {
    private String mDate;
    private String mTime;
    private String mCafe;
    private String mSum;
    private String mQuantity;
    private String mAdress;
    public Order(String cafe,String adress,String quantity,String sum,String time,String date){
        mTime=time;
        mCafe=cafe;
        mSum=sum;
        mDate=date;
        mQuantity=quantity;
        mAdress=adress;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmSum() {
        return mSum;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmCafe() {
        return mCafe;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public String getmAdress() {
        return mAdress;
    }
}
