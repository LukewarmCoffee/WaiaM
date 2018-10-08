package com.example.waiam;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class IncomeViewModel extends AndroidViewModel {
    private IncomeRepository mRepository;
    private LiveData<List<Income>> mAllIncomes;

    public IncomeViewModel (Application application){
        super(application);
        mRepository = new IncomeRepository(application);
        mAllIncomes = mRepository.getmAllIncomes();
    }

    LiveData<List<Income>> getAllIncomes() {return mAllIncomes;}


    public void insert(Income income) {mRepository.insert(income); }
}

