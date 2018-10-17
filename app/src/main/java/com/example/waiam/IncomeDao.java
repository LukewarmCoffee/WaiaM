package com.example.waiam;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface IncomeDao {

    @Insert
    void insert(Income income);

    @Delete
    void delete(Income income);

    @Query("DELETE FROM income_table")
    void deleteAll();

    @Query("SELECT * FROM income_table ORDER BY timeIn DESC")
    LiveData<List<Income>> getAllIncomes();

}
