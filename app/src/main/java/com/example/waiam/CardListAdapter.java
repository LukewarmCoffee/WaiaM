package com.example.waiam;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> implements GetPositionList{
    private List<CardData> mCards;
    private boolean[] posList = new boolean[3]; //three is the current amount of cards, this is extremely lazy programming. I am sorry
    private List<Integer> orderList;
    private final LayoutInflater mInflater;

    static class CardViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitle;
        private TextView mSummary;
        private TextView mOrder;
        private CheckBox mInMainView;

        public CardViewHolder(View view){
            super(view);
            mTitle = view.findViewById(R.id.titleView);
            mSummary = view.findViewById(R.id.summaryView);
            mOrder = view.findViewById(R.id.orderView);
            mInMainView = view.findViewById(R.id.inMainButton);
        }
    }

    CardListAdapter ( Context context){
        mInflater = LayoutInflater.from(context);

    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        if (mCards != null) {
            final CardData isChecked = mCards.get(position);
            holder.mInMainView.setOnCheckedChangeListener(null);
            holder.mInMainView.setChecked(isChecked.getSelected());
            holder.mInMainView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    isChecked.setSelected(b);
                    int pos = holder.getAdapterPosition();
                    posList[pos] = b;
                    if (b){
                        orderList.set(pos, findValue());
                        holder.mOrder.setText(findValue() + "");
                    } else {
                        orderList.set(pos, 0);
                        holder.mOrder.setText(0 + "");
                    }
                }
            });
            holder.mTitle.setText(isChecked.getTitle());
            holder.mSummary.setText(isChecked.getDescription());
            //holder.mOrder.setText(orderList.get(position) + "");

        }
        else{
            holder.mTitle.setText("no title");
            holder.mSummary.setText("no summary");
        }
    }

    void setCards(List<CardData> cards){
        mCards = cards;
        orderList = new ArrayList<>();
        for (int i = 0; i < mCards.size(); i++) {
            if (posList[i] = mCards.get(i).getSelected())
                orderList.add(mCards.get(i).getOrder());
            else
                orderList.add(0);
        }



        //currently useless
        //gets ordered list for viewpager. also yikes on efficiency
        List<CardData> orderList =  new ArrayList<>();
        int max = 0;
        for (int i = 0; i < mCards.size(); i++)
            if(mCards.get(i).getOrder() > max)
                max = mCards.get(i).getOrder();

        for (int i = 0; i < max; i++) {
            for (int j = 0; j < mCards.size(); j++) {
                if (mCards.get(j).getOrder() == i+1) {
                    orderList.add(mCards.get(j));
                }
            }
        }




        notifyDataSetChanged();
    }

    public boolean[] returnPositions(){
        return posList;
    }


    @Override
    public int getItemCount() {
        if (mCards != null)
            return mCards.size();
        else
            return 0;
    }

    //currently useless
    private int findValue(){
        int max = 0;
        int items = 0;
        for (int i = 0; i < mCards.size(); i++){
            int orderVal = mCards.get(i).getOrder();
            if (orderVal > 0)
                items++;
            if(orderVal > max)
                max = orderVal;

            int j;
            if ((j = mCards.get(i).getOrder()) >= max)
                max = j;
        }
        return max;
    }



}
