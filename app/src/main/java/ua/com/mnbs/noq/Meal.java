package ua.com.mnbs.noq;

public class Meal {
    private String mMealName;
    private String mMealPrice;
    private boolean mIsChecked;
    Meal(String mealName, String mealPrice){
        mMealName = mealName;
        mMealPrice = mealPrice;
    }
    public String getMealName(){
        return mMealName;
    }
    public String getMealPrice(){
        return mMealPrice;
    }
    public boolean getChecked() { return mIsChecked; }
    public void setChecked(boolean isChecked){
        mIsChecked = isChecked;
    }
}
