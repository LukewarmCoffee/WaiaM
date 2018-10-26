package com.example.waiam;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewIncomeActivity extends AppCompatActivity implements TimePickerFragment.setTimeListener {
    private int mId = 0;
    private double mEarnings;

    private TextView mEditTimeInView, mEditTimeOutView, mEditEarningsView;
    private Date mDateIn, mDateOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mEditTimeInView = findViewById(R.id.timein);
        mEditTimeOutView = findViewById(R.id.timeout);
        mEditEarningsView = findViewById(R.id.edit_earnings);   //uses BlacKCaT27 repository. does not allow typing of "." so you have to type two 00's every time you have no decimals, change in future

        //2 is the edit_activity request code
        if (bundle.getInt("requestCode") ==  2) {
            mId = bundle.getInt("id");
            mDateIn =new Date(bundle.getLong("timeIn"));
            mDateOut = new Date(mDateIn.getTime() + bundle.getLong("timeWorked"));
            mEarnings = bundle.getDouble("earnings");

            mEditTimeInView.setText(mDateIn + "");
            mEditTimeOutView.setText(mDateOut + "");
            mEditEarningsView.setText("$" + String.format("%,.2f", mEarnings));
        }


        //Time in picker
        Button timeInButton = findViewById(R.id.pick_timein);
        timeInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("IS_DATE_IN", true);

                //this appears first
                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.setArguments(bundle);
                timeFragment.show(getSupportFragmentManager(), "v1");//Date in picker
                //date frag is simply drawn over the time frag
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.setArguments(bundle);
                dateFragment.show(getSupportFragmentManager(), null);
            }
        });



        Button timeInNowButton = findViewById(R.id.timeinset_now);
        timeInNowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mDateIn = Calendar.getInstance().getTime();
                mEditTimeInView.setText(mDateIn + "");
            }
        });

        //Time out picker
        Button timeOutButton = findViewById(R.id.pick_timeout);
        timeOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("IS_DATE_IN", false);

                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.setArguments(bundle);
                timeFragment.show(getSupportFragmentManager(), null);

                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.setArguments(bundle);
                dateFragment.show(getSupportFragmentManager(), null);
            }
        });


        Button timeOutNowButton = findViewById(R.id.timeoutset_now);
        timeOutNowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mDateOut = Calendar.getInstance().getTime();
                mEditTimeOutView.setText(mDateOut + "");
            }
        });


        final FloatingActionButton fabSave = findViewById(R.id.fab);
        fabSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                Bundle allReplies = new Bundle();
                double earnings = formatEarnings(mEditEarningsView.getText().toString());
                //if you have incomplete fields, it takes you back to main. mEditEarningsView should never be empty in this current iteration
                if (mDateIn == null || mDateOut == null || TextUtils.isEmpty(mEditEarningsView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    if(mDateOut.getTime() <= mDateIn.getTime()) {
                        Toast.makeText(getApplicationContext(), "Time out cant be less than or equal to time in", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(earnings <= 0) {
                        Toast.makeText(getApplicationContext(), "Earnings cannot be less than or equal to $0", Toast.LENGTH_LONG).show();
                        return;
                    }

                    allReplies.putInt("ID", mId);   //this value only matters if we're editing an income

                    allReplies.putLong("TIME_IN", mDateIn.getTime());

                    allReplies.putLong("TIME_OUT", mDateOut.getTime());

                    allReplies.putDouble("EARNINGS", earnings); //earnings reply

                    replyIntent.putExtras(allReplies);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }

    //formats time as a date object for ease of maths
    public Date formatTime(String dateAndTime){
        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm", Locale.getDefault());  //use template SimpleDateFormat(String template, Locale locale) when extending this app to multiple countries
        Date date = new Date();
        try{
            date = format.parse(dateAndTime);
        }catch(ParseException e){
            e.printStackTrace();    //todo actual exception lmao
        }
        return date;
    }

    public double formatEarnings(String earnings){
        earnings = earnings.replace(",", "");
        return Double.parseDouble(earnings.substring(1));   //the first character from earnings is $
    }

    //timepickerfragment interface
    public void setTime(Date date, boolean dateIn){
        if (dateIn){
            mDateIn = date;
            mEditTimeInView.setText(date + "");
        }

        if (!dateIn){
            mDateOut = date;
            mEditTimeOutView.setText(date + "");
        }
    }




}


