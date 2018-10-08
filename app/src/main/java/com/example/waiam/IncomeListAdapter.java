package com.example.waiam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class IncomeListAdapter extends RecyclerView.Adapter<IncomeListAdapter.IncomeViewHolder> {
    class IncomeViewHolder extends RecyclerView.ViewHolder {
        private final TextView incomeItemView;

        private IncomeViewHolder(View itemView) {
            super (itemView);
            incomeItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Income> mIncomes; //cached copy of incomes

    IncomeListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public IncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new IncomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IncomeViewHolder holder, int position) {
        if (mIncomes != null) {
            Income current = mIncomes.get(position);
            double hoursWorked = TimeUnit.MILLISECONDS.toHours(current.getTimeOut().getTime() - current.getTimeIn().getTime());
            holder.incomeItemView.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault()).format(current.getTimeIn())
                    + " " + hoursWorked + " " + current.getEarnings()); //TODO: formatting
        } else {
            //covers the case of data not being ready yet
            holder.incomeItemView.setText("No Income");
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
