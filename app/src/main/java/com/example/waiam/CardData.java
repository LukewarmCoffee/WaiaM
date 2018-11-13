package com.example.waiam;

public class CardData {

    private int mTitle;
    private String mContent;
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
