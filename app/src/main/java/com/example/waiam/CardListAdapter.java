package com.example.waiam;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> /*implements View.OnClickListener*/{
    private List<String> titleList =  new ArrayList<>();
    private List<String> summaryList = new ArrayList<>();
    private List<Boolean> mainViewList = new ArrayList<>();
    private List<CardData> mCards = new ArrayList<>();
    SparseBooleanArray radioStates = new SparseBooleanArray();
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

    CardListAdapter (List<CardData> getCards, Context context){
        try{
            mCards = getCards;
        } catch (Exception e){
            e.printStackTrace();
        }
        mInflater = LayoutInflater.from(context);
        getTitles();
    }

  /*  @Override
    public void onClick(View view) {
        ConstraintLayout layout = (ConstraintLayout) view.getParent();
        int adapterPosition =
        if(!radioStates.get(adapterPosition, false)){
            (RadioButton)view.setChecked(true);
            radioStates.put(adapterPosition, true);
        }else {
            mInMainView.setChecked(false);
            radioStates.put(adapterPosition, false);
        }
    }*/

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, final int position) {
        if (titleList != null) {
            final CardData isChecked = mCards.get(position);
            holder.mInMainView.setOnCheckedChangeListener(null);
            holder.mInMainView.setChecked(isChecked.getSelected());
            holder.mInMainView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    isChecked.setSelected(b);
                }
            });
            /*if(!radioStates.get(position, false)){
                holder.mInMainView.setChecked(false);
            } else {
                holder.mInMainView.setChecked(true);
            }*/
            String currentTitle = titleList.get(position);
            holder.mTitle.setText(currentTitle);
            String currentSummary = summaryList.get(position);
            holder.mSummary.setText(currentSummary);
        }
        else{
            holder.mTitle.setText("no title");
            holder.mSummary.setText("no summary");
        }
    }

    //todo move this out
    public void getTitles(){
        titleList.add("SDf");
        titleList.add("SDf");
        titleList.add("SDf");
        titleList.add("SDf");
        summaryList.add("sgsd");
        summaryList.add("sgsd");
        summaryList.add("sgsd");
        summaryList.add("sgsd");

    }

    @Override
    public int getItemCount() {
        if (titleList != null)
            return titleList.size();
        else
            return 0;
    }


}
