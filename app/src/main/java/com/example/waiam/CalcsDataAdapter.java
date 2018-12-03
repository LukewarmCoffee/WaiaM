package com.example.waiam;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CalcsDataAdapter {
    private List<Income> mIncomes;
    private List<CardData> mCards;
    private List<CardData> viewCards;

    CalcsDataAdapter(){
        viewCards = new ArrayList<>();
        viewCards.add(new CardData(3, R.string.hourly_wage, "dsfdf", "lkjhn",true));  //stupidholder
    }

    //next up, take in the cardData
    //only perform calculations on Cards in Active Views
    //Return the Cards that need to be added to the viewpager

    public void setIncomes(List<Income> incomes) {
        mIncomes = incomes;
        updateData();
    }  //update the data on the currently shown cards

    public void setCards(List<CardData> cards) {
        mCards = cards;
        updateViewCards();
    } //change the currently shown cards

    private void updateData(){
        NumberFormat deciForm = new DecimalFormat("##.##");
        if (mCards != null){
            if (mCards.get(0).getSelected())    //total earnings
                mCards.get(0).setContent("$" + deciForm.format(getTotalEarnings()));
            if (mCards.get(1).getSelected())    //hours worked
                mCards.get(1).setContent("" + getTotalHoursWorked());
            if (mCards.get(2).getSelected())    //hourly wage
                mCards.get(2).setContent("$" + deciForm.format(getHourlyWage()));
        }
    }

    private void updateViewCards(){
        viewCards = new ArrayList<>();
        for (int i = 0; i < mCards.size(); i++){
            if (mCards.get(i).getSelected())
                viewCards.add(mCards.get(i));
        }
    }

    public List<CardData> getViewCards(){
        return viewCards;
    }

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
