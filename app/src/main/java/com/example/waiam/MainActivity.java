package com.example.waiam;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_INCOME_ACTIVITY_REQUEST_CODE = 1;
    public static final int NEW_INCOME_ACTIVITY_EDIT_REQUEST_CODE = 2;
    public static final int CARD_EDIT_REQUEST_CODE = 3;
    //connects repository to view
    private IncomeViewModel mIncomeViewModel;
    private CardViewModel mCardViewModel;
    private Integer mPosition;
    private View highlightedView;
    //for card views, each method creates a card
    private CalcsPagerAdapter mCalcAdapter;
    private SharedPreferences mPrefs;

    private ActionMode.Callback mModeCallBack;

    private List<CardData> mCards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.NightMode);

        /*if (mPrefs.getBoolean("nightmode", false))
            setTheme(R.style.NightMode);
        else
            setTheme(R.style.AppTheme);*/
        setContentView(R.layout.activity_main);

        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                        // listener implementation
                        if(key.equals("nightmode"))
                            recreate();
                    }
                };
        mPrefs.registerOnSharedPreferenceChangeListener(listener);



        mModeCallBack = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                //inflate resource with items
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                highlightedView.setActivated(true);
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_edit:
                        if(mPosition != null)
                            editItem(mPosition);
                        actionMode.finish();
                        return true;
                    case R.id.menu_delete:
                        if (mPosition != null)
                            deleteItem(mPosition);
                        actionMode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                highlightedView.setActivated(false);
                actionMode = null;
            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewIncomeActivity.class);
                startActivityForResult(intent, NEW_INCOME_ACTIVITY_REQUEST_CODE);
            }
        });

        OnRecyclerItemClickListener recyclerItemClickListener = new OnRecyclerItemClickListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                mPosition = position;   //global variable... scary
                if (highlightedView != null && highlightedView.isActivated())
                    highlightedView.setActivated(false);
                //view.setActivated(true);
                highlightedView = view;
                //todo highlight selection
                ((Activity) view.getContext()).startActionMode(mModeCallBack);
            }
        };

        //holds the card views
        final ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(mCalcAdapter);
        viewPager.setOffscreenPageLimit(3);


        //holds the income list
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final IncomeListAdapter adapter = new IncomeListAdapter(this, recyclerItemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mCardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        mCardViewModel.getAllCards().observe(this, new Observer<List<CardData>>() {
            @Override
            public void onChanged(@Nullable List<CardData> cardData) {
                mCards = cardData;
            }
        });

        final CalcsDataAdapter  calcsDataAdapter = new CalcsDataAdapter();  //connects Calcs data to cards
        mIncomeViewModel = ViewModelProviders.of(this).get(IncomeViewModel.class);
        mIncomeViewModel.getAllIncomes().observe(this, new Observer<List<Income>>() {
            @Override
            public void onChanged(@Nullable final List<Income> incomes) {
                //update the cached copy of the incomes in the adapter
                adapter.setIncomes(incomes);
                //again, our card adapter
                calcsDataAdapter.setIncomes(incomes);
                //refresh the card views with the new data
                //every time theres a change to incomes, we completely refresh our cards. This tends to be a bit expensive, especially since each method is O(n) rn
                mCalcAdapter = new CalcsPagerAdapter();
                NumberFormat deciForm = new DecimalFormat("##.##");
                /*for (int i = 0; i < adapterLength; i++){
                    mCalcAdapter.addCalcsItem(card(i));
                }*/

                mCalcAdapter.addCalcsItem(new CardData(0, R.string.hourly_wage, "$" + deciForm.format(calcsDataAdapter.getHourlyWage()), true));
                mCalcAdapter.addCalcsItem(new CardData(1, R.string.total_earnings,"$" + deciForm.format(calcsDataAdapter.getTotalEarnings()), true));
                mCalcAdapter.addCalcsItem(new CardData(2, R.string.total_hoursworked, deciForm.format(calcsDataAdapter.getTotalHoursWorked()), true));
                viewPager.setAdapter(mCalcAdapter);
                //todo expert: modify old cards without replacing any
            }
        });

    }

    void deleteItem(Integer position){
        mIncomeViewModel.delete(position);
    }

    //newincomeactivity is started, but it knows the old income
    void editItem(Integer position){
        Intent intent = new Intent(MainActivity.this, NewIncomeActivity.class);
        Bundle incomeBun = new Bundle();
        Income income = mIncomeViewModel.get(position);
        incomeBun.putInt("id", income.getId());
        incomeBun.putLong("timeIn", income.getTimeIn().getTime());
        incomeBun.putLong("timeWorked", income.getTimeWorked());
        incomeBun.putDouble("earnings", income.getEarnings());
        intent.putExtras(incomeBun);
        startActivityForResult(intent, NEW_INCOME_ACTIVITY_EDIT_REQUEST_CODE);
    }

    //currently useless
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_nightmode:
                if(mPrefs.getBoolean("nightmode", false)) {
                    mPrefs.edit().putBoolean("nightmode", false).apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                else {
                    mPrefs.edit().putBoolean("nightmode", true).apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
                return true;
            case R.id.menu_edit:
                startActivityForResult(new Intent(this, CardPicker.class), CARD_EDIT_REQUEST_CODE);
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /*@Override
    public Resources.Theme getTheme(){
        Resources.Theme theme = super.getTheme();
        if (mNightMode)
            theme.applyStyle(R.style.NightMode, true);
        else
            theme.applyStyle(R.style.AppTheme, true);
        return theme;
    }*/

    @Override
    public void startActivityForResult(Intent intent, int requestCode){
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    //method comes from newIncomeactivity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_INCOME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle dataReplies = data.getExtras();
            //income_table
            Date dateIn = new Date(dataReplies.getLong("TIME_IN")); //todo handle null
            long timeWorked = dataReplies.getLong("TIME_OUT") - dateIn.getTime();
            double earnings = dataReplies.getDouble("EARNINGS");

            Income income = new Income(0, dateIn, timeWorked, earnings);
            //Use the view model to insert an income into the db
            mIncomeViewModel.insert(income);
        }else if (requestCode == NEW_INCOME_ACTIVITY_EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle dataReplies = data.getExtras();
            //income_table
            int id = dataReplies.getInt("ID");
            Date dateIn = new Date(dataReplies.getLong("TIME_IN")); //todo handle null
            long timeWorked = dataReplies.getLong("TIME_OUT") - dateIn.getTime();
            double earnings = dataReplies.getDouble("EARNINGS");

            Income income = new Income(id, dateIn, timeWorked, earnings);
            //Use the view model to insert an income into the db
            mIncomeViewModel.update(income);
        } else if(requestCode == CARD_EDIT_REQUEST_CODE && resultCode == RESULT_OK){
            Bundle dataReplies = data.getExtras();
            CardData card;
            boolean[] posList = dataReplies.getBooleanArray("posList");
            for(int i = 0; i <= 2 ; i++){
                card = mCards.get(i);
                card.setSelected(posList[i]);
                mCardViewModel.update(card);
            }


        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
