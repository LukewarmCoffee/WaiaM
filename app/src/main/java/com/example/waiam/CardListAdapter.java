package com.example.waiam;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.TextView;

import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> implements GetPositionList{
    private List<CardData> mCards;// = new ArrayList<>();
    private boolean[] posList = new boolean[3]; //two is the current amount of cards
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

    CardListAdapter (/*List<CardData> getCards,*/ Context context){
       /* try{
            mCards = getCards;
        } catch (Exception e){
            e.printStackTrace();
        }*/
        mInflater = LayoutInflater.from(context);
       // getTitles();
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
                    posList[holder.getAdapterPosition()] = b;
                }
            });
            /*if(!radioStates.get(position, false)){
                holder.mInMainView.setChecked(false);
            } else {
                holder.mInMainView.setChecked(true);
            }*/
           // String currentTitle = titleList.get(position);
            holder.mTitle.setText(isChecked.getTitle());
            holder.mSummary.setText(isChecked.getContent());
        }
        else{
            holder.mTitle.setText("no title");
            holder.mSummary.setText("no summary");
        }
    }

    void setCards(List<CardData> cards){
        mCards = cards;
        posList[0] = mCards.get(0).getSelected();
        posList[1] = mCards.get(1).getSelected();
        posList[2] = mCards.get(2).getSelected();
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


}
