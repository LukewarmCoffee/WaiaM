package com.example.waiam;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "card_table")
public class CardData {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "title")
    private int mTitle; //user will not update this value, make priamary

    @ColumnInfo(name = "content")
    private String mContent;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "vieworder")
    private int mOrder;

    @ColumnInfo(name = "selected")
    private Boolean mSelected;  //todo change this to integer so you know what position on the viewpager

    public CardData(int id, int title, String content, String description, int order, Boolean selected){
        this.id = id;
        mTitle = title;
        mContent = content;
        mDescription = description;
        mOrder = order;
        mSelected = selected;
    }

    public int getId() {return id;}

    public int getTitle(){
        return mTitle;
    }

    public String getContent(){
        return mContent;
    }

    public void setContent(String content)  {mContent = content;}

    public String getDescription() {return mDescription;}

    public int getOrder() {
        return mOrder;
    }

    public Boolean getSelected(){return  mSelected;}

    public void setSelected(boolean selected){mSelected = selected;}
}
