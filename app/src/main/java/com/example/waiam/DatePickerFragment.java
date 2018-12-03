package com.example.waiam;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private TimeViewModel model;
    private boolean mDateIn;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDateIn = getArguments().getBoolean("IS_DATE_IN");

        model = ViewModelProviders.of(getActivity()).get(TimeViewModel.class);
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);  //starts at 0
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        if(PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getBoolean("nightmode",false))
            return new DatePickerDialog(getActivity(),R.style.DialogThemeDark, this, year, month, day);
        else
            return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //timeobj and viewModel allow our date picker and time picker to talk to each other. todo extend this so when somone cancels, they dont see the time picker
        TimeObj timeObj = new TimeObj(day, month, year, mDateIn);
        model.select(timeObj);
    }


}