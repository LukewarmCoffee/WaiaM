package com.example.waiam;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "income_table")
public class Income {

    //todo create key that wont die on the same date and time

    @ColumnInfo(name = "earnings")
    private double mEarnings;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "timeIN")
    private Date mTimeIn;

    @ColumnInfo(name = "timeOut")
    private Date mTimeOut;

    public Income(Date timeIn, Date timeOut, double earnings){
        this.mTimeIn = timeIn;
        this.mTimeOut = timeOut;
        this.mEarnings = earnings;
    }


    public double getEarnings() {return this.mEarnings;}

    public Date getTimeIn() {return this.mTimeIn;}

    public Date getTimeOut() {return this.mTimeOut;}

}
