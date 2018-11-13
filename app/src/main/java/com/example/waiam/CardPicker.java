package com.example.waiam;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CardPicker extends Activity {
    private List<CardData> mCards = new ArrayList<>();  //todo move this data out of here so my viewpager can talk to this bad boi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_picker);

        mCards.add(new CardData(R.string.hourly_wage, "Sdfsdf", false));
        mCards.add(new CardData(R.string.hourly_wage, "Sdfsdf", true));
        mCards.add(new CardData(R.string.hourly_wage, "Sdfsdf", true));
        mCards.add(new CardData(R.string.hourly_wage, "Sdfsdf", false));


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final CardListAdapter adapter = new CardListAdapter(mCards,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
