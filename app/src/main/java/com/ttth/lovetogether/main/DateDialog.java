package com.ttth.lovetogether.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by HangPC on 5/17/2017.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private Calendar today;
    private GetNumDay getNumDay;

    public void setGetNumDay(GetNumDay getNumDay) {
        this.getNumDay = getNumDay;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        today = Calendar.getInstance();
        int curDay = today.get(Calendar.DAY_OF_MONTH);
        int curMonth= today.get(Calendar.MONTH);
        int curYear = today.get(Calendar.YEAR);
        return new DatePickerDialog(getActivity(), this, curYear, curMonth, curDay);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth+"-"+(month+1)+"-"+year;
//        Log.e("-----------","======="+date);
        calculatorDay(year, month, dayOfMonth);
        getNumDay.getSetDay(year, month+1, dayOfMonth);
    }
    public void calculatorDay(int year, int month, int dayOfMonth){
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        thatDay.set(Calendar.MONTH, month);
        thatDay.set(Calendar.YEAR, year);
        final long day = ((today.getTimeInMillis() - thatDay.getTimeInMillis())/(24*60*60*1000))+1;
        if (day < 0 ){
            getNumDay.setNumDay("0");
        }else {
            getNumDay.setNumDay(String.valueOf(day));
        }
    }
    public interface GetNumDay{
        void setNumDay(String numDay);
        void getSetDay(int setYear, int setMonth, int setDay);
    }
}
