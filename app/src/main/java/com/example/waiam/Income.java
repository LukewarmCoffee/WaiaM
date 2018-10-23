package com.example.waiam;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "income_table")
public class Income {

    //todo create key that wont die on the same date and time


    @PrimaryKey(autoGenerate = true)
    private int id;


    @NonNull
    @ColumnInfo(name = "timeIN")
    private Date mTimeIn;

    @ColumnInfo(name = "earnings")
    private double mEarnings;


    @ColumnInfo(name = "timeWorked")
    private long mTimeWorked;

    public Income(int id, @NonNull Date timeIn, long timeWorked, double earnings){
        this.id = id;
        this.mTimeIn = timeIn;
        this.mTimeWorked = timeWorked;
        this.mEarnings = earnings;
    }

    public int getId() {return this.id;}

    public double getEarnings() {return this.mEarnings;}

    public Date getTimeIn() {return this.mTimeIn;}

    public long getTimeWorked() {return this.mTimeWorked;}

}
