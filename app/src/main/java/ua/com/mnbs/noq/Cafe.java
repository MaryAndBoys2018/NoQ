package ua.com.mnbs.noq;

public class Cafe {
    private String mCafeName;
    private String mCafeLocation;
    private String mCafeType;
    Cafe(String cafeName, String cafeLocation, String cafeType){
        mCafeName = cafeName;
        mCafeLocation = cafeLocation;
        mCafeType = cafeType;
    }
    public String getCafeName(){
        return mCafeName;
    }
    public String getCafeLocation(){
        return mCafeLocation;
    }
    public String getCafeType(){
        return mCafeType;
    }

}
