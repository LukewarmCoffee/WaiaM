package com.example.waiam;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CardPicker extends AppCompatActivity {

    private CardViewModel viewModel;
    private boolean[] posList = new boolean[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_picker);




        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final CardListAdapter adapter = new CardListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        viewModel.getAllCards().observe(this, new Observer<List<CardData>>() {
            @Override
            public void onChanged(@Nullable List<CardData> cards) {
                    adapter.setCards(cards);
            }
        });

        final FloatingActionButton fabSave = findViewById(R.id.fab);
        fabSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                Bundle allReplies = new Bundle();

                posList = adapter.returnPositions();

                if (posList == null) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    allReplies.putBooleanArray("posList", posList);
                    replyIntent.putExtras(allReplies);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }

}
