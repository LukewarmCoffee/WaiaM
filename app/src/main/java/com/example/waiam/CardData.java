package com.example.waiam;

public class CardData {

    private int mTitle;
    private String mContent;

    public CardData(int title, String content){
        mTitle = title;
        mContent = content;
    }

    public int getTitle(){
        return mTitle;
    }

    public String getContent(){
        return mContent;
    }
}
