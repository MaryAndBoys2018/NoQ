package ua.com.mnbs.noq;

public class CaffeClass {
    private String CaffeName;
    private String CaffeLocation;
    private String CaffeType;
    CaffeClass(String mCaffeeName, String mCaffeLocation, String mCaffeType){
        CaffeName=mCaffeeName;
        CaffeLocation=mCaffeLocation;
        CaffeType=mCaffeType;
    }
    public String getCaffeName(){
        return CaffeName;
    }
    public String getCaffeLocation(){
        return CaffeLocation;
    }
    public String getCaffeType(){
        return CaffeType;
    }

}
