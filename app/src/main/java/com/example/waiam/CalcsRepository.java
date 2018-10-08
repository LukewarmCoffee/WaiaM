package com.example.waiam;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CalcsRepository {
    private CalcsDao mCalcsDao;
    private LiveData<List<Calcs>> mAllCalcs;

    CalcsRepository(Application application) {
        IncomeRoomDatabase db = IncomeRoomDatabase.getDatabase(application);
        mCalcsDao =db.calcsDao();
        mAllCalcs = mCalcsDao.getAllCalcs();
    }

    LiveData<List<Calcs>> getmAllCalcs() {
        return mAllCalcs;
    }

    public void insert (Calcs calcs) {
        new insertAsyncTask(mCalcsDao).execute(calcs);
    }

    private static class insertAsyncTask extends AsyncTask<Calcs, Void, Void> {
        private CalcsDao mAsyncTaskDao;

        insertAsyncTask(CalcsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Calcs... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
