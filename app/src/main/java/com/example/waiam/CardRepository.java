package com.example.waiam;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import javax.xml.transform.Result;

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

    public void update(CardData card){
        new CardRepository.updateAsyncTask(mCardDao).execute(card);
    }

    public Integer highestOrder(){
        return mCardDao.highestOrder(); //lol no problem here
       // return (new CardRepository.orderAsyncTask(mCardDao).execute()).get();
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

    private static class updateAsyncTask extends AsyncTask<CardData, Void, Void> {
        private CardDao mAsyncTaskDao;

        updateAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CardData... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class orderAsyncTask extends AsyncTask<CardData, Void, Integer> {
        private CardDao mAsyncTaskDao;

        orderAsyncTask(CardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final CardData... params) {
           return mAsyncTaskDao.highestOrder();
        }

        @Override
        protected void onPostExecute(Integer result){

        }
    }

}
