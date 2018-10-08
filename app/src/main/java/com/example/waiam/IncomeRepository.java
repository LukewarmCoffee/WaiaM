package com.example.waiam;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class IncomeRepository {
    private IncomeDao mIncomeDao;
    private LiveData<List<Income>> mAllIncomes;

    IncomeRepository(Application application) {
        IncomeRoomDatabase db = IncomeRoomDatabase.getDatabase(application);
        mIncomeDao =db.incomeDao();
        mAllIncomes = mIncomeDao.getAllIncomes();
    }

    LiveData<List<Income>> getmAllIncomes() {
        return mAllIncomes;
    }

    public void insert (Income income) {
        new insertAsyncTask(mIncomeDao).execute(income);
    }

    private static class insertAsyncTask extends AsyncTask<Income, Void, Void> {
        private IncomeDao mAsyncTaskDao;

        insertAsyncTask(IncomeDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Income... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
