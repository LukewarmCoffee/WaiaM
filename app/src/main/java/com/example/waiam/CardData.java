package com.example.waiam;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "card_table")
public class CardData {

    @PrimaryKey
    @ColumnInfo(name = "title")
    private int mTitle; //user will not update this value, make priamary

    @ColumnInfo(name = "content")
    private String mContent;

    @ColumnInfo(name = "selected")
    private Boolean mSelected;  //todo change this to integer so you know what position on the viewpager

    public CardData(int title, String content, Boolean selected){
        mTitle = title;
        mContent = content;
        mSelected = selected;
    }

    public int getTitle(){
        return mTitle;
    }

    public String getContent(){
        return mContent;
    }

    public Boolean getSelected(){return  mSelected;}

    public void setSelected(boolean selected){mSelected = selected;}
}
