package com.example.waiam;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CalcsDataAdapter {
   // private List<Calcs> mCalcs; //cached copy of calcs
    private List<Income> mIncomes;

    //public void setCalcs(List<Calcs> calcs){
       // mCalcs = calcs;
  //}

    public void setIncomes(List<Income> incomes) {mIncomes = incomes;}

    public Double getHourlyWage(){
        double earnings = 0.0;
        double hoursWorked = 0.0;

        //this is costly
        for (int i = 0; i < mIncomes.size(); i++){
            earnings += mIncomes.get(i).getEarnings();
            hoursWorked += TimeUnit.MILLISECONDS.toHours(mIncomes.get(i).getTimeWorked());
        }
        earnings /= hoursWorked;
        return earnings;
    }

    public Double getTotalEarnings(){
        double earnings = 0.0;
        //this is costly
        for (int i = 0; i < mIncomes.size(); i++)
            earnings += mIncomes.get(i).getEarnings();
        return earnings;
    }


    public Double getTotalHoursWorked(){
        double hoursWorked = 0.0;
        //this is costly
        for (int i = 0; i < mIncomes.size(); i++)
            hoursWorked += TimeUnit.MILLISECONDS.toHours(mIncomes.get(i).getTimeWorked());
        return hoursWorked;
    }

}
