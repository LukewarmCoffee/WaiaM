package com.example.waiam;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CalcsDao {
    @Insert
    void insert(Calcs calcs);

    //todo: insert from outside dao
    //@Query("SELECT((timeOut - timeIn) / earnings ) AS hourlyWage, timeIn AS timeIn FROM income_table")
    //LiveData<List<Calcs>> setHourlyWage();

    @Query("DELETE FROM calculated_table")
    void deleteAll();

    @Query("SELECT * FROM calculated_table ORDER BY timeIn DESC")
    LiveData<List<Calcs>> getAllCalcs();
}
