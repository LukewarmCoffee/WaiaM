package com.example.waiam;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_INCOME_ACTIVITY_REQUEST_CODE = 1;
    //connects repository to view
    private IncomeViewModel mIncomeViewModel;
    private Toolbar mToolbar;
    //for card views
    private CalcsPagerAdapter mCalcAdapter;
    private CalcsFragmentPagerAdapter mFragmentCalcsAdapter;//todo why unused?


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // mToolbar =  findViewById(R.id.toolbar);s
        setSupportActionBar(mToolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewIncomeActivity.class);
                startActivityForResult(intent, NEW_INCOME_ACTIVITY_REQUEST_CODE);
            }
        });

        final ViewPager viewPager = findViewById(R.id.viewPager);
       // mCalcAdapter = new CalcsPagerAdapter();
        viewPager.setAdapter(mCalcAdapter);
        viewPager.setOffscreenPageLimit(3);




        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final IncomeListAdapter adapter = new IncomeListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mIncomeViewModel = ViewModelProviders.of(this).get(IncomeViewModel.class);

        mIncomeViewModel.getAllIncomes().observe(this, new Observer<List<Income>>() {
            @Override
            public void onChanged(@Nullable final List<Income> incomes) {
                //update the cached copy of the incomes in the adapter
                adapter.setIncomes(incomes);
            }
        });


        final CalcsDataAdapter  calcsDataAdapter = new CalcsDataAdapter();  //connects Calcs data to cards
        // mCalcsViewModel = ViewModelProviders.of(this).get(CalcsViewModel.class);
        mIncomeViewModel.getAllIncomes().observe(this, new Observer<List<Income>>() {
            @Override
            public void onChanged(@Nullable final List<Income> incomes) {
                //update the cached copy of the calcs in the adapter
                calcsDataAdapter.setIncomes(incomes);
                //refresh the card views with the new data
                mCalcAdapter = new CalcsPagerAdapter(); //this works really well to fix the issue of refreshing data, im sure theres some issue i dont know about
                NumberFormat deciForm = new DecimalFormat("##.##");
                mCalcAdapter.addCalcsItem(new CardData(R.string.hourly_wage, "$" + deciForm.format(calcsDataAdapter.getHourlyWage())));
                mCalcAdapter.addCalcsItem(new CardData(R.string.total_earnings,"$" + deciForm.format(calcsDataAdapter.getTotalEarnings())));
                mCalcAdapter.addCalcsItem(new CardData(R.string.total_hoursworked, deciForm.format(calcsDataAdapter.getTotalHoursWorked())));
                viewPager.setAdapter(mCalcAdapter);
                //todo expert: modify old cards without replacing any
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_INCOME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle dataReplies = data.getExtras();
            //income_table
            Date dateIn = new Date(dataReplies.getLong("TIME_IN"));
            long timeWorked = dataReplies.getLong("TIME_OUT") - dateIn.getTime();
            double earnings = dataReplies.getDouble("EARNINGS");

            Income income = new Income(dateIn,timeWorked, earnings );
            mIncomeViewModel.insert(income);

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,   //todo: change this so the user doesnt have to go to another screen just to see the not ok result.
                    Toast.LENGTH_LONG).show();
        }
    }
}
