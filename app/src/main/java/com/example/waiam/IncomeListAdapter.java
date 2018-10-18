package com.example.waiam;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class IncomeListAdapter extends RecyclerView.Adapter<IncomeListAdapter.IncomeViewHolder>{
    private final LayoutInflater mInflater;
    private List<Income> mIncomes; //cached copy of incomes
    private IncomeViewModel mIncomeViewModel;   //probably not a good idea to have this here
    private OnRecyclerItemClickListener mRecyclerItemClickListener;

    IncomeListAdapter(Context context, OnRecyclerItemClickListener recyclerItemClickListener){
        mInflater = LayoutInflater.from(context);
        mRecyclerItemClickListener = recyclerItemClickListener;
    }


    class IncomeViewHolder extends RecyclerView.ViewHolder {

        private final TextView DateInView;
        private final TextView HoursWorkedView;
        private final TextView EarningsMadeView;

        public IncomeViewHolder(View itemView) {
            super(itemView);
            // itemView.setOnClickListener(this);
            DateInView = itemView.findViewById(R.id.textView);
            HoursWorkedView = itemView.findViewById(R.id.textView2);
            EarningsMadeView = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRecyclerItemClickListener.onItemViewClick(view, getAdapterPosition());  //potentially use getLayoutPosition() instead
                }
            });
        }
    }

    @Override
    public IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new IncomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IncomeViewHolder holder, int position) {
        if (mIncomes != null) {
            Income current = mIncomes.get(position);
            double hoursWorked = TimeUnit.MILLISECONDS.toHours(current.getTimeWorked());
            holder.DateInView.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault()).format(current.getTimeIn())); //TODO: formatting
            //as these are just numbers, it's not a big deal that im concating text
            holder.HoursWorkedView.setText((int)hoursWorked + "");
            holder.EarningsMadeView.setText("$" + current.getEarnings());
        } else {
            //covers the case of data not being ready yet
            holder.DateInView.setText("No Income");
            holder.HoursWorkedView.setText("No Hours Worked");
            holder.EarningsMadeView.setText("No Earnings");
        }
    }

    void setIncomes(List<Income> incomes){
        mIncomes = incomes;
        notifyDataSetChanged();
    }

    //getItemCount() is called many times, and when it is first called,
    //mIncomes has not been updated (means initially, it's null, and we can't return null)
    @Override
    public int getItemCount() {
        if (mIncomes != null)
            return mIncomes.size();
        else return 0;
    }
}
