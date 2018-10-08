package com.example.waiam;

public class TimeObj {
    private int mDay, mMonth, mYear;
    private boolean mDateIn;

    public TimeObj(int day, int month, int year, boolean dateIn){
        mDay = day;
        mMonth = month;
        mYear = year;
        mDateIn = dateIn;
    }

    public int getDay(){
        return mDay;
    }

    public int getMonth(){
        return mMonth;
    }

    public int getYear(){
        return mYear;
    }

    public boolean getDateIn(){
        return mDateIn;
    }
}