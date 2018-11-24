package ua.com.mnbs.noq;

public class Meal {
    private String mMealName;
    private String mMealPrice;
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
}
