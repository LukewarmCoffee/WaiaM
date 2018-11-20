package com.example.waiam;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CardRepository {
    private CardDao mCardDao;
    private LiveData<List<CardData>> mAllCards;

    CardRepository(Application application) {
        IncomeRoomDatabase db = IncomeRoomDatabase.getDatabase(application);
        mCardDao = db.cardDao();
        mAllCards = mCardDao.getAllCards();
    }

    LiveData<List<CardData>> getAllCards() {
        return mAllCards;
    }

    public void insert (CardData card) {
        new CardRepository.insertAsyncTask(mCardDao).execute(card);
    }

    private static class insertAsyncTask extends AsyncTask<CardData, Void, Void> {
        private CardDao mAsyncTaskDao;

        insertAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CardData... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
