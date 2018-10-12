package com.example.waiam;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//the idea from this class comes from someones repository, todo make sure to credit author
public class CalcsPagerAdapter extends PagerAdapter {
    private List<CardData> mData;
    private List<CardView> mViews;


    public CalcsPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void addCalcsItem(CardData data) {
        mViews.add(null);
        mData.add(data);
    }

    @Override
    public int getCount(){
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter, container, false);
        container.addView(view);
        bindViewToData(mData.get(position), view);
        CardView cardView = view.findViewById(R.id.cardView);
        mViews.set(position, cardView);
        return view;
    }

    private void bindViewToData(CardData data, View view){
        TextView titleText = view.findViewById(R.id.titleText);
        TextView contentText = view.findViewById(R.id.contentText);
        titleText.setText(data.getTitle());
        contentText.setText(data.getContent());
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }





}
