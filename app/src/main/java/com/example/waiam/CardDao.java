package com.example.waiam;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CardDao {
    @Insert
    void insert(CardData card);

    @Delete
    void delete(CardData card);

    @Update
    void update(CardData card);

    @Query("DELETE FROM card_table")
    void deleteAll();

    @Query("SELECT * FROM card_table ORDER BY vieworder DESC")
    LiveData<List<CardData>> getAllCards();

    @Query("SELECT max(vieworder) FROM card_table")
    int highestOrder();
}
