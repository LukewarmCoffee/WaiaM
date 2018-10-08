package com.example.waiam;

import java.util.List;

public class CalcsDataAdapter {
    private List<Calcs> mCalcs; //cached copy of calcs

    public void setCalcs(List<Calcs> calcs){
        mCalcs = calcs;
    }

    public String getHourlyWage(){
        double earnings = 0.0;
        double hoursWorked = 0.0;

        //this is costly
        for (int i = 0; i < mCalcs.size(); i++){
            earnings += mCalcs.get(i).getEarnings();
            hoursWorked += mCalcs.get(i).getHoursWorked();
        }
        earnings /= hoursWorked;
        return "" + earnings;
    }

    public String getTotalEarnings(){
        double earnings = 0.0;
        //this is costly
        for (int i = 0; i < mCalcs.size(); i++)
            earnings += mCalcs.get(i).getEarnings();
        return "" + earnings;
    }


    public String getTotalHoursWorked(){
        double hoursWorked = 0.0;
        //this is costly
        for (int i = 0; i < mCalcs.size(); i++)
            hoursWorked += mCalcs.get(i).getHoursWorked();
        return "" + hoursWorked;
    }

}
