package com.example.waiam;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class CalcsViewModel extends AndroidViewModel {
    private CalcsRepository mCalcsRepository;
    private LiveData<List<Calcs>> mAllCalcs;

    public CalcsViewModel (Application application){
        super(application);
        mCalcsRepository = new CalcsRepository(application);
        mAllCalcs = mCalcsRepository.getmAllCalcs();
    }

    LiveData<List<Calcs>> getAllCalcs() {return mAllCalcs;}


    public void insert(Calcs calcs) {mCalcsRepository.insert(calcs); }
}