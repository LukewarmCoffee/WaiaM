package com.example.waiam;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class TimeViewModel extends ViewModel {
    private final MutableLiveData<TimeObj> selected = new MutableLiveData<TimeObj>();

    public void select(TimeObj timeObj){
        selected.setValue(timeObj);
    }

    public LiveData<TimeObj> getSelected(){
        return selected;
    }
}
