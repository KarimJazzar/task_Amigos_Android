package com.example.task_amigos_android;// Created by FebinRukfan on 05-02-2022.

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.example.task_amigos_android.databinding.FragmentInfoBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InfoController {

    private int mYear, mMonth, mDay;
    FragmentInfoBinding fragmentInfoBinding;
    Context context;

    public void selectDate(FragmentInfoBinding fragmentInfoBinding, Context context){

        this.fragmentInfoBinding=fragmentInfoBinding;
        this.context=context;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                        c.set(Calendar.MONTH,monthOfYear);

                        fragmentInfoBinding.txtDuedate.setText(dayOfMonth + " - " + (month_date.format(c.getTime())) + " - " + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
