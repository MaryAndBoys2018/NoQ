package ua.com.mnbs.noq;

public class Cafe {
    private String mCafeName;
    private String mCafeLocation;
    private String mCafeType;
    private String mCafeEmail;

    Cafe(String cafeName, String cafeLocation, String cafeType, String cafeEmail) {
        mCafeName = cafeName;
        mCafeLocation = cafeLocation;
        mCafeType = cafeType;
        mCafeEmail = cafeEmail;
    }

    public String getCafeName() {
        return mCafeName;
    }

    public String getCafeLocation() {
        return mCafeLocation;
    }

    public String getCafeType() {
        return mCafeType;
    }

    public String getCafeEmail() {
        return mCafeEmail;
    }

}
