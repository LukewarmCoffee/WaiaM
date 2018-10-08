package com.example.waiam;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CalcsFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardDataAdapter{
    private List<CardFragment> mFrags;

    public CalcsFragmentPagerAdapter(FragmentManager fm){
        super(fm);
        mFrags = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            addCardFragment(new CardFragment());
        }
    }

    @Override
    public int getCount() {
        return mFrags.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFrags.get(position);
    }

    @Override
    public CardView getCardViewAt(int position){
        return mFrags.get(position).getCardView();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFrags.set(position, (CardFragment) fragment);
        return fragment;
    }


    public void addCardFragment(CardFragment frag){
        mFrags.add(frag);
    }
}
