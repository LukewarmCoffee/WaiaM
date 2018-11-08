package com.example.waiam;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    private Context mContext;

    public GridAdapter(Context context){
        mContext = context;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView == null){
            textView = new TextView(mContext);
            textView.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
            textView.setPadding(8, 8, 8, 8);
        } else {
            textView =  (TextView)convertView;
        }

        textView.setText(mThumbIds[position]);
        return textView;
    }

    private String[] mThumbIds = {"Hourly Wage", "Total Earnings", "Hours Worked"};
}
