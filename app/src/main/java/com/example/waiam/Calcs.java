package com.example.waiam;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "calculated_table")
public class Calcs {
    //todo foreign key timein


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "timeIn")
    private Date mTimeIn;

    @ColumnInfo(name = "earnings")
    private double mEarnings;

    @ColumnInfo(name = "hoursWorked")
    private double mHoursWorked;

    @ColumnInfo(name = "hourlyWage")
    private double mHourlyWage;



    public Calcs(Date timeIn, double hoursWorked, double hourlyWage, double earnings){
        this.mTimeIn = timeIn;
        this.mHoursWorked = hoursWorked;
        this.mHourlyWage = hourlyWage;
        this.mEarnings = earnings;
    }

    public Date getTimeIn() {return this.mTimeIn;}

    public double getHoursWorked() {return this.mHoursWorked;}

    public double getHourlyWage() {return this.mHourlyWage;}

    public double getEarnings() {return this.mEarnings;}
}
