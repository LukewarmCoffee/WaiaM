package com.example.waiam;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardRepository mRepository;
    private LiveData<List<CardData>> mAllCards;

    public CardViewModel (Application application){
        super(application);
        mRepository = new CardRepository(application);
        mAllCards = mRepository.getAllCards();
    }

    LiveData<List<CardData>> getAllCards() {return mAllCards;}


    public void insert(CardData card) {mRepository.insert(card); }

    public void update(CardData card) {mRepository.update(card);}

    public CardData get(Integer position) {return mAllCards.getValue().get(position);}

}
