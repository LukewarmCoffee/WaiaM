package com.example.waiam;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private setTimeListener mTimeListener;
    private TimeObj mTimeObj;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTimeListener = (setTimeListener) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final TimeViewModel model = ViewModelProviders.of(getActivity()).get(TimeViewModel.class);
        model.getSelected().observe(this, new Observer<TimeObj>() {
            @Override
            public void onChanged(@Nullable TimeObj timeObj) {
                mTimeObj = timeObj;
            }
        });
        // Use the current time as the default values for the picker

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        // Create a new instance of TimePickerDialog and return it
        if (PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getBoolean("nightmode",false))
         return new TimePickerDialog(getActivity(),R.style.DialogThemeDark, this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        else
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hour, int minute) {
        Calendar cal = Calendar.getInstance();  //gets teh correct timezone
        cal.set(mTimeObj.getYear(), mTimeObj.getMonth(), mTimeObj.getDay(), hour, minute);
        Date date = new Date(cal.getTimeInMillis());
        mTimeListener.setTime(date, mTimeObj.getDateIn() );    //todo formatting
    }

    public interface setTimeListener {
        void setTime(Date date, boolean timeRequested );
    }

}