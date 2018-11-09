package com.example.waiam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    private List<String> titleList =  new ArrayList<>();
    private List<String> summaryList = new ArrayList<>();
    private List<Integer> mainViewList = new ArrayList<>();
    private final LayoutInflater mInflater;

    static class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mSummary;
        private TextView mOrder;
        private RadioButton mInMainView;

        public CardViewHolder(View view){
            super(view);
            mTitle = view.findViewById(R.id.titleView);
            mSummary = view.findViewById(R.id.summaryView);
            mOrder = view.findViewById(R.id.orderView);
            mInMainView = view.findViewById(R.id.inMainButton);
        }
    }

    CardListAdapter (Context context){
        mInflater = LayoutInflater.from(context);
        getTitles();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        if (titleList != null) {
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
