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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewIncomeActivity extends AppCompatActivity implements TimePickerFragment.setTimeListener {
    public static final String EXTRA_REPLY = "com.example.android.incomelistsql.REPLY";

    private TextView mEditTimeInView, mEditTimeOutView, mEditEarningsView;
    private Date mDateIn, mDateOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income);
        mEditTimeInView = findViewById(R.id.timein);
        mEditTimeOutView = findViewById(R.id.timeout);

        mEditEarningsView = findViewById(R.id.edit_earnings);   //uses BlacKCaT27 repository. does not allow typing of "." so you have to type two 00's every time you have no decimals, change in future



        //Time in picker
        Button timeInButton = findViewById(R.id.pick_timein);
        timeInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("IS_DATE_IN", true);
                DialogFragment timeFragment = new TimePickerFragment();
                timeFragment.setArguments(bundle);
                timeFragment.show(getSupportFragmentManager(), "v1");//Date in picker

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


                //call from date fragment
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


        //todo: dont allow timout to be < timein
        final FloatingActionButton fabSave = findViewById(R.id.fab);
        fabSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                Bundle allReplies = new Bundle();
                if (mDateIn == null || mDateOut == null || TextUtils.isEmpty(mEditEarningsView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    allReplies.putLong("TIME_IN", mDateIn.getTime());

                    allReplies.putLong("TIME_OUT", mDateOut.getTime());

                    allReplies.putDouble("EARNINGS", formatEarnings(mEditEarningsView.getText().toString())); //earnings reply

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
        //todo doesnt work for ","
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


